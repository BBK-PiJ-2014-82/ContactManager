import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.LinkedList;

/**
 * An implementation of the 'ContactManager' class.
 * 
 * @author James
 */
public class ContactManagerImpl {
   
    /**
     * Linked List containing all the contacts being managed.
     */
    private Set<ContactImpl> contacts;
    
    /**
     * Linked List containing all the meetings being managed.
     */
    private List<MeetingImpl> meetings;
    
    /**
     * This is the next free number to be used for contact IDs.
     */
    private int nextContactID = 0;
    
    /**
     * This is the next free number to be used for meeting IDs.
     */
    private int nextMeetingID = 0;
    
    /**
     * This is the class constructor.
     */
    public ContactManagerImpl(){
        contacts = new HashSet<>();
        meetings = new LinkedList<>();
    }
    
    
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) throws IllegalArgumentException {
        if(!this.contacts.containsAll(contacts)){
            throw new IllegalArgumentException();
        } else if (date.before(Calendar.getInstance())){
            throw new IllegalArgumentException();
        } else {
            MeetingImpl newMeet = new FutureMeetingImpl(nextMeetingID, contacts, date);
            meetings.add(newMeet);
            nextMeetingID++;
            return newMeet.getId();
        }
    }
    
    
    public PastMeeting getPastMeeting(int id) throws IllegalArgumentException {
        Meeting meet = getMeeting(id);
        if(meet instanceof PastMeeting) {
            return (PastMeeting) meet;
        } else if (meet instanceof FutureMeeting) {
            throw new IllegalArgumentException();
        } else {
            return null;
        }
    }
    
    
    public FutureMeeting getFutureMeeting(int id) throws IllegalArgumentException {
        Meeting meet = getMeeting(id);
        if(meet instanceof FutureMeeting) {
            return (FutureMeeting) meet;
        } else if (meet instanceof PastMeeting) {
            throw new IllegalArgumentException();
        } else {
            return null;
        }
    }
    
    public Meeting getMeeting(int id){
        for(MeetingImpl meet : meetings){
            if(meet.getId() == id){
                return meet;
            }
        }
        return null;
    }
    
    
    public List<Meeting> getFutureMeetingList(Contact contact) throws IllegalArgumentException {
        List<Meeting> returnList = new LinkedList<>();
        if(!contacts.contains((ContactImpl)contact)){
            throw new IllegalArgumentException();
        } else {
            for(Meeting meet : meetings){
                if(meet instanceof FutureMeeting){
                    if(meet.getContacts().contains(contact)){
                        returnList.add(meet);
                    }
                }
            }
        }
        Collections.sort(returnList, (Meeting o1, Meeting o2) -> o1.getDate().compareTo(o2.getDate()));
        return returnList;
    }
    
    
    public List<Meeting> getFutureMeetingList(Calendar date) {
        // Set variables.
        List<Meeting> returnList = new LinkedList<>();
        Calendar checkDate;
        boolean sameDay;
        
        // Loop through the meetings checking & add to list when match found.
        for(Meeting meet : meetings){
            checkDate = meet.getDate();
            sameDay = checkDate.get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
                    checkDate.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR);
            if(sameDay){
                returnList.add(meet);
            }
        }
        
        // Sort the collection & return it.
        Collections.sort(returnList, (Meeting o1, Meeting o2) -> o1.getDate().compareTo(o2.getDate()));
        return returnList;
    }
    
    
    public List<Meeting> getPastMeetingList(Contact contact) throws IllegalArgumentException {
        List<Meeting> returnList = new LinkedList<>();
        if(!contacts.contains((ContactImpl)contact)){
            throw new IllegalArgumentException();
        } else {
            for(Meeting meet : meetings){
                if(meet instanceof PastMeeting){
                    if(meet.getContacts().contains(contact)){
                        returnList.add(meet);
                    }
                }
            }
        }
        Collections.sort(returnList, (Meeting o1, Meeting o2) -> o1.getDate().compareTo(o2.getDate()));
        return returnList;
    }
    
    
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) throws IllegalArgumentException, NullPointerException {
        if(!this.contacts.containsAll(contacts) || this.contacts.isEmpty()){
            throw new IllegalArgumentException();
        } else if (date == null || text == null || text.equalsIgnoreCase("")){
            throw new NullPointerException();
        } else {
            PastMeetingImpl newMeet = new PastMeetingImpl(nextMeetingID, contacts, date);
            newMeet.addNotes(text);
            meetings.add(newMeet);
            nextMeetingID++;
        }
    }
    
    
    public void addNewContact(String name, String notes) throws NullPointerException {
        if(name == null || notes == null){
            throw new NullPointerException();
        } else {
            ContactImpl newContact = new ContactImpl(nextContactID, name);
            newContact.addNotes(notes);
            contacts.add(newContact);
            nextContactID++;
        }
    }
    
    
    public Set<Contact> getContacts(int... ids) throws IllegalArgumentException {
        HashSet<Contact> returnContacts = new HashSet();
        boolean exists;
        for(int i = 0; i < ids.length; i++){
            exists = false;
            for(ContactImpl cont : contacts){
                if(cont.contactID == ids[i]){
                    returnContacts.add(cont);
                    exists = true;
                }
            }
            if(!exists){
                throw new IllegalArgumentException();
            }
        }
        return returnContacts;
    }
    
    public Set<Contact> getContacts(String name) throws NullPointerException {
        if(name == null || name.equals("")){
            throw new NullPointerException();
        } else {
            HashSet<Contact> returnContacts = new HashSet();
            for(ContactImpl cont : contacts){
                if(cont.contactName.contains(name)){
                    returnContacts.add(cont);
                }
            }
            return returnContacts;
        }
    }
}