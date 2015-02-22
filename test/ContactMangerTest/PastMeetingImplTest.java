package ContactMangerTest;

import contactmanager.ContactImpl;
import contactmanager.PastMeetingImpl;
import interfaces.Contact;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;

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
    
}