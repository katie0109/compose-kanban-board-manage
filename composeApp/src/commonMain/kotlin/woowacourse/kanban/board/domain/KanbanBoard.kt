package woowacourse.kanban.board.domain

data class KanbanBoard(val title: String, val boardList: List<Task> = emptyList()) {
    fun totalStatusCount(): Int = boardList.size
    fun doneCount(): Int = boardList.count { it.status == Status.DONE }

    fun progress(): Float =
        if (totalStatusCount() == 0) 0f else (boardList.count { it.status == Status.DONE }).toFloat() / totalStatusCount()

    fun addBoardData(task: Task): KanbanBoard = copy(boardList = boardList + task)

    fun moveBoardDataStatus(taskId: Int, targetStatus: Status): KanbanBoard {
        val targetIndex = boardList.indexOfFirst { it.id == taskId }
        if (targetIndex == -1) return this

        val targetBoard = boardList[targetIndex]
        if (targetBoard.status == targetStatus) return this

        val updatedBoardList = boardList.map { boardData ->
            if (boardData.id == taskId) boardData.copy(status = targetStatus) else boardData
        }

        return copy(boardList = updatedBoardList)
    }

    fun getStatusBoard(status: Status): List<Task> = boardList.filter { it.status == status }
}
