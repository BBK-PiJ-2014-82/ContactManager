import contactmanager.*;
import interfaces.*;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the MeetingImpl class.
 *
 * @author James
 */
public class MeetingImplTest {
    
    Contact newContact;
    Set<Contact> contactList;
    GregorianCalendar date;
    Meeting newMeeting;
    
    /**
     * Create a simple 'MeetingImpl' to use in all other tests.
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
        newMeeting = new MeetingImpl(1, contactList, date);
    }
    
    /**
     * Test that the correct ID is returned.
     */
    @Test
    public void testMeetingIDReturn(){
        int checkID = 1;
        int testID = newMeeting.getId();
        assertEquals("An incorrect ID has been returned.", checkID, testID);
    }
    
    /**
     * Test the correct date is returned.
     */
    @Test
    public void testMeetingDateReturn(){
        Calendar checkDate = new GregorianCalendar(2000, 01, 01);
        Calendar testDate = newMeeting.getDate();
        assertEquals("An incorrect date was returned.", checkDate, testDate);
    }
    
    /**
     * Test the correct contacts are returned.
     */
    @Test
    public void testGetContacts(){
        // Create a Set for checking.
        Set checkSet = new HashSet();
        checkSet.add(newContact);
        
        // Test the 2 Sets.
        Set testSet = newMeeting.getContacts();
        assertEquals("The 2 sets are not identical.", checkSet, testSet);
        
        // Add new items to check Sets with more than 1 item.
        ContactImpl secondContact = new ContactImpl(2, "Saisai");
        checkSet.add(secondContact);
        contactList.add(secondContact);
        
        // Test the larger sets.
        testSet = newMeeting.getContacts();
        assertEquals("The 2 double sets are not identical.", checkSet, testSet);
    }
}