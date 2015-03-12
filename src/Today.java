import java.util.Calendar;

/**
 * This class records the current date external to the ContactManagerImpl
 * which allows date sensitive methods to be tested.
 * 
 * @author James
 */
public interface Today {

    /**
     * This is the class constructor.  It will create a new 'Today' object that
     * is a wrapper for a Calendar initially set to the system date / time.
     */
    public void today();
    
    /**
     * This setter allows the tester of the software to set a date in order to
     * allow the testing of time sensitive methods in the ContactManagerImpl.
     * 
     * @param newDate the date that the Today object will be set to.
     */
    public void setToday(Calendar newDate);
    
}