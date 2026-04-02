package woowacourse.kanban.board.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.domain.isMoveTodoToInProgress
import woowacourse.kanban.board.domain.isStatusTransitionAllowed
import kotlin.collections.component1
import kotlin.collections.component2

class KanbanBoardState {

    var showDialog by mutableStateOf(false)
    var snackBarState by mutableStateOf(SnackBarState())
    var dialogState by mutableStateOf(DialogState())
    var draggedTaskId by mutableStateOf<String?>(null)
    var currentDragPosition by mutableStateOf<Offset?>(null)
    val columnBounds = mutableStateMapOf<Status, Rect>()
    var draggedTaskSourceStatus by mutableStateOf<Status?>(null)

    var draggedTaskNickname by mutableStateOf<String?>(null)

    fun onTaskDragEnd(state: KanbanBoardState, onMoveTaskStatus: (String, Status) -> Unit){
        val dropPosition = state.currentDragPosition ?: run {
            state.draggedTaskId = null
            draggedTaskNickname = null
            return
        }
        val targetStatus = state.columnBounds.entries
            .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

        if (targetStatus != null && state.draggedTaskId != null) {
            if(state.draggedTaskSourceStatus.isMoveTodoToInProgress(targetStatus) && draggedTaskNickname == "없음"){
                snackBarState = SnackBarState(
                    isVisible = true,
                    text = "담당자를 지정해야 상태를 옮길 수 있습니다."
                )
                state.currentDragPosition = null
                state.draggedTaskId = null
                state.draggedTaskSourceStatus = null
                state.draggedTaskNickname = null
                return
            }
            if (targetStatus != state.draggedTaskSourceStatus && state.draggedTaskSourceStatus.isStatusTransitionAllowed(targetStatus)) {
                snackBarState = SnackBarState(
                    isVisible = true,
                    text = "태스크가 이동되었습니다."
                )
                onMoveTaskStatus(state.draggedTaskId!!, targetStatus)
            }
            else{
                snackBarState = SnackBarState(
                    isVisible = true,
                    text = "해당 상태로 옮길 수 없습니다."
                )
            }
        }
        state.currentDragPosition = null
        state.draggedTaskId =  null
        state.draggedTaskSourceStatus = null
        state.draggedTaskNickname = null
    }
}
