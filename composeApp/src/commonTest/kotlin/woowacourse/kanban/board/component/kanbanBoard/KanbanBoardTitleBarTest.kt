package woowacourse.kanban.board.component.kanbanBoard

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.KanbanBoard
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.domain.Tag

private const val DEFAULT_TITLE = "LazyColumn 컴포넌트 구현"
private const val MAX_TITLE = "너무너무 긴 제목은 한 줄까지만 노출합니다."
private const val DEFAULT_CONTENT = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다."
private const val MAX_CONTENT = "너무너무너무 긴 설명은 두 줄까지만 노출하고 말줄임표로 처리합니다 두 줄까지만 노출합니다."
private const val DEFAULT_NAME = "다이노"
private const val MAX_NAME = "너무너무너무 긴 담당자도 한 줄까지만 노출합니다."


@OptIn(ExperimentalTestApi::class)
class KanbanBoardTitleBarTest {

    private val kanbanBoard = KanbanBoard(
        title = "Compose1",
        taskList = listOf(
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
        ),
    )

    @Test
    fun `새 태스크 생성 버튼을 누르면 다이얼로그가 열린다`() = runComposeUiTest {
        var showDialog = false

        setContent {
            fun onCreateClick() {
                showDialog = !showDialog
            }

            KanbanBoardTitleBar(
                progress = 0f,
                doneCount = 0,
                totalStatusCount = 0,
                onCreateClick = { onCreateClick() },
                title = kanbanBoard.title,
            )
        }

        onNodeWithText("+ 새 태스크 생성").performClick()
        waitForIdle()
        assertThat(showDialog).isEqualTo(true)
    }

    @Test
    fun `20%의 완료율을 가졌을 때 20%가 진행바에 나타난다`() = runComposeUiTest {

        setContent {
            KanbanBoardTitleBar(
                progress = kanbanBoard.progress(),
                doneCount = kanbanBoard.doneCount(),
                totalStatusCount = kanbanBoard.totalStatusCount(),
                onCreateClick = {},
                title = kanbanBoard.title,
            )
        }

        onNodeWithText("완료율: 20.0% (1/5)").assertExists()
    }
}
