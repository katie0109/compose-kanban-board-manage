package woowacourse.kanban.board.domain

data class Task(
    val id: Int = nextId(),
    val title: String,
    val description: String = "",
    val tags: List<Tag> = emptyList(),
    val status: Status,
    val nickname: String,
) {
    init {
        require(!isTitleError(title)) { "[ERROR] 제목이 비어있으면 안됩니다." }
        require(!isTagsError(tags)) { "[ERROR] 태그의 개수가 너무 많습니다." }
    }

    companion object {
        var ID_COUNT = 0

        private fun nextId(): Int {
            val current = ID_COUNT
            ID_COUNT += 1
            return current
        }

        const val MAX_TAGS_SIZE = 5
        fun isTitleError(title: String): Boolean = title.isBlank()
        fun isTagsError(tags: List<Tag>): Boolean = tags.size > MAX_TAGS_SIZE
    }
}

data class Tag(val text: String) {
    init {
        require(!isTagError(text)) { "[ERROR] 태그에서 오류가 발생했습니다." }
    }

    companion object {
        const val MAX_TAG_LENGTH = 5
        fun isTagError(tag: String): Boolean = tag.length > MAX_TAG_LENGTH || tag.isBlank() || tag.count { it.toString().isBlank() } > 0
    }
}

enum class Status(val state: String) {
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    DONE("Done"),
}
