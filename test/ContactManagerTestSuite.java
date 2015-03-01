import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
            ContactImplTest.class,
            MeetingImplTest.class,
            PastMeetingImplTest.class
        })

public class ContactManagerTestSuite {}