package edu.seu.ntorm.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 一次数据库的操作应该具有事务管理能力，而不是通过JDBC获取连接后直接执行
 * 定义标准的事务接口，用以连接，提交，回滚和关闭
 * 包装数据源的功能(装饰器)
 */
public interface Transaction {

    Connection getConnection() throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;

    void close() throws SQLException;
}
