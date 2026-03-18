package woowacourse.kanban.board

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import woowacourse.kanban.board.component.kanbanBoard.KanbanBoard

@Composable
fun App() {
    KanbanBoard(
        modifier = Modifier.background(color = Color.White),
    )
}
