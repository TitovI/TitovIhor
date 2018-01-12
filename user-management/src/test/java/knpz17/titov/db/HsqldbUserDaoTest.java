package knpz17.titov.db;

import knpz17.titov.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class HsqldbUserDaoTest {

    UserDao userDao;

    @Before
    public void setUp() throws Exception {
        userDao = new HsqldbUserDao();


    }

    @Test
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