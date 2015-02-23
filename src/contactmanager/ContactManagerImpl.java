package contactmanager;

import interfaces.Contact;
import java.util.Calendar;
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
        if(!contacts.containsAll(this.contacts)){
            throw new IllegalArgumentException();
        } else if (date.before(Calendar.getInstance())){
            throw new IllegalArgumentException();
        } else {
            MeetingImpl newMeet = new MeetingImpl(nextMeetingID, contacts, date);
            meetings.add(newMeet);
            nextMeetingID++;
            return newMeet.meetingID;
        }
    }
    
    public MeetingImpl getMeeting(int id){
        for(MeetingImpl meet : meetings){
            if(meet.meetingID == id){
                return meet;
            }
        }
        return null;
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
}