import WallService.add
import WallService.createComment
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        WallService.resetCounters()
        WallService.resetNotes()
        WallService.resetComments()
    }

    @Test
    fun add_Test_True() {
        println(1)
        val note= Note(oid = 262, title = "Sports news",
            text = "Alexander Ovechkin scored the 895th goal")
        assertTrue(add(note) == 1)
    }

    @Test
    fun createComment_True() {
        println(2)
        add(note= Note(oid = 262, title = "Sports news",
            text = "Alexander Ovechkin is now the best sniper in NHL history"))
        assertTrue(createComment(comment = Comment(uid=12, nid=1)) == 1)
    }

    @Test
    fun createComment_False() {
        println(3)
        add(note= Note(oid = 262, title = "Sports news",
            text = "Alexander Ovechkin is now the best sniper in NHL history"))
        val result = createComment(comment = Comment(uid = 12, nid = 1))
        assertFalse(result != 1)

    }
    @Test (expected = NoteNotFoundException :: class)
    fun createComment_Exception (){
        println(4)
        add(note= Note(oid = 262, title = "Sports news",
            text = "Alexander Ovechkin is now the best sniper in NHL history"))
        createComment(comment= Comment(uid = 12, nid = 0))
    }
}