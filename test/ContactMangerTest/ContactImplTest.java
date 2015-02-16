package ContactMangerTest;

import contactmanager.*;
import org.junit.*;
import static org.junit.Assert.*;

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
    
    /**
     * Test the correct ID is returned.
     */
    @Test
    public void testContactIDReturn(){
        int checkID = 1;
        int testID = newContact.getID();
        assertEquals("The following incorrect ID has been returned: " + testID, checkID, testID);
    }
    
}