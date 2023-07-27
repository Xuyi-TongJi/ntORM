package edu.seu.ntorm.driver;

import edu.seu.ntorm.driver.netty.NtDbClient;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class NtDbDriver implements Driver {

    private final Logger logger = Logger.getLogger("NtDbDriver");

    /**
     * 连接的URL
     */
    private final Set<String> urls = new HashSet<>();

    /**
     * NtDb客户端, 目前暂时不支持用户名密码连接
     */
    private final NtDbClient client = new NtDbClient();

    /**
     * 执行客户端连接操作
     */
    @PostConstruct
    public void init() {

    }

    /**
     * 获得一个JDBC连接
     * @param url the URL of the database to which to connect
     * @param info a list of arbitrary string tag/value pairs as
     * connection arguments. Normally at least a "user" and
     * "password" property should be included.
     * @return A NTDB JDBC Connection
     * @throws SQLException SQLException
     */
    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        return null;
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return urls.contains(url);
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return 1;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return logger;
    }
}
