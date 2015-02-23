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
        Contact[] contactArray;
        
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
        contactArray = contactManager.getContacts(0).toArray(new Contact[1]);
        checkString = contactArray[0].getName();
        testString = name1;
        assertEquals("Incorrect name1 returned.", checkString, testString);
        
        // Test the 1st contact note is correct.
        checkString = contactArray[0].getNotes();
        testString = note1;
        assertEquals("Incorrect note1 returned.", checkString, testString);
        
        // Test the 2nd contact name is correct.
        contactArray = contactManager.getContacts(1).toArray(new Contact[1]);
        checkString = contactArray[0].getName();
        testString = name2;
        assertEquals("Incorrect name2 returned.", checkString, testString);
        
        // Test the 2nd contact note is correct.
        checkString = contactArray[0].getNotes();
        testString = note2;
        assertEquals("Incorrect note2 returned.", checkString, testString);
    }
    
    /**
     * Test null name throws correct exception when adding contact.
     */
    @Test (expected = NullPointerException.class)
    public void addContactThrowsExceptionWithNullName(){
        contactManager.addNewContact(null, "Great");
    }
    
    /**
     * Test null note throws correct exception when adding contact.
     */
    @Test (expected = NullPointerException.class)
    public void addContactThrowsExceptionWithNullNote(){
        contactManager.addNewContact("James", null);
    }
    
    /**
     * Test the exception returned from single wrong ID.
     */
    @Test (expected = IllegalArgumentException.class)
    public void checkingSetForNonExistentIDThrowsException(){
        contactManager.addNewContact("James Hill", "Great");
        contactManager.addNewContact("赛赛", "太好了");
        contactManager.getContacts(2);
    }
    
    /**
     * Test the exception returned from multiple wrong IDs.
     */
    @Test (expected = IllegalArgumentException.class)
    public void checkSetForMultipleNonExistentIDThrowsException(){
        contactManager.addNewContact("James Hill", "Great");
        contactManager.addNewContact("赛赛", "太好了");
        contactManager.getContacts(2,3,4);
    }
    
    /**
     * Test the exception returned from mixed right & wrong IDs.
     */
    @Test (expected = IllegalArgumentException.class)
    public void checkSetForMixedExistenceIDThrowsException(){
        contactManager.addNewContact("James Hill", "Great");
        contactManager.addNewContact("赛赛", "太好了");
        contactManager.getContacts(1,2);
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
        int checkID;
        int testID;
        Meeting checkMeet;
        Meeting testMeet;
        Calendar checkDate;
        Calendar testDate;
        
        // Prepare the contactList.
        contactList = contactManager.getContacts(0,1);
        
        // Create the future meeting & test the ID is returned correctly.
        checkID = 0;
        testID = contactManager.addFutureMeeting(contactList, futureDate);
        assertEquals("Incorrect meeting ID has been returned.", checkID, testID);
        
        // Check the date on added meeting is correct.
        checkDate = futureDate;
        testDate = contactManager.getMeeting(0).getDate();
        assertEquals("Incorrect date has been returned.", checkDate, testDate);
        
        // Create and test the FutureMeeting is added to the contact manager.
        checkMeet = new MeetingImpl(0, contactList, futureDate);
        testMeet = contactManager.getMeeting(0);
        assertEquals("Incorrect ID has been returned.", checkMeet, testMeet);
        
        // Test getting non-existent meeting.
        checkMeet = null;
        testMeet = contactManager.getMeeting(2);
        assertEquals("A null is not returned by getMeeting.", checkMeet, testMeet);
    }
    
    /**
     * Test add future meeting with past date throws IllegalArgumentException.
     */
    @Test (expected = IllegalArgumentException.class)
    public void addFutureMeetingThrowsExceptionWithPastDate(){
        // add new contacts.
        contactManager.addNewContact("James Hill", "Great");
        contactManager.addNewContact("Saisai", "Best");
        
        // Prepare the contactList.
        contactList = contactManager.getContacts(0,1);
        
        contactManager.addFutureMeeting(contactList, pastDate);
    }
    
    /**
     * Test adding unknown contact results in IllegalArgumentException.
     */
    @Test (expected = IllegalArgumentException.class)
    public void addFutureMeetingThrowsExceptionWithUnknownContact(){
        // Create set that doesn't exist in ContactManager.
        Set<Contact> nonExistList = new HashSet<>();
        Contact nonExistContact = new ContactImpl(3, "Geoffrey");
        nonExistList.add(nonExistContact);
        
        contactManager.addFutureMeeting(nonExistList, futureDate);
    }
}