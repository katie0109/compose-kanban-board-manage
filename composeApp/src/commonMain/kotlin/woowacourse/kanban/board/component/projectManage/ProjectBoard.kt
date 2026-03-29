package woowacourse.kanban.board.component.projectManage

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.kanban.board.component.kanbanBoard.KanbanBoard
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.state.ProjectBoardState

@Composable
fun ProjectBoard() {
    val state = remember { ProjectBoardState() }

    Row {
        ProjectSideBar(
            kanbanBoardList = state.kanbanBoards,
            isSelected = { state.isKanbanBoardTaskSelected(it) },
            onClick = { state.selectedOnValueChange(it) },
        )
        VerticalDivider()
        KanbanBoard(
            state.selectedKanbanBoardTask,
            onAddTask = { task ->
                state.updateSelectedBoard { it.addTask(task) }
            },
            onMoveTaskStatus = { taskId, targetStatus ->
                state.updateSelectedBoard { it.moveTaskStatus(taskId, targetStatus) }
            },
        )
    }
}

@Preview(showBackground = true, widthDp = 1500, heightDp = 800)
@Composable
private fun ProjectBoardPreview() {
    ProjectBoard()
}
