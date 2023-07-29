package edu.seu.ntorm.dataSource.pooled;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PoolState {

    private final PooledDataSource ds;

    /**
     * 空闲连接
     */
    private final List<PooledConnection> idleConnections = new ArrayList<>();

    /**
     * 活跃连接
     */
    private final List<PooledConnection> activeConnection = new ArrayList<>();

    /**
     * 请求次数
     */
    private long requestCount = 0;

    /**
     * 总请求时间
     */
    private long accumulatedRequestTime = 0;

    private long accumulatedCheckoutTime = 0;

    private long chaimedOverdueConnectionCount = 0;

    private long accumulatedCheckoutTimeOfOverdueCount = 0;

    /**
     * 总等待时间
     */
    private long accumulatedWaitTime = 0;

    /**
     * 要等待的次数
     */
    private long hadToWaitCount = 0;

    /**
     * 失败次数连接
     */
    private long badConnectionCount = 0;

    public PoolState(PooledDataSource ds) {
        this.ds = ds;
    }

    public List<PooledConnection> getIdleConnections() {
        return idleConnections;
    }

    public List<PooledConnection> getActiveConnection() {
        return activeConnection;
    }

    public long getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(long requestCount) {
        this.requestCount = requestCount;
    }

    public long getAccumulatedRequestTime() {
        return accumulatedRequestTime;
    }

    public void setAccumulatedRequestTime(long accumulatedRequestTime) {
        this.accumulatedRequestTime = accumulatedRequestTime;
    }

    public long getAccumulatedCheckoutTime() {
        return accumulatedCheckoutTime;
    }

    public void setAccumulatedCheckoutTime(long accumulatedCheckoutTime) {
        this.accumulatedCheckoutTime = accumulatedCheckoutTime;
    }

    public long getChaimedOverdueConnectionCount() {
        return chaimedOverdueConnectionCount;
    }

    public void setChaimedOverdueConnectionCount(long chaimedOverdueConnectionCount) {
        this.chaimedOverdueConnectionCount = chaimedOverdueConnectionCount;
    }

    public long getAccumulatedCheckoutTimeOfOverdueCount() {
        return accumulatedCheckoutTimeOfOverdueCount;
    }

    public void setAccumulatedCheckoutTimeOfOverdueCount(long accumulatedCheckoutTimeOfOverdueCount) {
        this.accumulatedCheckoutTimeOfOverdueCount = accumulatedCheckoutTimeOfOverdueCount;
    }

    public long getAccumulatedWaitTime() {
        return accumulatedWaitTime;
    }

    public void setAccumulatedWaitTime(long accumulatedWaitTime) {
        this.accumulatedWaitTime = accumulatedWaitTime;
    }

    public long getHadToWaitCount() {
        return hadToWaitCount;
    }

    public void setHadToWaitCount(long hadToWaitCount) {
        this.hadToWaitCount = hadToWaitCount;
    }

    public long getBadConnectionCount() {
        return badConnectionCount;
    }

    public void setBadConnectionCount(long badConnectionCount) {
        this.badConnectionCount = badConnectionCount;
    }

    public PooledDataSource getDs() {
        return ds;
    }
}
