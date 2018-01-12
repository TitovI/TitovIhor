package knpz17.titov.db;

import knpz17.titov.db.impl.ConnectionFactoryImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoFactory {
    private static final String DRIVER = "connection.driver";
    private static final String URL = "connection.url";
    private static final String USER = "connection.user";
    private static final String PASSWORD = "connection.password";

    private static final String USER_DAO = "knpz17.titov.db.UserDao";

    private final static DaoFactory INSTANCE = new DaoFactory();

    private Properties props;

    private DaoFactory() {
        props = new Properties();
        try {
            InputStream settingsResource =
                    getClass().getClassLoader().getResourceAsStream("settings.properties");
            props.load(settingsResource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    public UserDao getUserDao() {
        try {
            Class daoImpl = Class.forName(props.getProperty(USER_DAO));
            UserDao result = (UserDao) daoImpl.newInstance();
            result.setConnectionFactory(getConnectionFactory());
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionFactory getConnectionFactory() {
        String driver = props.getProperty(DRIVER);
        String url = props.getProperty(URL);
        String user = props.getProperty(USER);
        String pass = props.getProperty(PASSWORD);

        return new ConnectionFactoryImpl(driver, url, user, pass);
    }
}
