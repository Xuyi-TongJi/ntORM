package edu.seu.ntorm.driver.jdbc;

import cn.hutool.core.util.NumberUtil;
import edu.seu.ntorm.driver.netty.NtDbClient;
import edu.seu.ntorm.exception.DataBaseConnectionException;

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
    private final Map<String, NtDbClient> clients = new HashMap<>();

    /**
     * 执行客户端连接操作
     */
    @PostConstruct
    public void init() {
        for (String url : urls) {
            String[] info = url.split(":");
            if (info.length != 2 || !NumberUtil.isNumber(info[1])) {
                throw new DataBaseConnectionException();
            }
            int port = Integer.parseInt(info[1]);
            if (port < 0 || port > 65535) {
                throw new DataBaseConnectionException();
            }
            String[] ip = info[0].split("\\.");
            if (ip.length == 4) {
                for (int i = 0; i < 4; ++i) {
                    if (!NumberUtil.isNumber(ip[i])) {
                        throw new DataBaseConnectionException();
                    }
                }
                clients.put(url, new NtDbClient(info[0], port));
            } else {
                throw new DataBaseConnectionException();
            }
        }
    }

    /**
     * 获得一个JDBC连接
     * @param url the URL of the database to which to connect
     * @param info a list of arbitrary string tag/value pairs as
     * connection arguments. Normally at least a "user" and
     * "password" property should be included.
     * @return An ntDb JDBC Connection
     * @throws SQLException SQLException
     */
    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        if (!acceptsURL(url)) {
            throw new DataBaseConnectionException();
        }
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
