package knpz17.titov.db;

import knpz17.titov.User;
import knpz17.titov.db.impl.ConnectionFactoryImpl;
import knpz17.titov.db.impl.HsqldbUserDao;
import knpz17.titov.exception.db.UserNotFoundException;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;

@RunWith(JUnit4.class)
public class HsqldbUserDaoTest extends DatabaseTestCase {

    private static final String DRIVER = "org.hsqldb.jdbcDriver";
    private static final String URL = "jdbc:hsqldb:file:db/usermanagement";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static final Long TEST_ID = 1001L;

    private UserDao userDao;
    private ConnectionFactory connectionFactory;

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl(DRIVER, URL, USER, PASSWORD);
        return new DatabaseConnection(connectionFactory.getConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        InputStream datasetResource = getClass().getClassLoader()
                .getResourceAsStream("userdataset.xml");
        return new XmlDataSet(datasetResource);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        userDao = new HsqldbUserDao(connectionFactory);
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = getTestUser();

        assertNull(user.getId());

        User persistedUser = userDao.create(user);

        assertNotNull(persistedUser);
        assertNotNull(persistedUser.getId());
    }

    @Test
    public void testFindAll() throws Exception {
        Collection<User> allUsers = userDao.findAll();

        assertNotNull("Collection is null", allUsers);
        assertEquals("Collection size is wrong", 2, allUsers.size());
    }

    @Test
    public void testFindById() throws Exception {
        User user = userDao.find(TEST_ID);

        assertNotNull(user);
        assertEquals(TEST_ID, user.getId());
    }

    @Test(expected = UserNotFoundException.class)
    public void testDelete() throws Exception {
        User userToDelete = new User();
        userToDelete.setId(TEST_ID);

        userDao.delete(userToDelete);

        userDao.find(TEST_ID);
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