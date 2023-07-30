package edu.seu.ntorm.env;

import edu.seu.ntorm.binding.method.MethodParams;
import edu.seu.ntorm.executor.Executor;
import edu.seu.ntorm.executor.resultsetHandler.ResultSetHandler;
import edu.seu.ntorm.executor.statementHandler.StatementHandler;
import edu.seu.ntorm.executor.typeHandler.TypeHandler;
import edu.seu.ntorm.mapping.BoundSql;
import edu.seu.ntorm.mapping.MappedStatement;
import edu.seu.ntorm.ntDb.SqlStatementConfig;
import edu.seu.ntorm.session.SqlSession;
import edu.seu.ntorm.transaction.Transaction;

import java.util.List;
import java.util.Map;

/**
 * Configuration是
 */
public interface Configuration {

    /**
     * 添加这个包下的所有Mapper Interface 到 MapperRegistry
     * @param packageName package 全路径
     */
    void addMappers(String packageName);

    <T> void addMapper(Class<T> type);

    <T> T getMapper(Class<T> type, SqlSession sqlSession);

    boolean hasMapper(Class<?> type);

    /**
     * 添加MappedStatement到map中
     * @param statement sql statement
     */
    void addMappedStatement(MappedStatement statement);

    MappedStatement getMappedStatement(String id);

    Environment getEnvironment();

    SqlStatementConfig getSqlStatementConfig();

    /**
     * 由配置类生产执行器 Executor
     * @param transaction 事务(连接包装类)
     * @return 执行器
     */
    Executor buildExecutor(Transaction transaction);

    /**
     *
     * @param methodParams methodParams
     * @return typeHandler
     */
    TypeHandler buildTypeHandler(List<MethodParams> methodParams);

    /**
     * 构建结果集处理器
     * @param executor 执行器
     * @param mappedStatement 映射语句（来自Configuration）
     * @param boundSql SQL -> 可以获取结果DTO （Map or 实体类）
     * @return 结果集处理器
     */
    ResultSetHandler buildResultSetHandler(Executor executor, MappedStatement mappedStatement, BoundSql boundSql);

    /**
     * 构建语句处理器
     * @param executor 执行器
     * @param mappedStatement 映射语句（来自Configuration）
     * @param parameter SQL语句参数
     * @param boundSql SQL包装类
     * @return 语句处理器
     */
    StatementHandler buildStatementHandler(Executor executor, MappedStatement mappedStatement, Map<String, String> parameter,
                                           BoundSql boundSql);
}
