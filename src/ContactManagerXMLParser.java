import java.util.List;
import java.util.Set;

/**
 * Interface for a a class that parses and reads in an XML file for a
 * ContactManager object.
 *
 * @author James
 */
public interface ContactManagerXMLParser {
    
    /**
     * Parse the contacts and store them in a contact Set.
     * 
     * @return the Set of contacts.
     */
    public Set<Contact> parseContacts();
    
    /**
     * Parse the meetings and store them into a List.
     * 
     * @return the List of meetings.
     */
    public List<Meeting> parseMeetings();
}