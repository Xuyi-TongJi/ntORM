package edu.seu.ntorm.dataSource.pooled;

import com.alibaba.druid.util.StringUtils;
import edu.seu.ntorm.dataSource.AbstractDataSource;
import edu.seu.ntorm.dataSource.unpooled.UnpooledDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 支持配置的PooledDataSource
 * 有池化数据源实现
 * 在close时需要允许其他用户获取连接
 */
@Slf4j
public class PooledDataSource extends AbstractDataSource {

    /**
     * 池状态
     */
    private final PoolState state = new PoolState(this);

    /**
     * 最大活跃连接数，可配置
     */
    @Value("pool_maximum_active_connections")
    private int poolMaximumActiveConnections = 10;

    /**
     * 最大空闲连接数，可配置
     */
    @Value("pool_maximum_idle_connections")
    private int poolMaximumIdleConnections = 5;

    /**
     * 在被强制返回之前，池中连接被检查的时间
     */
    @Value("pool_maximum_checkout_time")
    private long poolMaximumCheckoutTime = 20000L;

    @Value("pool_time_to_wait")
    private long poolTimeToWait = 20000L;

    private String poolPingQuery = "NO PING QUERY SET";

    /**
     * 开启或禁用侦测查询
     */
    @Value("pool_ping_enabled")
    private boolean poolPingEnabled = false;

    private int poolPingConnectionsNotUsedFor = 0;

    private int expectedConnectionTypeCode;

    /**
     * 归还连接
     * @param connection connection
     */
    private void pushConnection(PooledConnection connection) throws SQLException {

    }

    /**
     * 获取连接
     * @param username usernmae
     * @param password password
     * @return 连接池化包装类
     */
    private PooledConnection popConnection(String username, String password) throws SQLException {
        return null;
    }

    public void forceCloseAll() {

    }

    private boolean pingConnection() {
        return false;
    }

    private int assembleConnectionTypeCode(String url, String username, String password) {
        return -1;
    }


    @Override
    protected Connection doGetConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    protected Connection doGetConnection(Properties properties) throws SQLException {
        return null;
    }
}
