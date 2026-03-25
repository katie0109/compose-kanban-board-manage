package woowacourse.kanban.board.model

data class KanbanBoardData(
    val title: String,
    private val boardList: MutableList<BoardData> = mutableListOf()
) {
    fun getBoardList(): MutableList<BoardData> = boardList
    fun totalStatusCount(): Int = boardList.size
    fun doneCount(): Int = boardList.count { it.status == Status.DONE }

    fun progress(): Float =
        if (totalStatusCount() == 0) 0f else (boardList.count { it.status == Status.DONE }).toFloat() / totalStatusCount()

    fun addBoardData(boardData: BoardData) {
        boardList.add(boardData)
    }

    fun getStatusBoard(status: Status): List<BoardData> = boardList.filter { it.status == status }
}
