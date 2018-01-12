package knpz17.titov;

import junit.framework.TestCase;
import org.junit.Before;

import java.util.Calendar;
import java.util.Date;

public class UserTest extends TestCase {

    private User user;
    private Date dateOfBirth;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        user = new User();

        Calendar calendar = Calendar.getInstance();
        calendar.set(1984, Calendar.MAY, 26);

        dateOfBirth = calendar.getTime();
    }

    public void testGetFullName() {
        user.setFirstName("John");
        user.setLastName("Doe");

        assertEquals("Doe, John", user.getFullName());
    }

    public void testGetAge() {
        user.setDateOfBirth(dateOfBirth);

        assertEquals(2018 - 1984, user.getAge());
    }
}