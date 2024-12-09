package note

import comment.Comment

data class Note(
    val id: Long = 0,
    var content: String = "",
    val comments: MutableList<Comment> = mutableListOf(),
    var isDeleted: Boolean = false
)