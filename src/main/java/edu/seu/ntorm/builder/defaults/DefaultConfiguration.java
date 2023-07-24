package edu.seu.ntorm.builder.defaults;

import edu.seu.ntorm.binding.registry.MapperRegistry;
import edu.seu.ntorm.dataSource.druid.DruidDataSourceFactory;
import edu.seu.ntorm.exception.AddMapperException;
import edu.seu.ntorm.exception.MappedStatementNotExistException;
import edu.seu.ntorm.exception.MapperNotExistException;
import edu.seu.ntorm.mapping.Environment;
import edu.seu.ntorm.mapping.MappedStatement;
import edu.seu.ntorm.ntDb.DefaultBuilderAutoConfigurator;
import edu.seu.ntorm.session.Configuration;
import edu.seu.ntorm.session.SqlSession;
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

    private Environment environment;

    @Autowired
    private TypeAliasRegistry typeAliasRegistry;

    /**
     * Mapper注册机
     */
    @Autowired
    private MapperRegistry mapperRegistry;

    @PostConstruct
    public void postConstruct() {
        typeAliasRegistry.register("JDBC", JdbcTransactionFactory.class);
        typeAliasRegistry.register("DRUID", DruidDataSourceFactory.class);
    }

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

    @Override
    public Environment getEnvironment() {
        return environment;
    }
}
