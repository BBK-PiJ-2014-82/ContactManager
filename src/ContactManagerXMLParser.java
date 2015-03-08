import java.util.List;
import java.util.Set;
import javax.xml.xpath.XPathExpressionException;

/**
 * Interface for a a class that parses and reads in an XML file for a
 * ContactManager object.
 *
 * @author James
 */
public interface ContactManagerXMLParser {
    
    /**
     * Parse the nextContactID.
     * 
     * @return the next ID to be used for contact numbers.
     * @throws javax.xml.xpath.XPathExpressionException
     */
    public int parseNextContactID() throws XPathExpressionException;
    
    /**
     * Parse the nextMeetingID.
     * 
     * @return the next ID to be used for meeting numbers.
     * @throws javax.xml.xpath.XPathExpressionException
     */
    public int parseNextMeetingID() throws XPathExpressionException;
    
    /**
     * Parse the contacts and store them in a contact Set.
     * 
     * @return the Set of contacts.
     * @throws javax.xml.xpath.XPathExpressionException
     */
    public Set<Contact> parseContacts() throws XPathExpressionException;
    
    /**
     * Parse the meetings and store them into a List.
     * 
     * @param contacts a list of the contacts for this contact manager.
     * @return the List of meetings.
     * @throws javax.xml.xpath.XPathExpressionException
     */
    public List<Meeting> parseMeetings(Set<Contact> contacts) throws XPathExpressionException;
}