import java.util.Calendar;

/**
 * This is an implementation of the Today interface.
 *
 * @author James
 */
public class TodayImpl implements Today {
    
    // This is the date in Calendar form. Initially this is set to the system
    // date / time but this can be changed for testing purposes.
    Calendar today;
    
    /**
     * This is the class constructor.
     */
    public void TodayImpl(){
        today = Calendar.getInstance();
    }
    
    @Override
    public void setToday(Calendar newDate){
        today = newDate;
    }
    
}