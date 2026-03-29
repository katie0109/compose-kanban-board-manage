package woowacourse.kanban.board.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import woowacourse.kanban.board.domain.Status
import kotlin.collections.component1
import kotlin.collections.component2

class KanbanBoardState {

    var showDialog by mutableStateOf(false)
    var isShowSnackBar by mutableStateOf(false)
    var text by  mutableStateOf("새로운 태스크가 생성되었습니다.")

    //드래그 앤 드롭 상태
    var draggedTaskId by mutableStateOf<Int?>(null)
    var currentDragPosition by mutableStateOf<Offset?>(null)
    val columnBounds = mutableStateMapOf<Status, Rect>()
    var draggedTaskSourceStatus by mutableStateOf<Status?>(null)

    fun onTaskDragEnd(state: KanbanBoardState, onMoveTaskStatus: (Int, Status) -> Unit){
        val dropPosition = state.currentDragPosition ?: run {
            state.draggedTaskId = null
            return
        }
        val targetStatus = state.columnBounds.entries
            .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

        if (targetStatus != null && state.draggedTaskId != null) {
            if (targetStatus != state.draggedTaskSourceStatus) {
                state.text = "태스크가 이동되었습니다."
                state.isShowSnackBar = true
            }
            onMoveTaskStatus(state.draggedTaskId!!, targetStatus)
        }
        state.currentDragPosition = null
        state.draggedTaskId = null
        state.draggedTaskSourceStatus = null
    }
}
