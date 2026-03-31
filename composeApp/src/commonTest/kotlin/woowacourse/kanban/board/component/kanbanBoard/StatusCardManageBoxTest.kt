package woowacourse.kanban.board.component.kanbanBoard

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import org.junit.Test
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.domain.Tag
import woowacourse.kanban.board.theme.StatusColor

private const val DEFAULT_TITLE = "LazyColumn 컴포넌트 구현"
private const val MAX_TITLE = "너무너무 긴 제목은 한 줄까지만 노출합니다."
private const val DEFAULT_CONTENT = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다."
private const val MAX_CONTENT = "너무너무너무 긴 설명은 두 줄까지만 노출하고 말줄임표로 처리합니다 두 줄까지만 노출합니다."
private const val DEFAULT_NAME = "다이노"
private const val MAX_NAME = "너무너무너무 긴 담당자도 한 줄까지만 노출합니다."


@OptIn(ExperimentalTestApi::class)
class StatusCardManageBoxTest {

    private val boardList = listOf(
        Task(
            title = DEFAULT_TITLE,
            description = DEFAULT_CONTENT,
            tags = listOf(Tag("컴포넌트"), Tag("성능")),
            status = Status.TODO,
            nickname = DEFAULT_NAME,
        ),
        Task(
            title = DEFAULT_TITLE,
            tags = listOf(Tag("컴포넌트"), Tag("성능")),
            status = Status.TODO,
            nickname = DEFAULT_NAME,
        ),
        Task(
            title = DEFAULT_TITLE,
            description = DEFAULT_CONTENT,
            status = Status.IN_PROGRESS,
            nickname = DEFAULT_NAME,
        ),
        Task(
            title = DEFAULT_TITLE,
            status = Status.TODO,
            nickname = DEFAULT_NAME,
        ),
        Task(
            title = MAX_TITLE,
            description = MAX_CONTENT,
            tags = listOf(Tag("너무너무"), Tag("긴태그"), Tag("최대로"), Tag("5자까지"), Tag("5개제한임")),
            status = Status.DONE,
            nickname = MAX_NAME,
        ),
    )

    @Test
    fun `boardList 중에 TODO에 해당하는 카드 3개를 박스에 그린다`() = runComposeUiTest {

        val status = Status.TODO

        setContent {
            StatusCardManageBox(
                boardList = boardList,
                status = status,
                statusColor = StatusColor.getStatusColor(status),
            )
        }

        onNodeWithText("3").assertExists()
    }
}
