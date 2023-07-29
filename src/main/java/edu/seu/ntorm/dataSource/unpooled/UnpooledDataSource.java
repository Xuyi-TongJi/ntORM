package edu.seu.ntorm.dataSource.unpooled;

import com.alibaba.druid.util.StringUtils;
import edu.seu.ntorm.dataSource.AbstractDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class UnpooledDataSource extends AbstractDataSource {

    @Override
    protected Connection doGetConnection(String username, String password) throws SQLException {
        Properties properties = new Properties();
        Properties props = super.getProperties();
        if (props != null) {
            properties.putAll(props);
        }
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            properties.put("username", username);
            properties.put("password", password);
        }
        return doGetConnection(properties);
    }

    @Override
    protected Connection doGetConnection(Properties properties) throws SQLException {
        // 初始化并注册驱动
        initializerDriver();
        // DriverManager获取连接
        Connection connection = DriverManager.getConnection(getUrl(), properties);
        if (getAutoCommit() != null && getAutoCommit() != connection.getAutoCommit()) {
            connection.setAutoCommit(getAutoCommit());
        }
        if (getDefaultTransactionIsolationLevel() != null) {
            connection.setTransactionIsolation(getDefaultTransactionIsolationLevel());
        }
        return connection;
    }
}
