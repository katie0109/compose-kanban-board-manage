package woowacourse.kanban.board.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.Task
import woowacourse.kanban.board.domain.Status
import woowacourse.kanban.board.domain.Tag

class DialogState {
    var titleInputValue by mutableStateOf("")
    var descriptionInputValue by mutableStateOf("")
    var tagsInputValue by mutableStateOf("")
    var statusValue by mutableStateOf(Status.TODO)
    var nameValue by mutableStateOf("다이노")

    var isTitleError by mutableStateOf(false)
    var isTagsError by mutableStateOf(false)
    var createdTask by mutableStateOf<Task?>(null)
    var mode by mutableStateOf(DialogMode.CREATE)

    fun titleOnValueChange(value: String) {
        titleInputValue = value
        isTitleError = Task.isTitleError(titleInputValue)
    }

    fun descriptionOnValueChange(value: String) {
        descriptionInputValue = value
    }

    fun tagsOnValueChange(value: String) {
        tagsInputValue = value
        val tags = if (tagsInputValue.isNotBlank()) tagsInputValue.split(",") else emptyList()
        isTagsError = tags.any { Tag.isTagError(it) } || Task.isTagsError(tags.map { Tag(it) })
    }

    fun statusOnValueChange(status: Status) {
        statusValue = status
    }

    fun isSelectedStatus(status: Status): Boolean {
        return statusValue == status
    }

    fun nameOnValueChange(name: String) {
        nameValue = name
    }

    fun isSelectedName(name: String): Boolean {
        return nameValue == name
    }

    fun onTaskCreate() {
        createdTask = Task(
            title = titleInputValue,
            description = descriptionInputValue,
            tags = if (tagsInputValue.isNotBlank()) {
                tagsInputValue.split(",").map { Tag(it) }
            } else emptyList(),
            status = statusValue,
            nickname = nameValue,
        )
    }
}

enum class DialogMode {
    CREATE, EDIT
}
