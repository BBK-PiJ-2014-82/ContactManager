package ContactMangerTest;

import contactmanager.*;
import org.junit.*;

/**
 * Tests for the ContactImpl class.
 *
 * @author James
 */
public class ContactImplTest {
    
    ContactImpl newContact;
    
    /**
     * Create a simple 'ContactImpl' to use in all other tests.
     */
    @Before
    public void initialize(){
        newContact = new ContactImpl(1, "James Hill");
    }
    
}