package woowacourse.kanban.board.domain

enum class Status(val state: String) {
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    REVIEW("Review"),
    DONE("Done"),
}
fun Status?.isMoveTodoToInProgress(status: Status):Boolean{
    return when(this){
        Status.TODO -> status==Status.IN_PROGRESS
        else -> false
    }
}

fun Status?.isStatusTransitionAllowed(status: Status): Boolean {
    return when (this) {
        Status.TODO -> status == Status.IN_PROGRESS
        Status.IN_PROGRESS -> status == Status.TODO || status ==Status.REVIEW
        Status.REVIEW -> status == Status.IN_PROGRESS || status == Status.DONE
        Status.DONE -> status == Status.TODO
        else -> false
    }
}
