package ContactMangerTest;

import contactmanager.*;
import interfaces.*;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        boolean exists;
        int checkID;
        int testID;
        Meeting checkMeet;
        Meeting testMeet;
        Calendar checkDate;
        Calendar testDate;
        Set<Contact> contactSet;
        
        // Prepare the contactList.
        contactList = contactManager.getContacts(0,1);
        
        // Create the future meeting & test the ID is returned correctly.
        checkID = 0;
        testID = contactManager.addFutureMeeting(contactList, futureDate);
        assertEquals("Incorrect meeting ID has been returned.", checkID, testID);
        
        // Check the 1st contact was added to the meeting correctly.
        contactSet = contactManager.getMeeting(0).getContacts();
        exists = false;
        
        for(Contact contacts : contactSet){
            if(contacts.getName().equals("James Hill") && contacts.getNotes().equals("Great")){
                exists = true;
            }
        }
        assertTrue("Incorrect contacts have been returned.", exists);
        
        // Check the 2nd contact was added to the meeting correctly.
        contactSet = contactManager.getMeeting(0).getContacts();
        exists = false;
        
        for(Contact contacts : contactSet){
            if(contacts.getName().equals("Saisai") && contacts.getNotes().equals("Best")){
                exists = true;
            }
        }
        assertTrue("Incorrect contacts have been returned.", exists);
        
        // Check the date on added meeting is correct.
        checkDate = futureDate;
        testDate = contactManager.getMeeting(0).getDate();
        assertEquals("Incorrect date has been returned.", checkDate, testDate);
        
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
    
    /**
     * Test getting contacts with partial String name.
     */
    @Test
    public void testGetContactsWithString(){
        // Create variables.
        boolean nameFound = true;
        String testString = "Hill";
        
        // add new contacts.
        contactManager.addNewContact("James Hill", "Great");
        contactManager.addNewContact("Saisai Hill", "Best");
        contactManager.addNewContact("Ian King", "Cool");
        
        // return set using ID's.
        contactList = contactManager.getContacts(testString);
        
        // Test the size of the set is correct.
        int checkSize = 2;
        int testSize = contactList.size();
        assertEquals("Incorrect size returned.", checkSize, testSize);
        
        // Loop through contacts to check they all contain correct string.
        for(Contact cont : contactList){
            if(!cont.getName().contains(testString)){
                nameFound = false;
            }
        }
        
        assertTrue("These strings do not contain the same text.", nameFound);
    }
    
    /**
     * Test null name throws correct exception when searching for contacts.
     */
    @Test (expected = NullPointerException.class)
    public void searchContactsThrowsExceptionWithNullName(){
        String notExist = null;
        contactManager.getContacts(notExist);
    }
    
    /**
     * Test an empty string throws a null pointer exception.
     */
    @Test (expected = NullPointerException.class)
    public void searchContactsThrowsExceptionWithEmtpyString(){
        contactManager.getContacts("");
    }
    
    /**
     * Test add past meeting works correctly.
     */
    @Test
    public void testAddNewPastMeetingAndGetPastMeeting(){
        // add new contacts.
        contactManager.addNewContact("James Hill", "Great");
        contactManager.addNewContact("Saisai", "Best");
        
        // Create text for the meeting notes.
        String notes = "This meeting went extremely well.";
        
        // Create variables.
        boolean exists;
        Calendar checkDate;
        Calendar testDate;
        Set<Contact> contactSet;
        String checkNotes;
        String testNotes;
        
        // Prepare the contactList.
        contactList = contactManager.getContacts(0,1);
        
        // Create the meeting.
        contactManager.addNewPastMeeting(contactList, pastDate, notes);
        
        // Check the 1st contact was added to the meeting correctly.
        contactSet = contactManager.getPastMeeting(0).getContacts();
        exists = false;
        
        for(Contact contacts : contactSet){
            if(contacts.getName().equals("James Hill") && contacts.getNotes().equals("Great")){
                exists = true;
            }
        }
        assertTrue("Incorrect contacts have been returned.", exists);
        
        // Check the 2nd contact was added to the meeting correctly.
        contactSet = contactManager.getPastMeeting(0).getContacts();
        exists = false;
        
        for(Contact contacts : contactSet){
            if(contacts.getName().equals("Saisai") && contacts.getNotes().equals("Best")){
                exists = true;
            }
        }
        assertTrue("Incorrect contacts have been returned.", exists);
        
        // Check the date on added meeting is correct.
        checkDate = pastDate;
        testDate = contactManager.getPastMeeting(0).getDate();
        assertEquals("Incorrect date has been returned.", checkDate, testDate);
        
        // Check that the notes returned are correct.
        checkNotes = notes;
        testNotes = contactManager.getPastMeeting(0).getNotes();
        assertEquals("Incorrect date has been returned.", checkNotes, testNotes);
    }
    
    /**
     * Test addNewPastMeeting throws exception for empty list of contacts.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testAddNewPastMeetingThrowsExceptionForEmptyContacts(){
        contactList = new HashSet<>();
        contactManager.addNewPastMeeting(contactList, pastDate, "Hello");
    }
    
    /**
     * Test addNewPastMeeting throws exception on non-existent contact.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testAddNewPastMeetingThrowsExceptionForNonExistentContacts(){
        // Create set that doesn't exist in ContactManager.
        Set<Contact> nonExistList = new HashSet<>();
        Contact nonExistContact = new ContactImpl(3, "Geoffrey");
        nonExistList.add(nonExistContact);
        
        contactManager.addNewPastMeeting(nonExistList, pastDate, "Hello.");
    }
    
    /**
     * Test addNewPastMeeting throws exception on null date.
     */
    @Test (expected = NullPointerException.class)
    public void testAddNewPastMeetingThrowsExceptionForNullDate(){
        GregorianCalendar nullDate = null;
        contactManager.addNewContact("James Hill", "Great");
        contactList = contactManager.getContacts(0);
        contactManager.addNewPastMeeting(contactList, nullDate, "Hello.");
    }
    
    /**
     * Test addNewPastMeeting throws exception on null notes.
     */
    @Test (expected = NullPointerException.class)
    public void testAddNewPastMeetingThrowsExceptionForNullNotes(){
        contactManager.addNewContact("James Hill", "Great");
        contactList = contactManager.getContacts(0);
        String notes = null;
        contactManager.addNewPastMeeting(contactList, pastDate, notes);
    }
    
    /**
     * Test addNewPastMeeting throws exception on empty notes.
     */
    @Test (expected = NullPointerException.class)
    public void testAddNewPastMeetingThrowsExceptionForEmptyNotes(){
        contactManager.addNewContact("James Hill", "Great");
        contactList = contactManager.getContacts(0);
        String notes = "";
        contactManager.addNewPastMeeting(contactList, pastDate, notes);
    }
    
    /**
     * Test getPastMeeting returns null when meetingID doesn't exist.
     */
    @Test
    public void testGetPastMeetingReturnsNull(){
        Meeting checkMeet = null;
        Meeting testMeet = contactManager.getPastMeeting(5);
        assertEquals("The returned meeting is not null.", checkMeet, testMeet);
    }
    
    /**
     * Test getPastMeeting throws incorrect exception when returned meeting is
     * a future meeting.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testGetPastMeetingThrowsExceptionForFutureMeetings(){
        // add new contacts & create the set.
        contactManager.addNewContact("James Hill", "Great");
        contactList = contactManager.getContacts(0);
        
        // Add a future meeting to the list.
        int meetNum = contactManager.addFutureMeeting(contactList, futureDate);
        
        // Test the condition.
        contactManager.getPastMeeting(meetNum);
    }
    
    /**
     * Test the addition and return of a future meeting.
     */
    @Test
    public void testGetFutureMeeting(){
        // add new contacts.
        contactManager.addNewContact("James Hill", "Great");
        contactManager.addNewContact("Saisai", "Best");
        
        // Create variables.
        boolean exists;
        Calendar checkDate;
        Calendar testDate;
        Set<Contact> contactSet;
        
        // Prepare the future meeting.
        contactList = contactManager.getContacts(0,1);
        contactManager.addFutureMeeting(contactList, futureDate);
        
        // Check the 1st contact was added to the meeting correctly.
        contactSet = contactManager.getFutureMeeting(0).getContacts();
        exists = false;
        
        for(Contact contacts : contactSet){
            if(contacts.getName().equals("James Hill") && contacts.getNotes().equals("Great")){
                exists = true;
            }
        }
        assertTrue("Incorrect contacts have been returned.", exists);
        
        // Check the 2nd contact was added to the meeting correctly.
        exists = false;
        
        for(Contact contacts : contactSet){
            if(contacts.getName().equals("Saisai") && contacts.getNotes().equals("Best")){
                exists = true;
            }
        }
        assertTrue("Incorrect contacts have been returned.", exists);
        
        // Check the date on added meeting is correct.
        checkDate = futureDate;
        testDate = contactManager.getFutureMeeting(0).getDate();
        assertEquals("Incorrect date has been returned.", checkDate, testDate);
        
    }
    
    /**
     * Test getFutureMeeting returns null when meetingID doesn't exist.
     */
    @Test
    public void testGetFutureMeetingReturnsNull(){
        Meeting checkMeet = null;
        Meeting testMeet = contactManager.getFutureMeeting(5);
        assertEquals("The returned meeting is not null.", checkMeet, testMeet);
    }
    
    /**
     * Test getFutureMeeting throws incorrect exception when returned meeting is
     * a past meeting.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testGetFutureMeetingThrowsExceptionForFutureMeetings(){
        // add new contacts & create the set.
        contactManager.addNewContact("James Hill", "Great");
        contactList = contactManager.getContacts(0);
        
        // Add a future meeting to the list.
        int meetNum = contactManager.addFutureMeeting(contactList, pastDate);
        
        // Test the condition.
        contactManager.getFutureMeeting(meetNum);
    }
    
}