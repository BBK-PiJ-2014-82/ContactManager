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
        this.contactNotes = "";
    }
    
    /**
     * Returns the unique contactID for this contact.
     * 
     * @return the contactID variable.
     */
    public int getID(){
        return contactID;
    }
    
    /**
     * Returns the name for this contact.
     * 
     * @return the name recorded for this contact.
     */
    public String getName(){
        return contactName;
    }
    
    /**
     * Returns the notes added to this contact or a blank if there are none.
     * 
     * @return the notes attached to this contact.
     */
    public String getNotes(){
        return contactNotes;
    }
    
    /**
     * Adds the note entered by the user to the contact.
     * 
     * @param note the notes to be added.
     */
    public void addNotes(String note){
        this.contactNotes = note;
    }
}