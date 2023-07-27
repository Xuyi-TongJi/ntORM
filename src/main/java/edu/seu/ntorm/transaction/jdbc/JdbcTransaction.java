package edu.seu.ntorm.transaction.jdbc;

import edu.seu.ntorm.exception.JdbcTransactionException;
import edu.seu.ntorm.transaction.TransactionIsolationLevel;
import edu.seu.ntorm.transaction.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcTransaction implements Transaction {

    /**
     * 数据源中获取的连接
     */
    protected Connection connection;

    /**
     * 数据源
     */
    protected DataSource dataSource;

    /**
     * 隔离级别
     */
    protected TransactionIsolationLevel isolationLevel;

    /**
     * 自动提交
     */
    protected boolean autoCommit;

    public JdbcTransaction(DataSource dataSource,
                           TransactionIsolationLevel isolationLevel,
                           boolean autoCommit) {
        this.dataSource = dataSource;
        this.isolationLevel = isolationLevel;
        this.autoCommit = autoCommit;
        // TODO 如何获取连接 ？
        try {
            this.connection = dataSource.getConnection();
            connection.setAutoCommit(autoCommit);
            connection.setTransactionIsolation(isolationLevel.getLevel());
        } catch (SQLException e) {
            throw new JdbcTransactionException();
        }
    }

//    public JdbcTransaction(Connection conn) {
//        this.connection = conn;
//    }

    @Override
    public Connection getConnection() throws SQLException {
//        Connection conn = dataSource.getConnection();
//        if (conn == null) {
//            throw new SQLException();
//        }
//        conn.setAutoCommit(autoCommit);
//        conn.setTransactionIsolation(isolationLevel.getLevel());
        return connection;
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
