package woowacourse.kanban.board.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.Status

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

    // 보드 선택
    fun selectedOnValueChange(kanbanBoard: KanbanBoard) {
        selectedIndex = kanbanBoards.indexOf(kanbanBoard)
    }

    fun isKanbanBoardTaskSelected(kanbanBoard: KanbanBoard): Boolean =
        kanbanBoards.indexOf(kanbanBoard) == selectedIndex

    fun addTask(task: Task) {
        updateSelectedBoard { it.addTask(task) }
    }

    fun editTask(task: Task) {
        updateSelectedBoard { it.updateTask(task) }
    }

    fun deleteTask(task: Task) {
        updateSelectedBoard { it.deleteTask(task) }
    }

    fun moveTaskStatus(taskId: String, targetStatus: Status) {
        updateSelectedBoard { it.moveTaskStatus(taskId, targetStatus) }
    }

    private fun updateSelectedBoard(update: (KanbanBoard) -> KanbanBoard) {
        kanbanBoards = kanbanBoards.mapIndexed { index, board ->
            if (index == selectedIndex) update(board) else board
        }
    }
}
