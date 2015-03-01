import org.junit.*;
import static org.junit.Assert.*;

/**
 * Tests for the ContactImpl class.
 *
 * @author James
 */
public class ContactImplTest {
    
    ContactImpl newContact;
    
    /**
     * Create a simple 'ContactImpl' to use in all other tests.
     */
    @Before
    public void initialize(){
        newContact = new ContactImpl(1, "James Hill");
    }
    
    /**
     * Test the correct ID is returned.
     */
    @Test
    public void testContactIDReturn(){
        int checkID = 1;
        int testID = newContact.getId();
        assertEquals("The following incorrect ID has been returned: " + testID, checkID, testID);
    }
    
    /**
     * Test the correct name is returned.
     */
    @Test
    public void testContactNameReturn(){
        String checkName = "James Hill";
        String testName = newContact.getName();
        assertEquals("The following incorrect name was returned: " + testName, checkName, testName);
    }
    
    /**
     * Test adding and getting notes to the ContactImpl.
     */
    @Test
    public void testAddAndGetNotes(){
        // Initial test is for contact with no notes.
        String checkNotes = "";
        String testNotes = newContact.getNotes();
        assertEquals("A blank string was not returned:", checkNotes, testNotes);
        
        // Now test that the correct notes are returned after addition of notes.
        checkNotes = "He is an extremely thorough programmer.";
        newContact.addNotes(checkNotes);
        testNotes = newContact.getNotes();
        assertEquals("The following incorrect notes were returned: " + testNotes, checkNotes, testNotes);
    }
}