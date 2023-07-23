package edu.seu.ntorm.transaction;

import javax.sql.DataSource;
import java.sql.Connection;

public interface TransactionFactory {

    /**
     * 根据Connection创建Transaction
     * @param conn 连接
     * @return Transaction -> 实质上是一个Connection的包装类
     */
    Transaction newTransaction(Connection conn);

    Transaction newTransaction(DataSource ds, TransactionIsolationLevel level, boolean autoCommitted);
}
