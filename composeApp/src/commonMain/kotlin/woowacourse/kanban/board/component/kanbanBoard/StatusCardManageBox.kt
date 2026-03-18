package woowacourse.kanban.board.component.kanbanBoard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.component.card.KanbanBoardTemplate
import woowacourse.kanban.board.constant.DEFAULT_CONTENT
import woowacourse.kanban.board.constant.DEFAULT_NAME
import woowacourse.kanban.board.constant.DEFAULT_TITLE
import woowacourse.kanban.board.constant.MAX_CONTENT
import woowacourse.kanban.board.constant.MAX_NAME
import woowacourse.kanban.board.constant.MAX_TITLE
import woowacourse.kanban.board.constant.TODO_CARD_BOX_BORDER_COLOR
import woowacourse.kanban.board.constant.TODO_CARD_BOX_CONTENT_COLOR
import woowacourse.kanban.board.constant.TODO_CARD_BOX_TITLE_COLOR
import woowacourse.kanban.board.model.BoardData
import woowacourse.kanban.board.model.Status
import woowacourse.kanban.board.model.StatusColor
import woowacourse.kanban.board.model.Tag

@Composable
fun StatusCardManageBox(boardList: List<BoardData>, status: Status, statusColor: StatusColor, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.size(width = 320.dp, height = 700.dp),
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .background(
                    color = Color(statusColor.titleBgColor),
                )
                .padding(vertical = 12.dp, horizontal = 16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    Text(status.state, fontSize = 16.sp, fontWeight = FontWeight.W600, color = Color.White)
                }
                Box(
                    modifier = Modifier.size(width = 24.dp, height = 29.dp).background(color = Color.White, shape = CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "${boardList.count { it.status == status }}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        color = Color.Black,
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                .fillMaxSize()
                .background(
                    color = Color(statusColor.boardBgColor),
                )
                .border(
                    width = 1.dp,
                    color = Color(statusColor.boardBorderColor),
                    shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp),
                ),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 17.dp, vertical = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                boardList.indices.forEach { index ->
                    KanbanBoardTemplate(boardList[index])
                    if (index != boardList.lastIndex) Box(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StatusCardManageBoxPreview() {
    val boardList = listOf(
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
            status = Status.TODO,
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
            status = Status.TODO,
            nickname = MAX_NAME,
        ),
    )
    StatusCardManageBox(
        boardList = boardList,
        status = Status.TODO,
        statusColor = StatusColor(TODO_CARD_BOX_TITLE_COLOR, TODO_CARD_BOX_CONTENT_COLOR, TODO_CARD_BOX_BORDER_COLOR),
    )
}
