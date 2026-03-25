package woowacourse.kanban.board.model

data class BoardData(
    val title: String,
    val description: String = "",
    val tags: List<Tag> = emptyList(),
    val status: Status,
    val nickname: String,
) {
    val id: Int = ID_COUNT
    init {
        require(!isTitleError(title)) { "[ERROR] 제목이 비어있으면 안됩니다." }
        require(!isTagsError(tags)) { "[ERROR] 태그의 개수가 너무 많습니다." }
        addIdCount()
    }

    companion object {
        var ID_COUNT = 0
        fun addIdCount() {
            ID_COUNT += 1
        }

        const val MAX_TAGS_SIZE = 5
        fun isTitleError(title: String): Boolean = title.isBlank()
        fun isTagsError(tags: List<Tag>): Boolean = tags.size > MAX_TAGS_SIZE
    }
}
