package edu.seu.ntorm.executor.statementHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 语句执行器，由Configuration生产
 */
public interface StatementHandler {

    /**
     * 准备语句
     * @param connection 数据库连接
     * @return 语句
     */
    Statement prepare(Connection connection) throws SQLException;

    /**
     * 参数化
     * @param statement 语句
     */
    void parameterize(Statement statement) throws SQLException;

    /**
     * 执行查询
     * @param statement 语句
     * @return 查询结果
     */
    <E> List<E> query(Statement statement);
}
