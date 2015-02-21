package ContactMangerTest;

import contactmanager.*;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.TreeSet;
import org.junit.*;

/**
 * Tests for the MeetingImpl class.
 *
 * @author James
 */
public class MeetingImplTest {
    
    ContactImpl newContact;
    Set<ContactImpl> contactList;
    GregorianCalendar date;
    MeetingImpl newMeeting;
    
    /**
     * Create a simple 'MeetingImpl' to use in all other tests.
     */
    @Before
    public void initialize(){
        // Create the list of contacts.
        newContact = new ContactImpl(1, "James Hill");
        contactList = new TreeSet<>();
        contactList.add(newContact);
        
        // Add a date for the meeting.
        date = new GregorianCalendar(2000, 01, 01);
        
        // Create the meeting.
        newMeeting = new MeetingImpl(1, contactList, date);
    }
    
}