package contactmanager;

/**
 * This class implements the 'Contact' interface.
 * 
 * @author James
 */
public class ContactImpl {
    
    /**
     * This is a unique ID through which the application can identify a
     * contact.
     */
    int contactID;
    
    /**
     * This is the name given to the contact by the user.
     */
    String contactName;
    
    /**
     * This is a string of notes that can record information about a contact.
     */
    String contactNotes;
    
    /**
     * This is the class constructor.
     * 
     * @param contactID the unique ID is provided by the application. 
     * @param contactName the name is provided by the user upon creation.
     */
    public ContactImpl(int contactID, String contactName){
        this.contactID = contactID;
        this.contactName = contactName;
    }
    
}