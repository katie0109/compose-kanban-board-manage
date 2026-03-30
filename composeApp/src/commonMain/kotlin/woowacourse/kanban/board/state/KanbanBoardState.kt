package woowacourse.kanban.board.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import woowacourse.kanban.board.domain.Status
import kotlin.collections.component1
import kotlin.collections.component2

class KanbanBoardState {

    var showDialog by mutableStateOf(false)
    var snackBarState by mutableStateOf(SnackBarState())
    var draggedTaskId by mutableStateOf<String?>(null)
    var currentDragPosition by mutableStateOf<Offset?>(null)
    val columnBounds = mutableStateMapOf<Status, Rect>()
    var draggedTaskSourceStatus by mutableStateOf<Status?>(null)

    fun onTaskDragEnd(state: KanbanBoardState, onMoveTaskStatus: (String, Status) -> Unit){
        val dropPosition = state.currentDragPosition ?: run {
            state.draggedTaskId = null
            return
        }
        val targetStatus = state.columnBounds.entries
            .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

        if (targetStatus != null && state.draggedTaskId != null) {
            if (targetStatus != state.draggedTaskSourceStatus) {
                snackBarState = SnackBarState(
                    isVisible = true,
                    text = "태스크가 이동되었습니다."
                )
            }
            onMoveTaskStatus(state.draggedTaskId!!, targetStatus)
        }
        state.currentDragPosition = null
        state.draggedTaskId = null
        state.draggedTaskSourceStatus = null
    }
}
