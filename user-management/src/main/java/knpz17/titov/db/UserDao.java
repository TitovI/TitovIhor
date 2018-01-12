package knpz17.titov.db;

import knpz17.titov.User;
import knpz17.titov.exception.db.DatabaseException;

import java.util.Collection;

public interface UserDao {

    User create(User user) throws DatabaseException;

    void update(User user) throws DatabaseException;

    void delete(User user) throws DatabaseException;

    User find(Long id) throws DatabaseException;

    Collection<User> findAll() throws DatabaseException;
}
