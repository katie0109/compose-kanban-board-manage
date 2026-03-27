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
import woowacourse.kanban.board.model.KanbanBoardData

@Composable
fun ProjectBoard() {
    var kanbanBoardDatas by remember {
        mutableStateOf(
            listOf(
                KanbanBoardData(title = "Compose1"),
                KanbanBoardData(title = "Compose2"),
                KanbanBoardData(title = "Compose3너무너무너무너무너무너무너무너무"),
            ),
        )
    }

    var selectedIndex by remember { mutableStateOf(0) }

    val selectedKanbanBoardData = kanbanBoardDatas[selectedIndex]

    fun selectedOnValueChange(kanbanBoardData: KanbanBoardData) {
        selectedIndex = kanbanBoardDatas.indexOf(kanbanBoardData)
    }

    fun isKanbanBoardDataSelected(kanbanBoardData: KanbanBoardData): Boolean = kanbanBoardDatas.indexOf(kanbanBoardData) == selectedIndex

    fun updateSelectedBoard(update: (KanbanBoardData) -> KanbanBoardData) {
        kanbanBoardDatas = kanbanBoardDatas.mapIndexed { index, data ->
            if (index == selectedIndex) update(data) else data
        }
    }

    Row {
        ProjectSideBar(kanbanBoardDatas, isSelected = { isKanbanBoardDataSelected(it) }, onClick = { selectedOnValueChange(it) })
        VerticalDivider()
        KanbanBoard(
            selectedKanbanBoardData,
            onAddBoardData = { boardData ->
                updateSelectedBoard { it.addBoardData(boardData) }
            },
            onMoveBoardDataStatus = { taskId, targetStatus ->
                updateSelectedBoard { it.moveBoardDataStatus(taskId, targetStatus) }
            },
        )
    }
}

@Preview(showBackground = true, widthDp = 1500, heightDp = 800)
@Composable
private fun ProjectBoardPreview() {
    ProjectBoard()
}
