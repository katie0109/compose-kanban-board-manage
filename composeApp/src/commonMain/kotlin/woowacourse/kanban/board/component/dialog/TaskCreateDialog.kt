package woowacourse.kanban.board.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.state.DialogState

@Composable
fun TaskCreateDialog(
    modifier: Modifier = Modifier,
    statuses: List<Status>,
    names: List<String>,
    onTaskCreate: (DialogState) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val dialogState = remember { DialogState() }

    val isCreateError = dialogState.isTitleError || dialogState.isTagsError || dialogState.titleInputValue.isBlank()

    Column(
        modifier = modifier
            .background(color = Color.White)
            .verticalScroll(rememberScrollState()),
    ) {
        DialogBar(
            modifier = Modifier.padding(
                vertical = 28.dp,
                horizontal = 24.dp,
            )
                .fillMaxWidth(),
            onClick = onDismissRequest,
        )
        HorizontalDivider()
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            CommonTextColumn(
                modifier = Modifier.fillMaxWidth(),
                title = "제목 *",
                content = dialogState.titleInputValue,
                onValueChange = { dialogState.titleOnValueChange(it) },
                isError = dialogState.isTitleError,
                placeholderText = "태스크 제목을 입력하세요",
            )
            CommonTextColumn(
                modifier = Modifier.fillMaxWidth().height(116.dp),
                title = "설명",
                content = dialogState.descriptionInputValue,
                onValueChange = { dialogState.descriptionOnValueChange(it) },
                placeholderText = "태스크에 대한 자세한 설명을 입력하세요",
            )
            CommonTextColumn(
                modifier = Modifier.fillMaxWidth(),
                title = "태그 *",
                content = dialogState.tagsInputValue,
                onValueChange = { dialogState.tagsOnValueChange(it) },
                isError = dialogState.isTagsError,
                placeholderText = "태그를 쉼표로 구분하여 입력하세요 (예: 버그, 긴급)",
                isSupportingText = true,
            )
            CommonButtonColumn(
                header = "상태 *",
                items = statuses,
                isSelected = { dialogState.isSelectedStatus(it) },
                onValueChange = { dialogState.statusOnValueChange(it) },
            ) { status, isSelected, onClick ->
                StatusButton(status = status, isSelected = isSelected, onClick = onClick)
            }
            CommonButtonColumn(
                header = "담당자 *",
                items = names,
                isSelected = { dialogState.isSelectedName(it) },
                onValueChange = { dialogState.nameOnValueChange(it) },
            ) { name, isSelected, onClick ->
                CoachButton(name = name, isSelected = isSelected, onClick = onClick)
            }
            HorizontalDivider()
            FooterRow(
                onCancel = onDismissRequest,
                onCreate = { onTaskCreate(dialogState) },
                isCreateError = isCreateError,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskCreateDialogPreview(){
    TaskCreateDialog(
        onTaskCreate = {},
        onDismissRequest = {},
        statuses = Status.entries,
        names = listOf("다이노", "페임스"),
        modifier = Modifier
    )
}
