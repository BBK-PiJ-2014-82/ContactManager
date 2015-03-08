import java.io.File;
import java.util.Calendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * This class implements the contactManagerXMLWriter interface.
 *
 * @author James
 */
public class contactManagerXMLWriterImpl implements contactManagerXMLWriter {
    
    /**
     * The Document builder for this class.
     */
    final private DocumentBuilder builder;
    
    /**
     * The document constructed by this class.
     */
    private Document doc;
    
    /**
     * The constructor for this class.
     * 
     * @throws ParserConfigurationException if there is a problem parsing.
     */
    public contactManagerXMLWriterImpl() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        
    }
    
    @Override
    public void build(Set<Contact> contacts, List<Meeting> meetings, int nextContactID, int nextMeetingID){
        doc = builder.newDocument();
        Element rootElement = doc.createElement("contactManager");
        doc.appendChild(rootElement);
        rootElement.appendChild(createTextElement("nextContactID", ""+nextContactID));
        rootElement.appendChild(createTextElement("nextMeetingID", ""+nextMeetingID));
        rootElement.appendChild(createContactsList(contacts));
        rootElement.appendChild(createMeetingsList(meetings));
    }
    
    @Override
    public void print(){
        Transformer trans;
        try {
            trans = TransformerFactory.newInstance().newTransformer();
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult file = new StreamResult(new File("C:\\Users\\James\\Desktop\\Contact Manager.xml"));
            trans.transform(source, file);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(contactManagerXMLWriterImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(contactManagerXMLWriterImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Builds a DOM element for a Set of contacts.
     * 
     * @param contacts the set of contacts.
     * @return a DOC element describing the contacts.
     */
    private Element createContactsList(Set<Contact> contacts){
        Element e = doc.createElement("contactsList");
        for(Contact con : contacts){
            e.appendChild(createContact(con));
        }
        return e;
    }
    
    /**
     * Builds a DOM element for a Contact.
     * 
     * @param con the contact that will be saved.
     * @return a DOM element describing the item.
     */
    private Element createContact(Contact con){
        Element e = doc.createElement("contact");
        e.appendChild(createTextElement("contactID", ""+con.getId()));
        e.appendChild(createTextElement("contactName", con.getName()));
        e.appendChild(createTextElement("contactNotes", con.getNotes()));
        return e;
    }
    
    /**
     * Builds a DOM element for a List of meetings.
     * 
     * @param meeting the list of meetings.
     * @return a DOC element describing the meetings.
     */
    private Element createMeetingsList(List<Meeting> meetings){
        Element e = doc.createElement("meetingsList");
        for(Meeting meet : meetings){
            e.appendChild(createMeeting(meet));
        }
        return e;
    }
    
    /**
     * Builds a DOM element for a Meeting.
     * 
     * @param meet the meeting that will be saved.
     * @return a DOM element describing the item.
     */
    private Element createMeeting(Meeting meet){
        Element e = doc.createElement("meeting");
        e.appendChild(createTextElement("meetingID", ""+meet.getId()));
        e.appendChild(createTextElement("meetingYear", ""+meet.getDate().get(Calendar.YEAR)));
        e.appendChild(createTextElement("meetingMonth", ""+meet.getDate().get(Calendar.MONTH)));
        e.appendChild(createTextElement("meetingDayOfMonth", ""+meet.getDate().get(Calendar.DAY_OF_MONTH)));
        e.appendChild(createTextElement("meetingHourOfDay", ""+meet.getDate().get(Calendar.HOUR_OF_DAY)));
        e.appendChild(createTextElement("meetingMinute", ""+meet.getDate().get(Calendar.MINUTE)));
        e.appendChild(createContactsList(meet.getContacts()));
        if(meet instanceof PastMeeting){
            PastMeeting pastMeet = (PastMeeting)meet;
            e.appendChild(createTextElement("contactNotes", pastMeet.getNotes()));
        }
        return e;
    }
    
    /**
     * Builds a DOM element for text data.
     * 
     * @param name the name of this variable.
     * @param text the text that will be saved.
     * @return a DOM element describing the item.
     */
    private Element createTextElement(String name, String text){
        Text t = doc.createTextNode(text);
        Element e = doc.createElement(name);
        e.appendChild(t);
        return e;
    }
}