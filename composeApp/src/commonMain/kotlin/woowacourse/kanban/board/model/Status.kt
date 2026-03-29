package woowacourse.kanban.board.model

import woowacourse.kanban.board.theme.DONE_CARD_BOX_BORDER_COLOR
import woowacourse.kanban.board.theme.DONE_CARD_BOX_CONTENT_COLOR
import woowacourse.kanban.board.theme.DONE_CARD_BOX_TITLE_COLOR
import woowacourse.kanban.board.theme.IN_PROGRESS_CARD_BOX_BORDER_COLOR
import woowacourse.kanban.board.theme.IN_PROGRESS_CARD_BOX_CONTENT_COLOR
import woowacourse.kanban.board.theme.IN_PROGRESS_CARD_BOX_TITLE_COLOR
import woowacourse.kanban.board.theme.TODO_CARD_BOX_BORDER_COLOR
import woowacourse.kanban.board.theme.TODO_CARD_BOX_CONTENT_COLOR
import woowacourse.kanban.board.theme.TODO_CARD_BOX_TITLE_COLOR

enum class Status(val state: String) {
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    DONE("Done"),
}
