import comment.Comment
import note.Note
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NoteServiceTest {

    @Before
    fun clearBeforeTest() {
        NoteService.clear()
    }

    @Test
    fun addNote() {
        val service = NoteService
        val note = service.add("test add note")

        assertEquals(note.content, "test add note")
    }

    @Test
    fun editValidNote() {
        val service = NoteService
        val note = service.add("test add note")
        service.edit(note.id, "test edit note")

        assertEquals(note.content, "test edit note")
    }

    @Test
    fun throwExceptionWhenEditNoteWithNotFoundId() {

        assertThrows(IllegalArgumentException::class.java) {
            NoteService.edit(1, "test edit note")
        }
    }

    @Test
    fun throwExceptionWhenEditNoteWithIsDeleted() {
        val service = NoteService
        var note = service.add("test add note")
        note.isDeleted = true

        assertThrows(IllegalStateException::class.java) {
            service.edit(note.id, "test edit notes")
        }
    }

    @Test
    fun deleteValidNote() {
        val service = NoteService
        val note = service.add("test add note")
        service.delete(note.id)

        assertTrue(note.isDeleted)
    }

    @Test
    fun throwExceptionWhenDeleteNoteWithNotFoundId() {

        assertThrows(IllegalArgumentException::class.java) {
            NoteService.delete(1)
        }
    }

    @Test
    fun throwExceptionWhenDeleteNoteWithIsDeleted() {
        val service = NoteService
        var note = service.add("test add note")
        note.isDeleted = true

        assertThrows(IllegalStateException::class.java) {
            service.delete(note.id)
        }
    }

    @Test
    fun restoreValidNote() {
        val service = NoteService
        val note = service.add("test add note")
        note.isDeleted = true
        service.restore(note.id)

        assertFalse(note.isDeleted)
    }

    @Test
    fun throwExceptionWhenRestoreNoteWithNotFoundId() {

        assertThrows(IllegalArgumentException::class.java) {
            NoteService.restore(1)
        }
    }

    @Test
    fun throwExceptionWhenRestoreNoteWithNotIsDeleted() {
        val service = NoteService
        val note = service.add("test add note")

        assertThrows(IllegalStateException::class.java) {
            service.restore(note.id)
        }
    }

    @Test
    fun CreateCommentToTheValidNote() {
        val service = NoteService
        val note = service.add("test add note")
        val comment = service.createComment(note.id, "test create comment")

        assertEquals(comment.text, "test create comment")
    }

    @Test
    fun throwExceptionWhenCreateCommentToTheNoteWithNotFoundId() {

        assertThrows(IllegalArgumentException::class.java) {
            NoteService.createComment(1, "test create comment")
        }
    }

    @Test
    fun throwExceptionWhenCreateCommentToTheNoteWithIsDeleted() {
        val service = NoteService
        val note = service.add("test add note")
        note.isDeleted = true

        assertThrows(IllegalStateException::class.java) {
            service.createComment(note.id, "test create comment")
        }
    }

    @Test
    fun EditValidCommentToTheValidNote() {
        val service = NoteService
        val note = service.add("test add note")
        val comment = service.createComment(note.id, "test create comment")
        service.editComment(note.id, comment.id, "test edit comment")

        assertEquals(comment.text, "test edit comment")
    }

    @Test
    fun throwExceptionWhenEditCommentToTheNoteWithNotFoundId() {
        val service = NoteService
        val note = service.add("test add note")
        val comment = service.createComment(note.id, "test create comment")

        assertThrows(IllegalArgumentException::class.java) {
            service.editComment(10, comment.id, "test edit comment")
        }
    }

    @Test
    fun throwExceptionWhenEditCommentToTheNoteWithIsDeleted() {
        val service = NoteService
        val note = service.add("test add note")
        val comment = service.createComment(note.id, "test create comment")
        note.isDeleted = true

        assertThrows(IllegalStateException::class.java) {
            service.editComment(note.id, comment.id, "test edit comment")
        }
    }

    @Test
    fun throwExceptionWhenEditCommentWithCommentNotFound() {
        val service = NoteService
        val note = service.add("test add note")

        assertThrows(IllegalStateException::class.java) {
            service.editComment(note.id, 1, "test edit comment")
        }
    }

    @Test
    fun throwExceptionWhenEditCommentWithCommentIsDeleted() {
        val service = NoteService
        val note = service.add("test add note")
        val comment = service.createComment(note.id, "test create comment")
        comment.isDeleted = true

        assertThrows(IllegalStateException::class.java) {
            service.editComment(note.id, comment.id, "test edit comment")
        }
    }

    @Test
    fun deleteValidCommentToTheValidNote() {
        val service = NoteService
        val note = service.add("test add note")
        val comment = service.createComment(note.id, "test create comment")
        service.deleteComment(note.id, comment.id)

        assertTrue(comment.isDeleted)
    }

    @Test
    fun throwExceptionWhenDeleteCommentToTheNoteWithNotFoundId() {
        val service = NoteService
        val note = service.add("test add note")
        val comment = service.createComment(note.id, "test create comment")

        assertThrows(IllegalArgumentException::class.java) {
            service.deleteComment(10, comment.id)
        }
    }

    @Test
    fun throwExceptionWhenDeleteCommentToTheNoteWithIsDeleted() {
        val service = NoteService
        val note = service.add("test add note")
        val comment = service.createComment(note.id, "test create comment")
        note.isDeleted = true

        assertThrows(IllegalStateException::class.java) {
            service.deleteComment(note.id, comment.id)
        }
    }

    @Test
    fun throwExceptionWhenDeleteCommentWithCommentNotFound() {
        val service = NoteService
        val note = service.add("test add note")

        assertThrows(IllegalStateException::class.java) {
            service.deleteComment(note.id, 1)
        }
    }

    @Test
    fun throwExceptionWhenDeleteCommentWithCommentIsDeleted() {
        val service = NoteService
        val note = service.add("test add note")
        val comment = service.createComment(note.id, "test create comment")
        comment.isDeleted = true

        assertThrows(IllegalStateException::class.java) {
            service.deleteComment(note.id, comment.id)
        }
    }

    @Test
    fun RestoreDeletedCommentToTheValidNote() {
        val service = NoteService
        val note = service.add("test add note")
        val comment = service.createComment(note.id, "test create comment")
        comment.isDeleted = true
        service.restoreComment(note.id, comment.id)

        assertFalse(comment.isDeleted)
    }

    @Test
    fun throwExceptionWhenRestoreCommentToTheNoteWithNotFoundId() {
        val service = NoteService
        val note = service.add("test add note")
        val comment = service.createComment(note.id, "test create comment")
        comment.isDeleted = true

        assertThrows(IllegalArgumentException::class.java) {
            service.restoreComment(10, comment.id)
        }
    }

    @Test
    fun throwExceptionWhenRestoreCommentToTheNoteWithIsDeleted() {
        val service = NoteService
        val note = service.add("test add note")
        val comment = service.createComment(note.id, "test create comment")
        service.delete(note.id)

        assertThrows(IllegalStateException::class.java) {
            service.restoreComment(note.id, comment.id)
        }
    }

    @Test
    fun throwExceptionWhenRestoreCommentWithCommentNotFound() {
        val service = NoteService
        val note = service.add("test add note")

        assertThrows(IllegalStateException::class.java) {
            service.restoreComment(note.id, 1)
        }
    }

    @Test
    fun throwExceptionWhenRestoreCommentWithCommentNotIsDeleted() {
        val service = NoteService
        val note = service.add("test add note")
        val comment = service.createComment(note.id, "test create comment")

        assertThrows(IllegalStateException::class.java) {
            service.restoreComment(note.id, comment.id)
        }
    }

    @Test
    fun getNotes() {
        val service = NoteService

        val notes = mutableListOf<Note>()
        val note1 = Note(id = 1, content = "test1")
        val note2 = Note(id = 2, content = "test2")
        notes += note1
        notes += note2

        service.add("test1")
        service.add("test2")
        val resultNotes = service.get()

        assertEquals(resultNotes, notes)
    }

    @Test
    fun getValidNotesById() {
        val service = NoteService
        val resultNote = service.add("test add note")

        val note = Note(id = 1, content = "test add note")

        assertEquals(service.getById(resultNote.id), note)
    }

    @Test
    fun getNotFoundIdNotesById() {
        val service = NoteService

        assertThrows(IllegalArgumentException::class.java) {
          service.getById(1)
        }
    }

    @Test
    fun getCommentsToTheValidNote() {
        val service = NoteService
        val note = service.add("test add note")
        val resultComment = service.createComment(note.id, "test create comment")

        val comments: MutableList<Comment> = mutableListOf()
        val comment = Comment(id = 1, text = "test create comment")
        comments += comment

        assertEquals(service.getComments(note.id), comments)
    }

    @Test
    fun throwExceptionWhengetCommentsToTheNoteWithIsDeleted() {
        val service = NoteService
        val note = service.add("test add note")
        val comment = service.createComment(note.id, "test create comment")
        service.delete(note.id)

        assertThrows(IllegalStateException::class.java) {
            service.getComments(note.id)
        }
    }

    @Test
    fun throwExceptionWhengetCommentsToTheNoteWithNotFoundId() {
        val service = NoteService

        assertThrows(IllegalArgumentException::class.java) {
            service.getComments(1)
        }
    }
}