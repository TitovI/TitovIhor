package knpz17.titov.db;

import knpz17.titov.exception.db.DatabaseException;

import java.sql.Connection;

public interface ConnectionFactory {

    Connection getConnection() throws DatabaseException;
}
