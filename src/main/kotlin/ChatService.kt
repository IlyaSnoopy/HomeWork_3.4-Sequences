import chat.Chat
import chat.Message

object ChatService {
    private val chats = mutableMapOf<Pair<Long, Long>, Chat>()

    fun clear() {
        chats.clear()
    }

    fun createChat(userId1: Long, userId2: Long) {
        val chatKey = Pair(userId1, userId2)
        chats.putIfAbsent(chatKey, Chat(userId2))
    }

    fun deleteChat(userId1: Long, userId2: Long) {
        val chatKey = Pair(userId1, userId2)
        chats.remove(chatKey)
    }

    fun sendMessage(senderId: Long, receiverId: Long, messageContent: String) {
        createChat(senderId, receiverId)
        val messageId = "$senderId.${System.currentTimeMillis()}".toDouble()
        val message = Message(messageId, messageContent)
        chats[Pair(senderId, receiverId)]?.addMessage(message)
    }

    fun getChats(userId: Long): List<Chat> {
        return chats.filter { it.key.first == userId || it.key.second == userId }.map { it.value }.toList()
    }

    fun getUnreadChatsCount(userId: Long): Int {
        return getChats(userId).count { it.unreadCount > 0 }
    }

    fun getLastMessages(userId: Long): List<String> {
        return getChats(userId)
            .asSequence()
            .map { it.getLastMessageOrDefault() }
            .toList()
    }

    fun getMessagesFromChat(userId1: Long, userId2: Long, count: Int): List<Message> {
        return chats[Pair(userId1, userId2)]?.getMessages(count) ?: emptyList()
    }

    fun deleteMessage(userId1: Long, userId2: Long, messageId: Double) {
        chats[Pair(userId1, userId2)]?.deleteMessage(messageId)
    }
}