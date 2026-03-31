package woowacourse.kanban.board.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.KanbanBoard

class ProjectBoardState {
    var kanbanBoards by mutableStateOf(
        listOf(
            KanbanBoard(title = "Compose1"),
            KanbanBoard(title = "Compose2"),
            KanbanBoard(title = "Compose3너무너무너무너무너무너무너무너무"),
        )
    )
    var selectedIndex by mutableStateOf(0)

    val selectedKanbanBoardTask get() = kanbanBoards[selectedIndex]

    fun selectedOnValueChange(kanbanBoard: KanbanBoard) {
        selectedIndex = kanbanBoards.indexOf(kanbanBoard)
    }

    fun isKanbanBoardTaskSelected(kanbanBoard: KanbanBoard): Boolean =
        kanbanBoards.indexOf(kanbanBoard) == selectedIndex

    fun updateSelectedBoard(update: (KanbanBoard) -> KanbanBoard) {
        kanbanBoards = kanbanBoards.mapIndexed { index, board ->
            if (index == selectedIndex) update(board) else board
        }
    }
}
