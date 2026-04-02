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
import androidx.compose.material3.Snackbar
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
import woowacourse.kanban.board.state.DialogMode
import woowacourse.kanban.board.state.DialogState
import woowacourse.kanban.board.state.KanbanBoardState
import woowacourse.kanban.board.state.SnackBarState
import woowacourse.kanban.board.theme.StatusColor
import java.awt.SystemColor.text
import javax.swing.JColorChooser.showDialog

@Composable
fun KanbanBoard(
    kanbanBoard: KanbanBoard,
    modifier: Modifier = Modifier,
    onAddTask: (Task) -> Unit = {},
    onEditTask: (Task) -> Unit = {},
    onMoveTaskStatus: (String, Status) -> Unit = { _, _ -> },
) {
    val statuses = remember { Status.entries }
    val names = remember { listOf("다이노", "페임스") }
    val state = remember { KanbanBoardState() }


    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(color = Color.White),
        ) {
            KanbanBoardTitleBar(
                title = kanbanBoard.title,
                progress = kanbanBoard.progress(),
                doneCount = kanbanBoard.doneCount(),
                totalStatusCount = kanbanBoard.totalStatusCount(),
                onCreateClick = {
                    state.showDialog = true
                    state.dialogState.mode = DialogMode.CREATE

                                },
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
                        onTaskClick = { task ->
                            state.showDialog = true
                            state.dialogState.mode = DialogMode.EDIT
                            state.dialogState.loadTaskData(task)
                        },
                    )
                }
            }
            DialogIfVisible(
                state = state,
                statuses = statuses,
                names = names,
                onAddTask = onAddTask,
                onEditTask = onEditTask,
            )
        }
        CreateAlertSnackBarVisible(
            state = state,
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
        )
    }
}

//Dialog 표시 여부 책임 분리
@Composable
private fun DialogIfVisible(
    state: KanbanBoardState,
    statuses:List<Status>,
    names:List<String>,
    onAddTask: (Task) -> Unit,
    onEditTask: (Task) -> Unit,
){
    if (state.showDialog) {
        Dialog(
            onDismissRequest = { state.showDialog = false },
        ) {
            TaskCreateDialog(
                statuses = statuses,
                names = names,
                onTaskCreate = {
                    task -> onAddTask(task);
                    state.showDialog = false
                    state.dialogState.resetDialog()
                    state.snackBarState = SnackBarState(
                        isVisible = true,
                        text = "새로운 태스크가 생성되었습니다."
                    )
                },
                onEditTask = {
                    task -> onEditTask(task);
                    state.showDialog = false
                    state.dialogState.resetDialog()
                    state.snackBarState = SnackBarState(
                        isVisible = true,
                        text = "태스크가 수정되었습니다."
                    )

                },
                onDismissRequest = {
                    state.showDialog = false
                    state.dialogState.resetDialog()
                },
                dialogState = state.dialogState,
            )
        }
    }
}

//스낵바 표시 여부 책임 분리
@Composable
private fun CreateAlertSnackBarVisible(
    state: KanbanBoardState,
    modifier: Modifier = Modifier,
){
    LaunchedEffect(state.snackBarState.isVisible) {
        if (state.snackBarState.isVisible) {
            delay(3000.milliseconds)
            state.snackBarState = SnackBarState(isVisible = false)
        }
    }
    if (state.snackBarState.isVisible) CreateAlertSnackBar(
        modifier = modifier
            .clip(shape = RoundedCornerShape(4.dp))
            .background(color = Color(0xFF322F35))
            .padding(start = 16.dp)
            .size(width = 344.dp, height = 48.dp),
        text = state.snackBarState.text,
        onClick = { state.snackBarState = SnackBarState(isVisible = false) },
    )
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800)
@Composable
private fun KanbanBoardPreview() {
    KanbanBoard(kanbanBoard = KanbanBoard(title = "Compose1"))
}
