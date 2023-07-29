package edu.seu.ntorm.executor;

import edu.seu.ntorm.mapping.BoundSql;
import edu.seu.ntorm.mapping.MappedStatement;
import edu.seu.ntorm.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 执行器接口
 * 定义事物的相关操作
 */
public interface Executor {

    /**
     * SQL查询操作
     * @param ms mappedStatement
     * @param parameter parameter
     * @param boundSql boundSql sql语句包装类
     * @return 查询结果
     */
    <E> List<E> query(MappedStatement ms, Map<String, Object> parameter, BoundSql boundSql);

    Transaction getTransaction();

    void commit(boolean required) throws SQLException;

    void rollback(boolean required) throws SQLException;

    void close(boolean forceRollback);
}
