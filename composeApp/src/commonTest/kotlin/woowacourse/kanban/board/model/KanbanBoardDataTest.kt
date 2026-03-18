package woowacourse.kanban.board.model

import org.assertj.core.api.Assertions
import org.junit.Test
import woowacourse.kanban.board.constant.DEFAULT_CONTENT
import woowacourse.kanban.board.constant.DEFAULT_NAME
import woowacourse.kanban.board.constant.DEFAULT_TITLE
import woowacourse.kanban.board.constant.MAX_CONTENT
import woowacourse.kanban.board.constant.MAX_NAME
import woowacourse.kanban.board.constant.MAX_TITLE

class KanbanBoardDataTest {
    private val boardList = mutableListOf(
        BoardData(
            title = DEFAULT_TITLE,
            description = DEFAULT_CONTENT,
            tags = listOf(Tag("컴포넌트"), Tag("성능")),
            status = Status.TODO,
            nickname = DEFAULT_NAME,
        ),
        BoardData(
            title = DEFAULT_TITLE,
            tags = listOf(Tag("컴포넌트"), Tag("성능")),
            status = Status.TODO,
            nickname = DEFAULT_NAME,
        ),
        BoardData(
            title = DEFAULT_TITLE,
            description = DEFAULT_CONTENT,
            status = Status.IN_PROGRESS,
            nickname = DEFAULT_NAME,
        ),
        BoardData(
            title = DEFAULT_TITLE,
            status = Status.TODO,
            nickname = DEFAULT_NAME,
        ),
        BoardData(
            title = MAX_TITLE,
            description = MAX_CONTENT,
            tags = listOf(Tag("너무너무"), Tag("긴태그"), Tag("최대로"), Tag("5자까지"), Tag("5개제한임")),
            status = Status.DONE,
            nickname = MAX_NAME,
        ),
    )

    @Test
    fun `태스크 전체 개수를 알고 있다`() {
        val kanbanBoardData = KanbanBoardData(
            boardList,
        )

        Assertions.assertThat(kanbanBoardData.totalStatusCount()).isEqualTo(boardList.size)
    }

    @Test
    fun `상태(To-Do, In Progress, Done)별 태스크 개수가 노출된다`() {
        val kanbanBoardData = KanbanBoardData(
            boardList,
        )
        Assertions.assertThat(kanbanBoardData.getStatusBoard(Status.TODO).size).isEqualTo(3)
        Assertions.assertThat(kanbanBoardData.getStatusBoard(Status.IN_PROGRESS).size).isEqualTo(1)
        Assertions.assertThat(kanbanBoardData.getStatusBoard(Status.DONE).size).isEqualTo(1)
    }

    @Test
    fun `5개 중에 done이 1개라면 20%의 완료율을 계산한다`() {
        val kanbanBoardData = KanbanBoardData(
            boardList,
        )

        Assertions.assertThat(kanbanBoardData.progress()).isEqualTo(0.2f)
    }
}
