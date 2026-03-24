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

    val kanbanBoardDatas = remember {
        mutableListOf(
            KanbanBoardData(title = "Compose1"),
            KanbanBoardData(title = "Compose2"),
            KanbanBoardData(title = "Compose3너무너무너무너무너무너무너무너무"),
        )
    }

    var selectedKanbanBoardData by remember { mutableStateOf(kanbanBoardDatas.first()) }

    fun selectedOnValueChange(kanbanBoardData: KanbanBoardData) {
        selectedKanbanBoardData = kanbanBoardData
    }

    fun isKanbanBoardDataSelected(kanbanBoardData: KanbanBoardData): Boolean = selectedKanbanBoardData == kanbanBoardData

    Row {
        ProjectSideBar(kanbanBoardDatas, isSelected = { isKanbanBoardDataSelected(it) }, onClick = { selectedOnValueChange(it) })
        VerticalDivider()
        KanbanBoard(selectedKanbanBoardData)
    }
}

@Preview(showBackground = true)
@Composable
private fun ProjectBoardPreview() {
    ProjectBoard()
}
