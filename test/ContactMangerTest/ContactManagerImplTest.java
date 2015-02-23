package ContactMangerTest;

import contactmanager.*;
import org.junit.*;

/**
 * Tests for the ContactManagerImpl class.
 * 
 * @author James
 */
public class ContactManagerImplTest {
    
    ContactManagerImpl contactManager;
    
    /**
     * Create a ContactManagerImplTest to use in all tests.
     */
    @Before
    public void initialize(){
        contactManager = new ContactManagerImpl();
    }
    
}