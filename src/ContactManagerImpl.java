import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

/**
 * An implementation of the 'ContactManager' class.
 * 
 * @author James
 */
public class ContactManagerImpl implements ContactManager {
    
    /**
     * The directory of the file for saving and loading the contact manager.
     */
    String fileName = ".Contact Manager.xml";
    
    /**
     * Linked List containing all the contacts being managed.
     */
    private Set<Contact> contacts = new HashSet<>();
    
    /**
     * Linked List containing all the meetings being managed.
     */
    private List<Meeting> meetings = new LinkedList<>();
    
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
     * 
     * @throws javax.xml.xpath.XPathExpressionException
     */
    public ContactManagerImpl() throws XPathExpressionException{
        File file = new File(fileName);
        if(file.exists()){
            try {
                ContactManagerXMLParser parser;
                parser = new ContactManagerXMLParserImpl(fileName);
                nextContactID = parser.parseNextContactID();
                nextMeetingID = parser.parseNextMeetingID();
                contacts = parser.parseContacts();
                meetings = parser.parseMeetings(contacts);
            } catch (ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(ContactManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
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
    
    @Override
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
    
    @Override
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
    
    @Override
    public Meeting getMeeting(int id){
        for(Meeting meet : meetings){
            if(meet.getId() == id){
                return meet;
            }
        }
        return null;
    }
    
    @Override
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
    
    @Override
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
    
    @Override
    public List<PastMeeting> getPastMeetingList(Contact contact) throws IllegalArgumentException {
        List<PastMeeting> returnList = new LinkedList<>();
        if(!contacts.contains((ContactImpl)contact)){
            throw new IllegalArgumentException();
        } else {
            for(Meeting meet : meetings){
                if(meet instanceof PastMeeting){
                    if(meet.getContacts().contains(contact)){
                        returnList.add((PastMeeting)meet);
                    }
                }
            }
        }
        Collections.sort(returnList, (Meeting o1, Meeting o2) -> o1.getDate().compareTo(o2.getDate()));
        return returnList;
    }
    
    @Override
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) throws IllegalArgumentException, NullPointerException {
        if(!this.contacts.containsAll(contacts) || this.contacts.isEmpty()){
            throw new IllegalArgumentException();
        } else if (date == null || text == null || text.equalsIgnoreCase("")){
            throw new NullPointerException();
        } else {
            PastMeeting newMeet = new PastMeetingImpl(nextMeetingID, contacts, date, text);
            meetings.add(newMeet);
            nextMeetingID++;
        }
    }
    
    @Override
    public void addMeetingNotes(int id, String text) throws IllegalArgumentException, IllegalStateException, NullPointerException {
        Meeting toAddMeet = getMeeting(id);
        if(toAddMeet == null){
            throw new IllegalArgumentException();
        } else if (text == null || text.equalsIgnoreCase("")){
            throw new NullPointerException();
        } else {
            if(toAddMeet instanceof PastMeeting){
                PastMeeting pastMeet = (PastMeeting) toAddMeet;
                String newNotes = pastMeet.getNotes() + text;
                toAddMeet = new PastMeetingImpl((PastMeeting) toAddMeet, newNotes);
            } else if (toAddMeet instanceof FutureMeeting){
                if(toAddMeet.getDate().after(Calendar.getInstance())){
                    throw new IllegalStateException();
                } else {
                    PastMeeting newMeet = new PastMeetingImpl((PastMeeting)toAddMeet, text);
                }
            }
        }
    }
    
    @Override
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
    
    @Override
    public Set<Contact> getContacts(int... ids) throws IllegalArgumentException {
        HashSet<Contact> returnContacts = new HashSet();
        boolean exists;
        for(int i = 0; i < ids.length; i++){
            exists = false;
            for(Contact cont : contacts){
                if(cont.getId() == ids[i]){
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
    
    @Override
    public Set<Contact> getContacts(String name) throws NullPointerException {
        if(name == null || name.equals("")){
            throw new NullPointerException();
        } else {
            HashSet<Contact> returnContacts = new HashSet();
            for(Contact cont : contacts){
                if(cont.getName().contains(name)){
                    returnContacts.add(cont);
                }
            }
            return returnContacts;
        }
    }
    
    @Override
    public void flush(){
        try {
            contactManagerXMLWriter builder;
            builder = new contactManagerXMLWriterImpl();
            builder.build(contacts, meetings, nextContactID, nextMeetingID);
            builder.print(fileName);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ContactManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}