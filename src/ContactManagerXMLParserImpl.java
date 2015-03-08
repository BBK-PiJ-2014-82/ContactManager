import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Implementation of the ContactManagerXMLParser class.
 * 
 * @author James
 */
public class ContactManagerXMLParserImpl implements ContactManagerXMLParser {
    
    private final Document doc;
    private final DocumentBuilder builder;
    private final XPath path;
    
    /**
     * This is the class constructor.
     * 
     * @param fileName the name of the file where contact info is stored.
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws java.io.IOException
     */
    public ContactManagerXMLParserImpl(String fileName) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        File f = new File(fileName);
        doc = builder.parse(f);
        XPathFactory xpFactory = XPathFactory.newInstance();
        path = xpFactory.newXPath();
    }
    
    @Override
    public int parseNextContactID() throws XPathExpressionException {
        int nextContactID;
        nextContactID = Integer.parseInt(path.evaluate("/contactManager/nextContactID", doc));
        return nextContactID;
    }
    
    @Override
    public int parseNextMeetingID() throws XPathExpressionException {
        int nextMeetingID;
        nextMeetingID = Integer.parseInt(path.evaluate("/contactManager/nextMeetingID", doc));
        return nextMeetingID;
    }
    
    @Override
    public Set<Contact> parseContacts() throws XPathExpressionException {
        Set<Contact> contacts = new HashSet<>();
        int contactsCount;
        contactsCount = Integer.parseInt(path.evaluate("count(/contactManager/contactsList/contact)", doc));
        for(int i = 1; i <= contactsCount; i++){
            int id = Integer.parseInt(path.evaluate("/contactManager/contactsList/contact["+i+"]/contactID", doc));
            String name = path.evaluate("/contactManager/contactsList/contact["+i+"]/contactName", doc);
            String notes = path.evaluate("/contactManager/contactsList/contact["+i+"]/contactNotes", doc);
            Contact newCon = new ContactImpl(id, name);
            newCon.addNotes(notes);
            contacts.add(newCon);
        }
        return contacts;
    }
    
    @Override
    public List<Meeting> parseMeetings(Set<Contact> contacts) throws XPathExpressionException {
        List<Meeting> meetings = new LinkedList<>();
        int meetingsCount;
        meetingsCount = Integer.parseInt(path.evaluate("count(/contactManager/meetingsList/meetings)", doc));
        for(int i = 1; i <= meetingsCount; i++){
            // Get the meeting id number.
            int meetID = Integer.parseInt(path.evaluate("/contactManager/meetingsList/meetings["+i+"]/meetingID", doc));
            
            // Create the calendar date for this meeting.
            int year = Integer.parseInt(path.evaluate("/contactManager/meetingsList/meetings["+i+"]/meetingYear", doc));
            int month = Integer.parseInt(path.evaluate("/contactManager/meetingsList/meetings["+i+"]/meetingMonth", doc));
            int day = Integer.parseInt(path.evaluate("/contactManager/meetingsList/meetings["+i+"]/meetingDay", doc));
            int hour = Integer.parseInt(path.evaluate("/contactManager/meetingsList/meetings["+i+"]/meetingHour", doc));
            int minute = Integer.parseInt(path.evaluate("/contactManager/meetingsList/meetings["+i+"]/meetingMinute", doc));
            Calendar date = new GregorianCalendar(year, month, day, hour, minute);
            
            // Get the list of contacts by id numbers.
            int contactCount;
            contactCount = Integer.parseInt(path.evaluate("count(/contactManager/meetingsList/meetings["+i+"]/meetingContacts)", doc));
            Set<Contact> newContacts = new HashSet<>();
            for(int j = 1; j <= contactCount; j++){
                int contID = Integer.parseInt(path.evaluate("/contactManager/meetingsList/meetings["+i+"]/meetingContacts/contactID", doc));
                for(Contact cons : contacts){
                    if(cons.getId() == contID){
                        newContacts.add(cons);
                    }
                }
            }
            
            // Get the notes for this meeting.
            String notes = path.evaluate("/contactManager/meetingsList/meetings["+i+"]/meetingNotes", doc);
            
            // Create the meeting.
            Meeting newMeet;
            if(notes.equalsIgnoreCase("")){
                newMeet = new FutureMeetingImpl(meetID, newContacts, date);
            } else {
                newMeet = new PastMeetingImpl(meetID, newContacts, date, notes);
            }
            meetings.add(newMeet);
        }
        return meetings;
    }
}