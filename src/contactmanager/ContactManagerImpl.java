package contactmanager;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.LinkedList;

/**
 * An implementation of the 'ContactManager' class.
 * 
 * @author James
 */
public class ContactManagerImpl {
   
    /**
     * Linked List containing all the contacts being managed.
     */
    private LinkedList<ContactImpl> contacts;
    
    /**
     * Linked List containing all the meetings being managed.
     */
    private LinkedList<MeetingImpl> meetings;
    
    /**
     * This is the class constructor.
     */
    public void ContactManagerImpl(){
        contacts = new LinkedList<>();
        meetings = new LinkedList<>();
    }
    
}