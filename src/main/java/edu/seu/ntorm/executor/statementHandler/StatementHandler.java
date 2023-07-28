package edu.seu.ntorm.executor.statementHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 语句执行器
 */
public interface StatementHandler {

    /**
     * 准备语句, 实质上是实例化+配置参数两个操作
     * @param connection 数据库连接
     * @return 语句
     */
    Statement prepare(Connection connection) throws SQLException;

    /**
     * 参数化，仅PreparedStatement支持
     * @param statement 语句
     */
    void parameterize(Statement statement) throws SQLException;

    /**
     * 执行查询
     * @param statement 语句
     * @return 查询结果
     */
    ResultSet execute(Statement statement);
}
