package knpz17.titov.db.impl;

import knpz17.titov.User;
import knpz17.titov.db.ConnectionFactory;
import knpz17.titov.db.UserDao;
import knpz17.titov.exception.db.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class HsqldbUserDao implements UserDao {

    private ConnectionFactory connectionFactory;

    public HsqldbUserDao() {
    }

    public HsqldbUserDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public User create(User user) throws DatabaseException {
        try {
            Connection conn = connectionFactory.getConnection();
            PreparedStatement ps =
                    conn.prepareStatement("INSERT INTO users (firstname, lastname, dateofbirth)" +
                            "VALUES (?, ?, ?)");

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setDate(3, convert(user.getDateOfBirth()));

            int rows = ps.executeUpdate();

            if (rows != 1) {
                throw new DatabaseException("Number of the inserted rows: " + rows);
            }

            CallableStatement cs = conn.prepareCall("call IDENTITY()");
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                long id = rs.getLong(1);
                user.setId(id);
            }

            rs.close();
            cs.close();
            ps.close();
            conn.close();

            return user;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
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
        String SELECT_ALL_SQL = "SELECT id, firstname, lastname, dateofbirth FROM users";
        try {
            Collection<User> result = new ArrayList<>();
            Connection c = connectionFactory.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(SELECT_ALL_SQL);

            while (rs.next()) {
                User user = new User();
                user.setId(new Long(rs.getLong(1)));
                user.setFirstName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setDateOfBirth(rs.getDate(4));
                result.add(user);
            }

            return result;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    private Date convert(java.util.Date utilDate) {
        return new Date(utilDate.getTime());
    }
}
