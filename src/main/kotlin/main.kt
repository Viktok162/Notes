import WallService.add
import WallService.comments
import WallService.createComment
import WallService.delete
import WallService.deleteComment
import WallService.notes
import WallService.notesOwner
import java.util.*

fun main() {
    val note = Note(oid = 101, title = "Space news",
        text = "Yuri Gagarin became the first person in world history to fly into space")
    add(note)
    add(note)
    notes[0].showNote()
    add(note= Note(oid = 262, title = "Sports news",
        text = "Alexander Ovechkin scored the 895th goal and is now the best sniper in NHL history"))
    notes.last().showNote()
    println(notes.size)

    val comment = Comment(uid = 1234, nid = 1, message = "The good news!", dateComment = Date())
    createComment(comment)
    comments.last().showComment()
    WallService.editComment(1,"The best news!")
    comments[0].showComment()
    deleteComment(1)

    delete(1)

    comments[0].showComment()

    WallService.edit(2, "Super news", "The Russian and US national hockey teams will play soon", 1)
    notes[0].showNote()

    get(101)
    println(notesOwner.size)
    notesOwner[0].showNote()

}

data class Note(
    val nid: Int = 0,           // note
    val oid: Int = 0,           // owner of note
    var title: String = "Article title",
    var text: String = "Text of article",
    val date: Date = Date(),
    var privacy: Int = 0,
    val comments: Int = 0
    ) {
    fun showNote() {
        println("Данные объекта Note: nid = $nid, oid = $oid, title = $title, text = $text, date = $date")
    }
}

data class Comment(
    val cid: Int = 0,                   // comment
    val uid: Int,                       // author of comment
    val nid: Int = 0,                   // note
    var dateComment: Date = Date(),     // date of comment
    var message: String = "Comments",   //text of comment
    var commentIsExist: Boolean = true
) {
    fun showComment() {
        if (commentIsExist) println("Данные объекта Comment: cid = $cid, uid = $uid, nid = $nid, message = $message, date = $dateComment")
        else println("Этот комментарий удалён")
    }
}
class NoteNotFoundException(message: String) : RuntimeException(message)
class CommentNotFoundException(message: String) : RuntimeException(message)
class NoteOwnerNotFoundException(message: String) : RuntimeException(message)

object WallService {
    var notes = mutableListOf<Note>()
    var comments = mutableListOf<Comment>()
    var notesOwner = mutableListOf<Note>()

    private var counterNote: Int = 0
    private var counterComment: Int = 0

    fun add(note: Note): Int {
        val newNote = note.copy(nid = ++counterNote)
        notes.add(newNote)
        return newNote.nid
    }

    fun createComment(comment: Comment): Int {
        notes.find { it.nid == comment.nid } ?: throw NoteNotFoundException("Такой заметки не существует")
        val newComment = comment.copy(cid = ++counterComment)
        comments.add(newComment)
        return newComment.cid
    }

    fun delete(noteId: Int): Int {
        notes.firstOrNull {it.nid == noteId} ?: throw
            NoteNotFoundException("Заметки с ID $noteId не существует")
        notes.removeAt(notes.indexOfFirst {it.nid == noteId})  // removeIf

        comments.forEach {
            if (it.nid == noteId) it.commentIsExist = false
        }
        return 1
    }

    fun deleteComment(commentId: Int): Boolean {
        val commentToDel = comments.firstOrNull { it.cid == commentId } ?: throw
            CommentNotFoundException("Комментария с ID $commentId не существует")
        commentToDel.commentIsExist = false
        return true
    }

    fun edit(noteId: Int, titleNew: String, textNew: String, privacyNew: Int): Boolean {
        val noteEdit = notes.firstOrNull { it.nid == noteId } ?: throw
            NoteNotFoundException("Заметки с ID $noteId не существует")
        noteEdit.title = titleNew
        noteEdit.text = textNew
        noteEdit.privacy = privacyNew

        return true
    }

    fun editComment(commentId: Int, messageNew: String): Boolean {
        val commentEdit = comments.firstOrNull { it.cid == commentId } ?: throw
            CommentNotFoundException("Комментария с ID $commentId не существует")
        commentEdit.message = messageNew
        commentEdit.dateComment = Date()

        return true
    }
}
    fun get(noteOid: Int): Boolean {
        notesOwner = notes.filter {it.oid == noteOid}.toMutableList()
        if (notesOwner.isEmpty()) {
            throw NoteOwnerNotFoundException("Заметок этого автора не найдено")
        }
        return true
    }

/*
    fun getById(noteId: Int, userId: , offset: , count: ):  {
    }

    fun getComments(noteId: Int, userId: , offset: , count: ): List<Comment> {
    }

    fun restoreComment(cid: Int, oid: Int): Int {
    }

 */
 