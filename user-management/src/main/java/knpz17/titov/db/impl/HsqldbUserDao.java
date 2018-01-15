package knpz17.titov.db.impl;

import knpz17.titov.User;
import knpz17.titov.db.ConnectionFactory;
import knpz17.titov.db.UserDao;
import knpz17.titov.exception.db.DatabaseException;
import knpz17.titov.exception.db.UserNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class HsqldbUserDao implements UserDao {

    private static final String INSERT_USER_SQL =    "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_SQL =     "SELECT id, firstname, lastname, dateofbirth FROM users";
    private static final String SELECT_BY_ID_SQL =   "SELECT id, firstname, lastname, dateofbirth FROM users WHERE id = ?";
    private static final String DELETE_SQL =         "DELETE FROM users WHERE id = ?";
    private static final String CALL_SEQUENCE_SQL =  "call IDENTITY()";

    private ConnectionFactory connectionFactory;

    public HsqldbUserDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public User create(User user) throws DatabaseException {
        try (
                Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_USER_SQL);
        ) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setDate(3, convert(user.getDateOfBirth()));

            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new DatabaseException("Number of the inserted rows: " + rows);
            }

            try (
                    CallableStatement cs = conn.prepareCall(CALL_SEQUENCE_SQL);
                    ResultSet rs = cs.executeQuery()
            ) {
                if (rs.next()) {
                    long id = rs.getLong(1);
                    user.setId(id);
                }
            }

            return user;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void update(User user) throws DatabaseException {
        //todo
    }

    @Override
    public void delete(User user) throws DatabaseException {
        try (
                Connection c = connectionFactory.getConnection();
                PreparedStatement ps = c.prepareStatement(DELETE_SQL)
        ) {
            ps.setLong(1, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public User find(Long id) throws DatabaseException {
        try (
                Connection c = connectionFactory.getConnection();
                PreparedStatement ps = c.prepareStatement(SELECT_BY_ID_SQL);
        ) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    return extractUser(rs);
                } else {
                    throw new UserNotFoundException(id);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Collection<User> findAll() throws DatabaseException {
        Collection<User> result = new ArrayList<>();
        try (
                Connection c = connectionFactory.getConnection();
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(SELECT_ALL_SQL);
        ) {
            while (rs.next()) {
                result.add(extractUser(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return result;
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(1));
        user.setFirstName(rs.getString(2));
        user.setLastName(rs.getString(3));
        user.setDateOfBirth(rs.getDate(4));
        return user;
    }

    private Date convert(java.util.Date utilDate) {
        return new Date(utilDate.getTime());
    }
}
