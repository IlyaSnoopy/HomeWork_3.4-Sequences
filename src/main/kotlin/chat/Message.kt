package chat

data class Message(
    val id: Double = 0.0,
    val content: String = "",
    var isRead: Boolean = false
)

