package woowacourse.kanban.board.domain

data class KanbanBoard(
    val title: String,
    val taskList: List<Task> = emptyList(),
    private val nextId: Int = 0,
) {
    fun totalStatusCount(): Int = taskList.size
    fun doneCount(): Int = taskList.count { it.status == Status.DONE }

    fun progress(): Float =
        if (totalStatusCount() == 0) 0f else (taskList.count { it.status == Status.DONE }).toFloat() / totalStatusCount()

    fun addTask(task: Task): KanbanBoard = copy(
        taskList = taskList + task.copy(id = nextId),
        nextId = nextId+1
    )

    fun moveTaskStatus(taskId: Int, targetStatus: Status): KanbanBoard {
        val targetIndex = taskList.indexOfFirst { it.id == taskId }
        if (targetIndex == -1) return this

        val targetTask = taskList[targetIndex]
        if (targetTask.status == targetStatus) return this

        val updatedTaskList = taskList.map { task ->
            if (task.id == taskId) task.copy(status = targetStatus) else task
        }

        return copy(taskList = updatedTaskList)
    }

    fun getStatusTask(status: Status): List<Task> = taskList.filter { it.status == status }
}
