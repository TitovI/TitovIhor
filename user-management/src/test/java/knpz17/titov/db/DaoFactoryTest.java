package knpz17.titov.db;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DaoFactoryTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getUserDao() throws Exception {
        DaoFactory daoFactory = DaoFactory.getInstance();

        assertNotNull(daoFactory);

        UserDao userDao = daoFactory.getUserDao();

        assertNotNull(userDao);
    }
}