package contactmanager;

import java.util.Objects;

/**
 * This class implements the 'Contact' interface.
 * 
 * @author James
 */
public class ContactImpl implements interfaces.Contact {
    
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
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.contactID;
        hash = 71 * hash + Objects.hashCode(this.contactName);
        hash = 71 * hash + Objects.hashCode(this.contactNotes);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ContactImpl other = (ContactImpl) obj;
        if (this.contactID != other.contactID) {
            return false;
        }
        if (!Objects.equals(this.contactName, other.contactName)) {
            return false;
        }
        if (!Objects.equals(this.contactNotes, other.contactNotes)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int getId(){
        return contactID;
    }
    
    @Override
    public String getName(){
        return contactName;
    }
    
    @Override
    public String getNotes(){
        return contactNotes;
    }
    
    @Override
    public void addNotes(String note){
        this.contactNotes = note;
    }
}