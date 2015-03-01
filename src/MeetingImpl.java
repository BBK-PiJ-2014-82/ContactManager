import java.util.Calendar;
import java.util.Set;

/**
 * This class implements the 'Meeting' interface.
 *
 * @author James
 */
public class MeetingImpl implements Meeting {
    
    /**
     * This is a unique ID through which the application can identify a
     * meeting.
     */
    int meetingID;
    
    /**
     * This is the date and time of the meeting stored as a Calendar object.
     */
    Calendar meetingDate;
    
    /**
     * This is a set containing the Contacts who attended the meeting.
     */
    Set<Contact> meetingContacts;
    
    /**
     * This is the class constructor.
     * 
     * @param meetingID a unique ID for this meeting.
     * @param meetingContacts a list of attendees for the meeting.
     * @param meetingDate the date at which the meeting takes place.
     */
    public MeetingImpl(int meetingID, Set<Contact> meetingContacts, Calendar meetingDate){
        this.meetingID = meetingID;
        this.meetingContacts = meetingContacts;
        this.meetingDate = meetingDate;
    }
    
    @Override
    public int getId(){
        return meetingID;
    }
    
    @Override
    public Calendar getDate(){
        return meetingDate;
    }
    
    @Override
    public Set<Contact> getContacts(){
        return meetingContacts;
    }
}