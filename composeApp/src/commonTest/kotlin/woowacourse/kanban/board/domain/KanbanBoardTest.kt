package woowacourse.kanban.board.domain

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import woowacourse.kanban.board.component.DEFAULT_CONTENT
import woowacourse.kanban.board.component.DEFAULT_NAME
import woowacourse.kanban.board.component.DEFAULT_TITLE
import woowacourse.kanban.board.component.MAX_CONTENT
import woowacourse.kanban.board.component.MAX_NAME
import woowacourse.kanban.board.component.MAX_TITLE

class KanbanBoardTest {
    private val taskList = listOf(
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

    private val kanbanBoard = taskList.fold(KanbanBoard(title = "Compose1")) { board, task ->
        board.addTask(task)
    }

    private val targetCard = kanbanBoard.taskList[0]

    @Test
    fun `태스크 전체 개수를 알고 있다`() {

        Assertions.assertThat(kanbanBoard.totalStatusCount()).isEqualTo(taskList.size)
    }

    @Test
    fun `상태(To-Do, In Progress, Done)별 태스크 개수가 노출된다`() {

        Assertions.assertThat(kanbanBoard.getStatusTask(Status.TODO).size).isEqualTo(3)
        Assertions.assertThat(kanbanBoard.getStatusTask(Status.IN_PROGRESS).size).isEqualTo(1)
        Assertions.assertThat(kanbanBoard.getStatusTask(Status.DONE).size).isEqualTo(1)
    }

    @Test
    fun `5개 중에 done이 1개라면 20%의 완료율을 계산한다`() {

        Assertions.assertThat(kanbanBoard.progress()).isEqualTo(0.2f)
    }

    @Test
    fun `상태를 To-Do에서 In Progress으로 옮겼을 때 객체의 상태가 변경된다`() {
        val changeKanbanBoardData = kanbanBoard.moveTaskStatus(taskId = targetCard.id, targetStatus = Status.IN_PROGRESS)

        assertThat(changeKanbanBoardData.taskList[0].status).isEqualTo(Status.IN_PROGRESS)
    }

    @Test
    fun `상태를 To-Do에서 Done으로 옮겼을 때 doneCount가 증가한다`() {
        val changeKanbanBoardData = kanbanBoard.moveTaskStatus(taskId = targetCard.id, targetStatus = Status.DONE)

        assertThat(changeKanbanBoardData.doneCount()).isEqualTo(2)
    }

    @Test
    fun `상태를 To-Do에서 In Progress로 변경했을 때 완료율은 변하지 않는다`() {
        val changeKanbanBoardData = kanbanBoard.moveTaskStatus(taskId = targetCard.id, targetStatus = Status.IN_PROGRESS)

        assertThat(changeKanbanBoardData.progress()).isEqualTo(kanbanBoard.progress())
    }

    @Test
    fun `To Do 태스크를 Done으로 옮겼을 때 태스크의 상태가 DONE으로 변경된다`() {
        val targetTask = kanbanBoard.taskList[0]
        val updated = kanbanBoard.moveTaskStatus(taskId = targetTask.id, targetStatus = Status.DONE)

        assertThat(updated.taskList[0].status).isEqualTo(Status.DONE)
    }

    @Test
    fun `태스크를 Done으로 옮겼을 때 완료율이 변경된다`() {
        val targetTask = kanbanBoard.taskList[0]
        val updated = kanbanBoard.moveTaskStatus(taskId = targetTask.id, targetStatus = Status.DONE)

        assertThat(updated.progress()).isEqualTo(0.4f)
    }

    @Test
    fun `태스크를 Done에서 To Do로 옮겼을 때 완료율이 변경된다`() {
        val doneTask = kanbanBoard.taskList[4]
        val updated = kanbanBoard.moveTaskStatus(taskId = doneTask.id, targetStatus = Status.TODO)

        assertThat(updated.progress()).isEqualTo(0.0f)
    }
}
