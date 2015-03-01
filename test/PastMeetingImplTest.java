import contactmanager.ContactImpl;
import contactmanager.PastMeetingImpl;
import interfaces.Contact;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import org.junit.*;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the PastMeetingImpl class.
 * 
 * @author James
 */
public class PastMeetingImplTest {
    
    ContactImpl newContact;
    Set<Contact> contactList;
    GregorianCalendar date;
    PastMeetingImpl newMeeting;
    
    /**
     * Create a simple 'PastMeetingImpl' to use in all other tests.
     */
    @Before
    public void initialize(){
        // Create the list of contacts.
        newContact = new ContactImpl(1, "James Hill");
        contactList = new HashSet<>();
        contactList.add(newContact);
        
        // Add a date for the meeting.
        date = new GregorianCalendar(2000, 01, 01);
        
        // Create the meeting.
        newMeeting = new PastMeetingImpl(1, contactList, date);
    }
    
    /**
     * Check that returning notes from a field without having added notes
     * returns an empty string.
     */
    @Test
    public void testGettingEmptyString(){
        // Create the 2 variables to check.
        String checkNotes = "";
        String testNotes;
        
        // Get notes from initialized meeting and check.
        testNotes = newMeeting.getNotes();
        assertEquals("The notes returned are not blank.", checkNotes, testNotes);
    }
    
    /**
     * Test that the same notes are returned after adding notes.
     */
    @Test
    public void testAddingAndGettingNotes(){
        // Create the 2 variables to check.
        String checkNotes = "This meeting went extremely well.";
        String testNotes;
        
        // Add the notes to the meeting.
        newMeeting.addNotes(checkNotes);
        
        // Return and test the notes that were added to 'newMeeting'.
        testNotes = newMeeting.getNotes();
        assertEquals("The notes are not identical.", checkNotes, testNotes);
    }
}