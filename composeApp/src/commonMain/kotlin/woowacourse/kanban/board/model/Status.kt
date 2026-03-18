package woowacourse.kanban.board.model

import woowacourse.kanban.board.constant.DONE_CARD_BOX_BORDER_COLOR
import woowacourse.kanban.board.constant.DONE_CARD_BOX_CONTENT_COLOR
import woowacourse.kanban.board.constant.DONE_CARD_BOX_TITLE_COLOR
import woowacourse.kanban.board.constant.IN_PROGRESS_CARD_BOX_BORDER_COLOR
import woowacourse.kanban.board.constant.IN_PROGRESS_CARD_BOX_CONTENT_COLOR
import woowacourse.kanban.board.constant.IN_PROGRESS_CARD_BOX_TITLE_COLOR
import woowacourse.kanban.board.constant.TODO_CARD_BOX_BORDER_COLOR
import woowacourse.kanban.board.constant.TODO_CARD_BOX_CONTENT_COLOR
import woowacourse.kanban.board.constant.TODO_CARD_BOX_TITLE_COLOR

enum class Status(val state: String) {
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    DONE("Done"),
}

data class StatusColor(val titleBgColor: Long, val boardBgColor: Long, val boardBorderColor: Long) {
    companion object {
        fun getStatusColor(status: Status): StatusColor {
            return when (status) {
                Status.TODO -> StatusColor(TODO_CARD_BOX_TITLE_COLOR, TODO_CARD_BOX_CONTENT_COLOR, TODO_CARD_BOX_BORDER_COLOR)
                Status.IN_PROGRESS -> StatusColor(
                    IN_PROGRESS_CARD_BOX_TITLE_COLOR,
                    IN_PROGRESS_CARD_BOX_CONTENT_COLOR,
                    IN_PROGRESS_CARD_BOX_BORDER_COLOR,
                )

                Status.DONE -> StatusColor(DONE_CARD_BOX_TITLE_COLOR, DONE_CARD_BOX_CONTENT_COLOR, DONE_CARD_BOX_BORDER_COLOR)
            }
        }
    }
}
