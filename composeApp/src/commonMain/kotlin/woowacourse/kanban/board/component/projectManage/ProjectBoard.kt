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

@Composable
fun ProjectBoard() {
    var kanbanBoards by remember {
        mutableStateOf(
            listOf(
                KanbanBoard(title = "Compose1"),
                KanbanBoard(title = "Compose2"),
                KanbanBoard(title = "Compose3너무너무너무너무너무너무너무너무"),
            ),
        )
    }

    var selectedIndex by remember { mutableStateOf(0) }

    val selectedKanbanBoardData = kanbanBoards[selectedIndex]

    fun selectedOnValueChange(kanbanBoard: KanbanBoard) {
        selectedIndex = kanbanBoards.indexOf(kanbanBoard)
    }

    fun isKanbanBoardDataSelected(kanbanBoard: KanbanBoard): Boolean = kanbanBoards.indexOf(kanbanBoard) == selectedIndex

    fun updateSelectedBoard(update: (KanbanBoard) -> KanbanBoard) {
        kanbanBoards = kanbanBoards.mapIndexed { index, data ->
            if (index == selectedIndex) update(data) else data
        }
    }

    Row {
        ProjectSideBar(kanbanBoards, isSelected = { isKanbanBoardDataSelected(it) }, onClick = { selectedOnValueChange(it) })
        VerticalDivider()
        KanbanBoard(
            selectedKanbanBoardData,
            onAddBoardData = { boardData ->
                updateSelectedBoard { it.addTask(boardData) }
            },
            onMoveBoardDataStatus = { taskId, targetStatus ->
                updateSelectedBoard { it.moveTaskStatus(taskId, targetStatus) }
            },
        )
    }
}

@Preview(showBackground = true, widthDp = 1500, heightDp = 800)
@Composable
private fun ProjectBoardPreview() {
    ProjectBoard()
}
