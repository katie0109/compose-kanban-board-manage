package woowacourse.kanban.board.domain

enum class Status(val state: String) {
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    REVIEW("Review"),
    DONE("Done"),
}

fun Status.isDeletable(): Boolean {
    return when (this) {
        Status.TODO, Status.IN_PROGRESS -> true
        Status.REVIEW, Status.DONE -> false
    }
}

fun Status.isAssigneeRequired():Boolean{
    return when(this){
        Status.TODO -> false
        Status.IN_PROGRESS, Status.REVIEW, Status.DONE -> true
    }
}

fun Status.isStatusTransitionAllowed(status: Status): Boolean {
    return when (this) {
        Status.TODO -> status == Status.IN_PROGRESS
        Status.IN_PROGRESS -> status == Status.TODO || status ==Status.REVIEW
        Status.REVIEW -> status == Status.IN_PROGRESS || status == Status.DONE
        Status.DONE -> status == Status.TODO
    }
}

fun Status.isStatusTransitionBlocked(status: Status): Boolean {
    return !this.isStatusTransitionAllowed(status)
}
