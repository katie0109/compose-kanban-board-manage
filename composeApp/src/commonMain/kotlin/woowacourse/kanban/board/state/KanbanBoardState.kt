package woowacourse.kanban.board.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import woowacourse.kanban.board.domain.Status

class KanbanBoardState {

    var showDialog by mutableStateOf(false)
    var isShowSnackBar by mutableStateOf(false)
    var text by  mutableStateOf("새로운 태스크가 생성되었습니다.")

    //드래그 앤 드롭 상태
    var draggedTaskId by mutableStateOf<Int?>(null)
    var currentDragPosition by mutableStateOf<Offset?>(null)
    val columnBounds = mutableStateMapOf<Status, Rect>()
    var draggedTaskSourceStatus by mutableStateOf<Status?>(null)
}
