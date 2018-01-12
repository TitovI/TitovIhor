package knpz17.titov.db.impl;

import knpz17.titov.db.ConnectionFactory;
import knpz17.titov.exception.db.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryImpl implements ConnectionFactory {

    @Override
    public Connection getConnection() throws DatabaseException {
        String driverName = " org.hsqldb.jdbcDriver";
        String url = "jdbc:hsqldb:file:db/usermanagement";
        String userName = "sa";
        String password = "";

        try {
            Class.forName(driverName);
            return DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
