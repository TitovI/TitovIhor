package knpz17.titov.db.impl;

import knpz17.titov.User;
import knpz17.titov.db.UserDao;
import knpz17.titov.exception.db.DatabaseException;

import java.util.Collection;

public class HsqldbUserDao implements UserDao {

    @Override
    public User create(User user) throws DatabaseException {
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
}
