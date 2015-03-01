import java.util.Calendar;
import java.util.Set;

/**
 * An implementation of the 'FutureMeeting' interface.
 * 
 * @author James
 */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {
    
    /**
     * The class constructor.
     * 
     * @param meetingID a unique ID to identify the meeting.
     * @param meetingContacts a list of the contacts attending the meeting.
     * @param meetingDate the date on which the meeting will occur.
     */
    public FutureMeetingImpl(int meetingID, Set<Contact> meetingContacts, Calendar meetingDate) {
        super(meetingID, meetingContacts, meetingDate);
    }
}