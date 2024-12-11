import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ChatServiceTest {

    @Before
    fun clearBeforeTest () {
       ChatService.clear()
    }

    @Test
    fun testCreateChat() {
        val chatService = ChatService
        chatService.createChat(1L, 2L)

        assertEquals(1, chatService.getChats(1L).size)
        assertEquals(1, chatService.getChats(2L).size)
    }

    @Test
    fun testDeleteChat() {
        val chatService = ChatService
        chatService.createChat(1L, 2L)
        chatService.deleteChat(1L, 2L)

        assertTrue(chatService.getChats(1L).isEmpty())
        assertTrue(chatService.getChats(2L).isEmpty())
    }

    @Test
    fun testSendMessage() {
        val chatService = ChatService
        val message = chatService.sendMessage(1L, 2L, "Hello")
        val messages = chatService.getMessagesFromChat(1L, 2L, 1)

        assertEquals(1, messages.size)
        assertEquals("Hello", messages[0].content)
        assertTrue(messages[0].isRead)
    }

    @Test
    fun testGetChats() {
        val chatService = ChatService
        chatService.createChat(1L, 2L)
        chatService.createChat(1L, 3L)

        assertEquals(2, chatService.getChats(1L).size)
        assertEquals(1, chatService.getChats(2L).size)
        assertEquals(1, chatService.getChats(3L).size)
    }

    @Test
    fun testGetUnreadChatsCount() {
        val chatService = ChatService
        chatService.createChat(1L, 2L)
        chatService.sendMessage(1L, 2L, "Hello")

        assertEquals(1, chatService.getUnreadChatsCount(2L))

        chatService.getMessagesFromChat(1L, 2L, 1)

        assertEquals(0, chatService.getUnreadChatsCount(2L))
    }

    @Test
    fun testGetLastMessages() {
        val chatService = ChatService
        chatService.sendMessage(1L, 2L, "Hello")
        chatService.sendMessage(1L, 3L, "Hi")
        val lastMessages = chatService.getLastMessages(1L)

        assertEquals(2, lastMessages.size)
        assertEquals("Hello", lastMessages[0])
        assertEquals("Hi", lastMessages[1])
    }

    @Test
    fun testGetMessagesFromChat() {
        val chatService = ChatService
        chatService.sendMessage(1L, 2L, "Message 1")
        chatService.sendMessage(1L, 2L, "Message 2")
        val messages = chatService.getMessagesFromChat(1L, 2L, 2)

        assertEquals(2, messages.size)
        assertEquals("Message 1", messages[0].content)
        assertEquals("Message 2", messages[1].content)
    }

    @Test
    fun testDeleteMessage() {
        val chatService = ChatService
        chatService.sendMessage(1L, 2L, "Message to delete")
        val messagesBeforeDelete = chatService.getMessagesFromChat(1L, 2L, 1)

        assertEquals(1, messagesBeforeDelete.size)

        val messageId = messagesBeforeDelete[0].id
        chatService.deleteMessage(1L, 2L, messageId)
        val messagesAfterDelete = chatService.getMessagesFromChat(1L, 2L, 1)

        assertTrue(messagesAfterDelete.isEmpty())
    }
}

