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
import woowacourse.kanban.board.theme.StatusColor

@Composable
fun KanbanBoard(
    kanbanBoard: KanbanBoard,
    modifier: Modifier = Modifier,
    onAddTask: (Task) -> Unit = {},
    onMoveTaskStatus: (Int, Status) -> Unit = { _, _ -> },
) {
    val statuses = remember { Status.entries }
    val names = remember { listOf("다이노", "페임스") }
    var showDialog by remember { mutableStateOf(false) }
    var isShowSnackBar by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("새로운 태스크가 생성되었습니다.") }

    fun onCreateClick() {
        showDialog = true
    }

    fun onDismissRequest() {
        showDialog = false
    }

    fun onShowSnackBar() {
        isShowSnackBar = true
    }

    fun onTaskCreate(dialogState: DialogState) {
        val task = Task(
            title = dialogState.titleInputValue,
            description = dialogState.descriptionInputValue,
            tags = if (dialogState.tagsInputValue.isNotBlank()) {
                dialogState.tagsInputValue.split(",").map { Tag(it) }
            } else emptyList(),
            status = dialogState.statusValue,
            nickname = dialogState.nameValue,
        )
        onAddTask(task)
    }

    suspend fun showSnackBar() {
        delay(3000.milliseconds)
        isShowSnackBar = false
    }

    fun onSnackBarCancelClick() {
        isShowSnackBar = false
    }

    var draggedTaskId by remember { mutableStateOf<Int?>(null) }
    var currentDragPosition by remember { mutableStateOf<Offset?>(null) }
    val columnBounds = remember { mutableStateMapOf<Status, Rect>() }
    var draggedTaskSourceStatus by remember { mutableStateOf<Status?>(null) }

    Box {
        Column(
            modifier = modifier.fillMaxSize().background(color = Color.White),
        ) {
            KanbanBoardTitleBar(
                title = kanbanBoard.title,
                progress = kanbanBoard.progress(),
                doneCount = kanbanBoard.doneCount(),
                totalStatusCount = kanbanBoard.totalStatusCount(),
                onCreateClick = { onCreateClick() },
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
                            currentDragPosition?.let { columnBounds[status]?.contains(it) } ?: false
                        },
                        onBoundsChanged = { rect -> columnBounds[status] = rect },
                        onTaskDragStart = { task ->
                            draggedTaskId = task.id
                            draggedTaskSourceStatus = task.status
                        },
                        onTaskDragChange = { pos -> currentDragPosition = pos },
                        onTaskDragEnd = {
                            val dropPosition = currentDragPosition ?: run {
                                draggedTaskId = null
                                return@StatusCardManageBox
                            }
                            val targetStatus = columnBounds.entries
                                .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

                            if (targetStatus != null && draggedTaskId != null) {
                                if (targetStatus != draggedTaskSourceStatus) {
                                    text = "태스크가 이동되었습니다."
                                    isShowSnackBar = true
                                }
                                onMoveTaskStatus(draggedTaskId!!, targetStatus)
                            }
                            currentDragPosition = null
                            draggedTaskId = null
                            draggedTaskSourceStatus = null
                        },
                        onTaskDragCancel = {
                            currentDragPosition = null
                            draggedTaskId = null
                        },
                    )
                }
            }

            if (showDialog) {
                Dialog(
                    onDismissRequest = { onDismissRequest() },
                ) {
                    TaskCreateDialog(
                        statuses = statuses,
                        names = names,
                        onTaskCreate = {
                            onTaskCreate(it)
                            onDismissRequest()
                            text = "새로운 태스크가 생성되었습니다."
                            onShowSnackBar()
                        },
                        onDismissRequest = { onDismissRequest() },
                    )
                }
            }
        }
        LaunchedEffect(isShowSnackBar) {
            if (isShowSnackBar) showSnackBar()
        }
        if (isShowSnackBar) CreateAlertSnackBar(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(4.dp))
                .background(color = Color(0xFF322F35))
                .padding(start = 16.dp)
                .size(width = 344.dp, height = 48.dp)
                .align(alignment = Alignment.BottomCenter),
            text = text,
            onClick = { onSnackBarCancelClick() },
        )
    }
}

@Preview(showBackground = true, widthDp = 1200, heightDp = 800)
@Composable
private fun KanbanBoardPreview() {
    KanbanBoard(kanbanBoard = KanbanBoard(title = "Compose1"))
}
