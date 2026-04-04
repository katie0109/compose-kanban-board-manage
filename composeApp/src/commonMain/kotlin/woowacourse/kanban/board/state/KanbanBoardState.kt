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
    var dialogState by mutableStateOf(DialogState())
    var draggedTaskId by mutableStateOf<String?>(null)
    var currentDragPosition by mutableStateOf<Offset?>(null)
    val columnBounds = mutableStateMapOf<Status, Rect>()
    var draggedTaskSourceStatus by mutableStateOf<Status?>(null)

    var draggedTaskNickname by mutableStateOf<String?>(null)

    fun onTaskDragEnd(state: KanbanBoardState, onMoveTaskStatus: (String, Status) -> Unit) {
        val dropPosition = state.currentDragPosition ?: run {
            resetDragState()
            return
        }
        val targetStatus = state.columnBounds.entries
            .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key
            ?: run {
                resetDragState()
                return
            }

        val taskId = state.draggedTaskId

        if (taskId == null) {
            resetDragState()
            return
        }

        onMoveTaskStatus(taskId, targetStatus)
        resetDragState()
    }

    private fun resetDragState() {
        currentDragPosition = null
        draggedTaskId = null
        draggedTaskSourceStatus = null
        draggedTaskNickname = null
    }
}
