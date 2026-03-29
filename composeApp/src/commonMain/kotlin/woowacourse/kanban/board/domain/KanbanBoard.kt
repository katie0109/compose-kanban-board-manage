package woowacourse.kanban.board.model

data class KanbanBoard(val title: String, val boardList: List<BoardData> = emptyList()) {
    fun totalStatusCount(): Int = boardList.size
    fun doneCount(): Int = boardList.count { it.status == Status.DONE }

    fun progress(): Float =
        if (totalStatusCount() == 0) 0f else (boardList.count { it.status == Status.DONE }).toFloat() / totalStatusCount()

    fun addBoardData(boardData: BoardData): KanbanBoard = copy(boardList = boardList + boardData)

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

    fun getStatusBoard(status: Status): List<BoardData> = boardList.filter { it.status == status }
}
