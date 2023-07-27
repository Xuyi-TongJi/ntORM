package edu.seu.ntorm.driver.jdbc;

import com.mysql.jdbc.NotImplemented;
import edu.seu.ntorm.driver.netty.NtDbClient;
import io.netty.channel.Channel;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * NtDB JDBC连接
 * 非并发安全，确保多个线程不会共用一个连接
 */
public class NtDbConnection implements Connection {

    /**
     * key: Netty Channel
     */
    private final Channel channel;

    private boolean autoCommit;

    private final NtDbClient ntDbClient;

    private boolean readOnly;

    private boolean isClose = false;

    private int isolationLevel;

    /**
     * typeAlias
     */
    private final Map<String, Class<?>> typeMap;

    public NtDbConnection(NtDbClient client, Channel channel, boolean autoCommit, boolean readOnly, int isolationLevel, Map<String, Class<?>> typeMap) {
        this.ntDbClient = client;
        this.channel = channel;
        this.autoCommit = autoCommit;
        this.readOnly = readOnly;
        this.isolationLevel = isolationLevel;
        this.typeMap = typeMap;
    }

    @Override
    public Statement createStatement() throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        // 暂不支持PreparedStatement
        throw new NotImplementedException();
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        // 暂不支持PreparedStatement
        throw new NotImplementedException();
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        // 暂不支持nativeSQL
        throw new NotImplementedException();
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {

    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return autoCommit;
    }

    @Override
    public void commit() throws SQLException {

    }

    @Override
    public void rollback() throws SQLException {

    }

    @Override
    public void close() throws SQLException {
        if (!isClosed()) {
            // TODO
        }
    }

    @Override
    public boolean isClosed() throws SQLException {
        return isClose;
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {

    }

    @Override
    public String getCatalog() throws SQLException {
        return null;
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {

    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return isolationLevel;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new NotImplementedException();
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new NotImplementedException();
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return null;
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public int getHoldability() throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public Clob createClob() throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public Blob createBlob() throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public NClob createNClob() throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return false;
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {

    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {

    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return null;
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return null;
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return null;
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return null;
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public String getSchema() throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public void abort(Executor executor) throws SQLException {

    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return 0;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new NotImplemented();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new NotImplemented();
    }
}
