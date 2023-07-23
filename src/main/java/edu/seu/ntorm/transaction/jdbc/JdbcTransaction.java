package edu.seu.ntorm.transaction.jdbc;

import edu.seu.ntorm.transaction.TransactionIsolationLevel;
import edu.seu.ntorm.transaction.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcTransaction implements Transaction {

    protected Connection connection;

    protected DataSource dataSource;

    protected TransactionIsolationLevel isolationLevel;

    protected boolean autoCommit;

    public JdbcTransaction(DataSource dataSource,
                           TransactionIsolationLevel isolationLevel,
                           boolean autoCommit) {
        this.dataSource = dataSource;
        this.isolationLevel = isolationLevel;
        this.autoCommit = autoCommit;
    }

    public JdbcTransaction(Connection conn) {
        this.connection = conn;
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection conn = dataSource.getConnection();
        if (conn == null) {
            throw new SQLException();
        }
        conn.setAutoCommit(autoCommit);
        conn.setTransactionIsolation(isolationLevel.getLevel());
        return conn;
    }

    @Override
    public void commit() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.commit();
        }
    }

    @Override
    public void rollback() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.rollback();
        }
    }

    @Override
    public void close() throws SQLException {
        if (!connection.isClosed()) {
            connection.close();
        }
    }
}
