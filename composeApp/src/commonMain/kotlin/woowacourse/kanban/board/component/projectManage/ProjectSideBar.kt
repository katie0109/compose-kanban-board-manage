package woowacourse.kanban.board.component.projectManage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.model.KanbanBoardData

@Composable
fun ProjectSideBar(
    kanbanBoardDataList: List<KanbanBoardData>,
    modifier: Modifier = Modifier,
    isSelected: (KanbanBoardData) -> Boolean,
    onClick: (KanbanBoardData) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxHeight().width(256.dp).background(color = Color.White),
    ) {
        ProjectTitle(modifier = Modifier.padding(24.dp))
        HorizontalDivider()
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            kanbanBoardDataList.forEach { kanbanBoardData ->
                KanbanBoardButton(kanbanBoardData = kanbanBoardData, isSelected = { isSelected(it) }, onClick = onClick)
            }
        }
    }
}

@Composable
private fun ProjectTitle(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        Text("프로젝트", fontSize = 18.sp, fontWeight = FontWeight.W600)
        Box(modifier = Modifier.height(4.dp))
        Text("4주차 미션 보드", fontSize = 14.sp, fontWeight = FontWeight.W400, color = Color(0xFF6a7282))
    }
}

@Composable
private fun KanbanBoardButton(
    kanbanBoardData: KanbanBoardData,
    modifier: Modifier = Modifier,
    isSelected: (KanbanBoardData) -> Boolean,
    onClick: (KanbanBoardData) -> Unit,
) {
    Button(
        modifier = Modifier.padding(bottom = 4.dp).fillMaxWidth(),
        colors = ButtonColors(
            containerColor = if (isSelected(kanbanBoardData)) Color(0xFFEEF2FF) else Color.White,
            contentColor = Color.White,
            disabledContainerColor = if (isSelected(kanbanBoardData)) Color(0xFFEEF2FF) else Color.White,
            disabledContentColor = Color.White,
        ),
        shape = RoundedCornerShape(16.dp),
        onClick = { onClick(kanbanBoardData) },
    ) {
        Text(
            text = kanbanBoardData.title,
            color = if (isSelected(kanbanBoardData)) Color(0xFF432DD7) else Color(0xFF364153),
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            modifier = modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProjectSideBarPreview() {
    val kanbanBoardDatas = remember {
        mutableListOf(
            KanbanBoardData(title = "Compose1"),
            KanbanBoardData(title = "Compose2"),
            KanbanBoardData(title = "Compose3너무너무너무너무너무너무너무너무"),
        )
    }

    ProjectSideBar(
        kanbanBoardDataList = kanbanBoardDatas,
        isSelected = { false },
        onClick = {},
    )
}
