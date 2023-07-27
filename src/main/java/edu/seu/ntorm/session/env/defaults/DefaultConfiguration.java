package edu.seu.ntorm.session.env.defaults;

import edu.seu.ntorm.binding.registry.MapperRegistry;
import edu.seu.ntorm.dataSource.druid.DruidDataSourceFactory;
import edu.seu.ntorm.exception.AddMapperException;
import edu.seu.ntorm.exception.MappedStatementNotExistException;
import edu.seu.ntorm.exception.MapperNotExistException;
import edu.seu.ntorm.executor.Executor;
import edu.seu.ntorm.executor.factory.ExecutorFactory;
import edu.seu.ntorm.executor.factory.ResultSetHandlerFactory;
import edu.seu.ntorm.executor.factory.StatementHandlerFactory;
import edu.seu.ntorm.executor.resultsetHandler.ResultSetHandler;
import edu.seu.ntorm.executor.statementHandler.StatementHandler;
import edu.seu.ntorm.mapping.BoundSql;
import edu.seu.ntorm.session.env.Environment;
import edu.seu.ntorm.mapping.MappedStatement;
import edu.seu.ntorm.ntDb.DefaultBuilderAutoConfigurator;
import edu.seu.ntorm.ntDb.SqlStatementConfig;
import edu.seu.ntorm.session.env.Configuration;
import edu.seu.ntorm.session.SqlSession;
import edu.seu.ntorm.transaction.Transaction;
import edu.seu.ntorm.transaction.jdbc.JdbcTransactionFactory;
import edu.seu.ntorm.type.TypeAliasRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration默认实现类
 * 用Mapper注册机 MapperRegistry 和 一个Map存储MappedStatement
 */
@ConditionalOnBean(value = {DefaultBuilderAutoConfigurator.class})
@Component
public class DefaultConfiguration implements Configuration {

    /**
     * SQL语句映射
     */
    private final Map<String, MappedStatement> statements = new HashMap<>();

    /**
     * 环境 -> DataSourceFactory  + TransactionFactory -> 指向ORM框架通信模块
     */
    @Autowired
    private Environment environment;

    @Autowired
    private TypeAliasRegistry typeAliasRegistry;

    /**
     * Mapper注册机
     */
    @Autowired
    private MapperRegistry mapperRegistry;

    /**
     * SQL执行配置
     */
    @Autowired
    private SqlStatementConfig sqlStatementConfig;

    /**
     * Executor工厂
     */
    @Autowired
    private ExecutorFactory executorFactory;

    /**
     * ResultHandler工厂
     */
    @Autowired
    private ResultSetHandlerFactory resultSetHandlerFactory;

    /**
     * StatementHandler工厂
     */
    @Autowired
    private StatementHandlerFactory statementHandlerFactory;

    @PostConstruct
    public void postConstruct() {
        typeAliasRegistry.register("JDBC", JdbcTransactionFactory.class);
        typeAliasRegistry.register("DRUID", DruidDataSourceFactory.class);
    }

    // ------------------ Mapper和Statement注册相关 -------------------- //

    @Override
    public void addMappers(String packageName) {
        try {
            mapperRegistry.addMappers(packageName);
        } catch (AddMapperException e) {
            throw new AddMapperException();
        }
    }

    @Override
    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    @Override
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        try {
            return mapperRegistry.getMapper(type, sqlSession);
        } catch (MapperNotExistException e) {
            throw new MapperNotExistException();
        }
    }

    @Override
    public boolean hasMapper(Class<?> type) {
        return mapperRegistry.hasMapper(type);
    }

    @Override
    public void addMappedStatement(MappedStatement statement) {
        statements.put(statement.getId(), statement);
    }

    @Override
    public MappedStatement getMappedStatement(String id) {
        MappedStatement statement =  statements.get(id);
        if (statement == null) {
            throw new MappedStatementNotExistException();
        } else {
            return statement;
        }
    }

    // ----------- 执行环境（数据源和事务工厂）相关 ----------- //

    @Override
    public Environment getEnvironment() {
        return environment;
    }

    // ------------------ SQL执行相关 -------------------- //

    @Override
    public SqlStatementConfig getSqlStatementConfig() {
        return sqlStatementConfig;
    }

    @Override
    public Executor buildExecutor(Transaction transaction) {
        return executorFactory.getExecutor(this, transaction);
    }

    @Override
    public ResultSetHandler buildResultHandler(Executor executor, MappedStatement mappedStatement, BoundSql boundSql) {
        return resultSetHandlerFactory.getResultSetHandler(executor, mappedStatement, boundSql);
    }

    @Override
    public StatementHandler buildStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameter, ResultSetHandler resultSetHandler, BoundSql boundSql) {
        return statementHandlerFactory.getStatementHandler(executor, mappedStatement, parameter, resultSetHandler, boundSql);
    }
}
