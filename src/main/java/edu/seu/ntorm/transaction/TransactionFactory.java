package edu.seu.ntorm.transaction;

import javax.sql.DataSource;
import java.sql.Connection;

public interface TransactionFactory {


    // Transaction newTransaction(Connection conn);

    /**
     * 根据Connection创建Transaction
     * @param ds 数据源
     * @param level 隔离级别
     * @param autoCommitted 自动提交
     * @return Transaction -> 实质上是一个Connection的包装类
     */
    Transaction newTransaction(DataSource ds, TransactionIsolationLevel level, boolean autoCommitted);
}
