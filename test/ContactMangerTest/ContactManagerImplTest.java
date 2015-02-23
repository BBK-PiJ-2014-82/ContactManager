package ContactMangerTest;

import contactmanager.*;
import interfaces.*;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the ContactManagerImpl class.
 * 
 * @author James
 */
public class ContactManagerImplTest {
    
    ContactManagerImpl contactManager;
    Set<Contact> contactList;
    ContactImpl contact1;
    ContactImpl contact2;
    
    GregorianCalendar pastDate;
    GregorianCalendar futureDate;
    
    /**
     * Create a ContactManagerImplTest to use in all tests.
     */
    @Before
    public void initialize(){
        contactManager = new ContactManagerImpl();
        pastDate = new GregorianCalendar(2000, 01, 01);
        futureDate = new GregorianCalendar(2020, 01, 01);
    }
    
    /**
     * Test the addition of contacts.
     */
    @Test
    public void testAddAndGetContactsWithIDs(){
        // Create variables.
        String name1 = "James Hill";
        String name2 = "Saisai";
        String note1 = "Great";
        String note2 = "Best";
        String checkString;
        String testString;
        Exception checkExcept;
        Exception testExcept;
        
        // add new contacts.
        contactManager.addNewContact(name1, note1);
        contactManager.addNewContact(name2, note2);
        
        // return set using ID's.
        contactList = contactManager.getContacts(0,1);
        
        // Test the size of the set is correct.
        int checkSize = 2;
        int testSize = contactList.size();
        assertEquals("Incorrect size returned.", checkSize, testSize);
        
        // Test the 1st contact name is correct.
        checkString = contactManager.getContacts(0)..getName();
        testString = name1;
        assertEquals("Incorrect name1 returned.", checkString, testString);
        
        // Test the 1st contact note is correct.
        checkString = contactManager.getContacts(0).getNotes();
        testString = note1;
        assertEquals("Incorrect note1 returned.", checkString, testString);
        
        // Test the 2nd contact name is correct.
        checkString = contactManager.getContacts(1).getName();
        testString = name2;
        assertEquals("Incorrect name2 returned.", checkString, testString);
        
        // Test the 2nd contact note is correct.
        checkString = contactManager.getContacts(1).getNotes();
        testString = note2;
        assertEquals("Incorrect note2 returned.", checkString, testString);
        
        // Test the exception returned from null name.
        checkExcept = new NullPointerException();
        testExcept = contactManager.addNewContact(null, "Great");
        assertEquals("Incorrect exception returned.", checkExcept, testExcept);
        
        // Test the exception returned from null note.
        checkExcept = new NullPointerException();
        testExcept = contactManager.addNewContact("James", null);
        assertEquals("Incorrect exception returned.", checkExcept, testExcept);
        
        // Test the exception returned from single wrong ID.
        checkExcept = new IllegalArgumentException();
        testExcept = contactManager.getContacts(2);
        assertEquals("Incorrect exception returned.", checkExcept, testExcept);
        
        // Test the exception returned from multiple wrong IDs.
        checkExcept = new IllegalArgumentException();
        testExcept = contactManager.getContacts(2,3,4);
        assertEquals("Incorrect exception returned.", checkExcept, testExcept);
        
        // Test the exception returned from mixed right & wrong IDs.
        checkExcept = new IllegalArgumentException();
        testExcept = contactManager.getContacts(1,2);
        assertEquals("Incorrect exception returned.", checkExcept, testExcept);
    }
    
    /**
     * Test the addition and return of a future meeting.
     */
    @Test
    public void testAddFutureMeetingAndGetMeeting(){
        // add new contacts.
        contactManager.addNewContact("James Hill", "Great");
        contactManager.addNewContact("Saisai", "Best");
        
        // Create variables.
        Exception checkExcept;
        Exception testExcept;
        int checkID;
        int testID;
        Meeting checkMeet;
        Meeting testMeet;
        
        // Prepare the contactList.
        contactList = contactManager.getContacts(0,1);
        
        // Test past date results in IllegalArgumentException.
        checkExcept = new IllegalArgumentException();
        testExcept = contactManager.addFutureMeeting(contactList, pastDate);
        assertEquals("Incorrect error has been returned.", checkExcept, testExcept);
        
        // Create set that doesn't exist in ContactManager.
        Set<Contact> nonExistList = new HashSet<>();
        Contact nonExistContact = new ContactImpl(3, "Geoffrey");
        nonExistList.add(nonExistContact);
        
        // Test unknown contact results in IllegalArgumentException.
        checkExcept = new IllegalArgumentException();
        testExcept = contactManager.addFutureMeeting(nonExistList, futureDate);
        assertEquals("Incorrect error has been returned.", checkExcept, testExcept);
        
        // Create the future meeting & test the ID is returned correctly.
        checkID = 1;
        testID = contactManager.addFutureMeeting(contactList, futureDate);
        assertEquals("Incorrect meeting ID has been returned.", checkID, testID);
        
        // Create and test the FutureMeeting is added to the contact manager.
        checkMeet = new MeetingImpl(1, contactList, futureDate);
        testMeet = contactManager.getMeeting(1);
        assertEquals("Incorrect ID has been returned.", checkMeet, testMeet);
        
        // Test getting non-existent meeting.
        checkMeet = null;
        testMeet = contactManager.getMeeting(2);
        assertEquals("A null is not returned by getMeeting.", checkMeet, testMeet);
    }
    
}