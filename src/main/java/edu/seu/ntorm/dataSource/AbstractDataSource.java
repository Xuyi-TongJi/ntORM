package edu.seu.ntorm.dataSource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * 支持多个数据源
 * 在initializerDriver初始化驱动中使用了Class.forName和newInstance方式创建数据源连接操作
 * 完成连接以后，把连接存放到驱动注册器中，方便后续使用中可以直接获取连接，避免重复创建
 * 子类：有池化和无池化的数据源
 */
public abstract class AbstractDataSource implements DataSource {

    private ClassLoader driverClassLoader;

    /**
     * 驱动配置
     */
    private Properties properties;

    /**
     * 支持多个驱动, 用静态成员
     */
    private static final Map<String, Driver> registeredDrivers = new ConcurrentHashMap<>();

    /**
     * 重要：驱动（全类名！！ i.e : com.cj.jdbc.mysql）
     * 驱动负责连接数据库
     */
    private String driver;

    private String url;

    /**
     * ntDB暂时不支持
     */
    private String username;

    /**
     * ntDB暂时不支持
     */
    private String password;

    /**
     * 是否自动提交
     */
    private Boolean autoCommit;

    /**
     * 事务级别
     */
    private Integer defaultTransactionIsolationLevel;

    /*
        加载这个类时执行静态代码块，加载JVM中所有已经加载的Driver
        静态代码块，在该类被加载时执行
        加载DriverManager中所有的驱动
     */
    static {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            registeredDrivers.put(driver.getClass().getName(), driver);
        }
    }

    /**
     * 驱动初始化
     * 将该类实体的驱动注册到静态成员registeredDrivers中
     */
    protected synchronized void initializerDriver() throws SQLException {
        if (!registeredDrivers.containsKey(this.driver)) {
            try {
                // 类加载 Class.forName
                Class<?> driverClass = Class.forName(driver, true, driverClassLoader);
                Driver instance = (Driver) driverClass.newInstance();
                // DriverManager中注册
                DriverManager.registerDriver(new DriverWrapper(instance));
                // registeredDrivers中注册
                registeredDrivers.put(driver, instance);
            } catch (Exception e) {
                throw new SQLException("Error initialize driver");
            }
        }
    }

    /**
     * 获取数据库连接
     * ntDB不支持鉴权
     * @param username username
     * @param password password
     * @return Connection 数据库连接
     * @throws SQLException SQLException
     */
    protected abstract Connection doGetConnection(String username, String password) throws SQLException;

    protected abstract Connection doGetConnection(Properties properties) throws SQLException;

    @Override
    public Connection getConnection() throws SQLException {
        return doGetConnection(username, password);
    }

    @Override
    public Connection getConnection(String u, String p) throws SQLException {
        return doGetConnection(u, p);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return DriverManager.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        DriverManager.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        DriverManager.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return DriverManager.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }

    public ClassLoader getDriverClassLoader() {
        return driverClassLoader;
    }

    public void setDriverClassLoader(ClassLoader driverClassLoader) {
        this.driverClassLoader = driverClassLoader;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Map<String, java.sql.Driver> getRegisteredDrivers() {
        return registeredDrivers;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(Boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public Integer getDefaultTransactionIsolationLevel() {
        return defaultTransactionIsolationLevel;
    }

    public void setDefaultTransactionIsolationLevel(Integer defaultTransactionIsolationLevel) {
        this.defaultTransactionIsolationLevel = defaultTransactionIsolationLevel;
    }

    /**
     * Driver代理类
     * 装饰器模式
     */
    private static class DriverWrapper implements Driver {

        private Driver driver;

        DriverWrapper(Driver driver) {
            this.driver = driver;
        }

        @Override
        public Connection connect(String url, Properties info) throws SQLException {
            return this.driver.connect(url, info);
        }

        @Override
        public boolean acceptsURL(String url) throws SQLException {
            return this.driver.acceptsURL(url);
        }

        @Override
        public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
            return this.driver.getPropertyInfo(url, info);
        }

        @Override
        public int getMajorVersion() {
            return this.driver.getMinorVersion();
        }

        @Override
        public int getMinorVersion() {
            return this.driver.getMinorVersion();
        }

        @Override
        public boolean jdbcCompliant() {
            return this.driver.jdbcCompliant();
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        }
    }
}
