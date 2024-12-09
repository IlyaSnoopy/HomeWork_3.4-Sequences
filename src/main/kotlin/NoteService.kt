import note.Note
import comment.Comment
import kotlin.jvm.Throws

object NoteService {

    private var notes = mutableListOf<Note>()
    private var commentIdCounter = 0L
    private var noteIdCounter = 0L

    fun clear() {
        notes.clear()
        commentIdCounter = 0L
        noteIdCounter = 0L
    }

    fun add(content: String): Note {
        val note = Note(id = ++noteIdCounter, content = content)
        notes.add(note)
        return note
    }

    fun edit(noteId: Long, newContent: String) {
        val note = getById(noteId) ?: throw IllegalArgumentException("Заметка с указанным ID не найдена")
        if (note.isDeleted) throw IllegalStateException("Заметка с указанным ID удалена")
        note.content = newContent
    }

    fun delete(noteId: Long) {
        val note = getById(noteId) ?: throw IllegalArgumentException("Заметка с указанным ID не найдена")
        if (note.isDeleted) throw IllegalStateException("Заметка с указанным ID уже удалена")
        note.isDeleted = true
        for (comment in note.comments) {
            comment.isDeleted = true
        }
    }

    fun restore(noteId: Long) {
        val note = getById(noteId) ?: throw IllegalArgumentException("Заметка с указанным ID не найдена")
        if (!note.isDeleted) throw IllegalStateException("Заметка с указанным ID не удалена (не требует восстановления)")
        note.isDeleted = false
    }

    fun createComment(noteId: Long, text: String): Comment {
        val note = getById(noteId) ?: throw IllegalArgumentException("Заметка с указанным ID не найдена")
        if (note.isDeleted) throw IllegalStateException("Заметка с указанным ID удалена")
        val comment = Comment(id = ++commentIdCounter, text = text)
        note.comments.add(comment)
        return comment
    }

    fun editComment(noteId: Long, commentId: Long, newContent: String) {
        val note = getById(noteId) ?: throw IllegalArgumentException("Заметка с указанным ID не найдена")
        if (note.isDeleted) throw IllegalStateException("Заметка с указанным ID удалена")
        var commentToEdit: Comment? = null

        for (comment in note.comments) {
            if (comment.id == commentId && !comment.isDeleted) {
                commentToEdit = comment
                break
            }
        }

        if (commentToEdit != null) {
            commentToEdit.text = newContent
        } else {
            throw IllegalStateException("Невозможно редактировать несуществующий или уже удалённый комментарий")
        }
    }

    fun deleteComment(noteId: Long, commentId: Long) {
        val note = getById(noteId) ?: throw IllegalArgumentException("Заметка с указанным ID не найдена")
        if (note.isDeleted) throw IllegalStateException("Заметка с указанным ID удалена")
        var commentToDelete: Comment? = null

        for (comment in note.comments) {
            if (comment.id == commentId && !comment.isDeleted) {
                commentToDelete = comment
                break
            }
        }

        if (commentToDelete != null) {
            commentToDelete.isDeleted = true
        } else {
            throw IllegalStateException("Невозможно удалить несуществующий или уже удалённый комментарий")
        }
    }

    fun restoreComment(noteId: Long, commentId: Long) {
        val note = getById(noteId) ?: throw IllegalArgumentException("Заметка с указанным ID не найдена")
        if (note.isDeleted) throw IllegalStateException("Заметка с указанным ID удалена")
        var commentToRestore: Comment? = null

        for (comment in note.comments) {
            if (comment.id == commentId && comment.isDeleted) {
                commentToRestore = comment
                break
            }
        }

        if (commentToRestore != null) {
            commentToRestore.isDeleted = false
        } else {
            throw IllegalStateException("Невозможно восстановить несуществующий или не удалённый (не требующий восстановления) комментарий")
        }
    }

    fun get(): List<Note> {
        val activeNotes = mutableListOf<Note>()
        for (note in notes) {
            if (!note.isDeleted) {
                activeNotes.add(note)
            }
        }
        return activeNotes
    }

    fun getById(noteId: Long): Note? {
        for (note in notes) {
            if (note.id == noteId) {
                return note
            }
        }
        throw IllegalArgumentException("Заметка с указанным ID не найдена")
    }

    fun getComments(noteId: Long): List<Comment> {
        for (note in notes) {
            if (note.id == noteId) {
                if (!note.isDeleted) {
                    return note.comments
                } else {
                    throw IllegalStateException("Заметка с указанным ID удалена")
                }
            }
        }
        throw IllegalArgumentException("Заметка с указанным ID не найдена")
    }
}