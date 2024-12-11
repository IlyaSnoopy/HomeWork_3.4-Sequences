package chat

data class Chat(
    val userId: Long,
    val messages: MutableList<Message> = mutableListOf()
) {
    val unreadCount: Int
        get() = messages.count { !it.isRead }

    fun addMessage(message: Message) {
        messages.add(message)
    }

    fun getLastMessageOrDefault(): String {
        return if (messages.isEmpty()) {
            "нет сообщений"
        } else {
            messages.last().content
        }
    }

    fun getMessages(count: Int): List<Message> {
        messages.forEach { it.isRead = true }
        return messages.takeLast(count)
    }

    fun deleteMessage(messageId: Double) {
        messages.removeIf { it.id == messageId }
    }
}