package knpz17.titov.db;

import junit.framework.TestCase;
import knpz17.titov.User;
import knpz17.titov.db.impl.ConnectionFactoryImpl;
import knpz17.titov.db.impl.HsqldbUserDao;
import org.junit.Before;

import java.util.Calendar;

public class HsqldbUserDaoTest extends TestCase {

    private UserDao userDao;
    private ConnectionFactory connectionFactory;

    @Before
    public void setUp() throws Exception {
        connectionFactory = new ConnectionFactoryImpl();
        userDao = new HsqldbUserDao(connectionFactory);
    }

    public void testCreateUser() throws Exception {
        User user = getTestUser();

        assertNull(user.getId());

        User persistedUser = userDao.create(user);

        assertNotNull(persistedUser);
        assertNotNull(persistedUser.getId());
    }

    private User getTestUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Snow");

        Calendar cal = Calendar.getInstance();
        cal.set(1991, Calendar.APRIL, 18);
        user.setDateOfBirth(cal.getTime());

        return user;
    }
}