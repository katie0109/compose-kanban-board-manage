package woowacourse.kanban.board.component.kanbanBoard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay
import woowacourse.kanban.board.component.dialog.TaskCreateDialog
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.domain.Tag
import woowacourse.kanban.board.state.DialogState
import woowacourse.kanban.board.state.KanbanBoardState
import woowacourse.kanban.board.theme.StatusColor
import java.awt.SystemColor.text
import javax.swing.JColorChooser.showDialog

@Composable
fun KanbanBoard(
    kanbanBoard: KanbanBoard,
    modifier: Modifier = Modifier,
    onAddTask: (Task) -> Unit = {},
    onMoveTaskStatus: (Int, Status) -> Unit = { _, _ -> },
) {
    val statuses = remember { Status.entries }
    val names = remember { listOf("다이노", "페임스") }
    var state = remember { KanbanBoardState() }

    suspend fun showSnackBar() {
        delay(3000.milliseconds)
        state.isShowSnackBar = false
    }

    Box {
        Column(
            modifier = modifier.fillMaxSize().background(color = Color.White),
        ) {
            KanbanBoardTitleBar(
                title = kanbanBoard.title,
                progress = kanbanBoard.progress(),
                doneCount = kanbanBoard.doneCount(),
                totalStatusCount = kanbanBoard.totalStatusCount(),
                onCreateClick = { state.showDialog = true },
            )
            Row(
                modifier = Modifier.padding(24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Status.entries.forEach { status ->
                    StatusCardManageBox(
                        boardList = kanbanBoard.getStatusTask(status),
                        status = status,
                        statusColor = StatusColor.getStatusColor(status),
                        getIsDropTarget = {
                            state.currentDragPosition?.let { state.columnBounds[status]?.contains(it) } ?: false
                        },
                        onBoundsChanged = { rect -> state.columnBounds[status] = rect },
                        onTaskDragStart = { task ->
                            state.draggedTaskId = task.id
                            state.draggedTaskSourceStatus = task.status
                        },
                        onTaskDragChange = { pos -> state.currentDragPosition = pos },
                        onTaskDragEnd = { state.onTaskDragEnd(state, onMoveTaskStatus) },
                        onTaskDragCancel = {
                            state.currentDragPosition = null
                            state.draggedTaskId = null
                        },
                    )
                }
            }

            if (state.showDialog) {
                Dialog(
                    onDismissRequest = { state.showDialog = false },
                ) {
                    TaskCreateDialog(
                        statuses = statuses,
                        names = names,
                        onTaskCreate = {
                            it.onTaskCreate(onAddTask)
                            state.showDialog = false
                            state.text = "새로운 태스크가 생성되었습니다."
                            state.isShowSnackBar = true
                        },
                        onDismissRequest = { state.showDialog = false },
                    )
                }
            }
        }
        LaunchedEffect(state.isShowSnackBar) {
            if (state.isShowSnackBar) showSnackBar()
        }
        if (state.isShowSnackBar) CreateAlertSnackBar(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(4.dp))
                .background(color = Color(0xFF322F35))
                .padding(start = 16.dp)
                .size(width = 344.dp, height = 48.dp)
                .align(alignment = Alignment.BottomCenter),
            text = state.text,
            onClick = { state.isShowSnackBar = false },
        )
    }
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800)
@Composable
private fun KanbanBoardPreview() {
    KanbanBoard(kanbanBoard = KanbanBoard(title = "Compose1"))
}
