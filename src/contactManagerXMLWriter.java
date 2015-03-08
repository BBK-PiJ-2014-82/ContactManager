import java.util.List;
import java.util.Set;

/**
 * A class to output the contactManager data into XML format.
 * 
 * @author James
 */
public interface contactManagerXMLWriter {
    
    /**
     * Builds a DOM document for this contact manager.
     * 
     * @param contacts the set of contacts to be saved.
     * @param meetings the list of contact to be saved.
     * @param nextContactID the next contactID that will be used.
     * @param nextMeetingID the next meetingID that will be used.
     * return the DOM structure constructed by this class.
     */
    public void build(Set<Contact> contacts, List<Meeting> meetings, int nextContactID, int nextMeetingID);
    
    /**
     * Prints a DOM document to a file on the users system.
     * 
     * @param fileName the file to be printed to.
     */
    public void print(String fileName);
}