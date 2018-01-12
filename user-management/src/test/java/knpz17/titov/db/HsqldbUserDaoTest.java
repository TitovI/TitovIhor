package knpz17.titov.db;

import knpz17.titov.User;
import knpz17.titov.db.impl.ConnectionFactoryImpl;
import knpz17.titov.db.impl.HsqldbUserDao;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import java.io.InputStream;
import java.util.Calendar;

public class HsqldbUserDaoTest extends DatabaseTestCase {

    private UserDao userDao;
    private ConnectionFactory connectionFactory = new ConnectionFactoryImpl();

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        return new DatabaseConnection(connectionFactory.getConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        InputStream datasetResource = getClass().getClassLoader()
                .getResourceAsStream("userdataset.xml");
        return new XmlDataSet(datasetResource);
    }

    protected void setUp() throws Exception {
        super.setUp();
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