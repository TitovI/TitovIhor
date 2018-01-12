package knpz17.titov.db.impl;

import knpz17.titov.User;
import knpz17.titov.db.ConnectionFactory;
import knpz17.titov.db.UserDao;
import knpz17.titov.exception.db.DatabaseException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

public class HsqldbUserDao implements UserDao {

    private ConnectionFactory connectionFactory;

    public HsqldbUserDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public User create(User user) throws DatabaseException {
        Connection conn = connectionFactory.getConnection();

        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)");
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setDate(3, convert(user.getDateOfBirth()));

            int rows = ps.executeUpdate();

            if (rows != 1) {
                throw new DatabaseException("Number of the inserted rows: " + rows);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

        return null;
    }

    @Override
    public void update(User user) throws DatabaseException {

    }

    @Override
    public void delete(User user) throws DatabaseException {

    }

    @Override
    public User find(Long id) throws DatabaseException {
        return null;
    }

    @Override
    public Collection<User> findAll() throws DatabaseException {
        return null;
    }

    private Date convert(java.util.Date utilDate) {
        return new Date(utilDate.getTime());
    }
}
