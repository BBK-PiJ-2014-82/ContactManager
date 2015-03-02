import java.util.Calendar;
import java.util.Set;

/**
 * This is an implementation of the 'PastMeeting' class.
 *
 * @author James
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {
    
    /**
     * This String is the notes for the meeting.
     */
    private String meetingNotes = "";
    
    /**
     * This is the class constructor.
     * 
     * @param meetingID a unique ID for this meeting.
     * @param meetingContacts a list of attendees for the meeting.
     * @param meetingDate the date at which the meeting takes place.
     */
    public PastMeetingImpl(int meetingID, Set<Contact> meetingContacts, Calendar meetingDate) {
        super(meetingID, meetingContacts, meetingDate);
    }
    
    /**
     * This is the copy constructor.
     * 
     * @param meeting a meeting you will copy.
     */
    public PastMeetingImpl(PastMeeting meeting, String notes) {
        this(meeting.getId(), meeting.getContacts(), meeting.getDate());
        meetingNotes = notes;
    }
    
    /**
     * This method allows a string of notes to be added to a past meeting.
     * 
     * @param meetingNotes the notes summarising the meeting.
     */
    public void addNotes(String meetingNotes){
        this.meetingNotes = meetingNotes;
    }
    
    @Override
    public String getNotes(){
        return meetingNotes;
    }
}