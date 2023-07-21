package edu.seu.ntorm.builder.session.defaults;

import edu.seu.ntorm.binding.registry.DefaultMapperRegistry;
import edu.seu.ntorm.binding.registry.MapperRegistry;
import edu.seu.ntorm.exception.AddMapperException;
import edu.seu.ntorm.exception.MappedStatementNotExistException;
import edu.seu.ntorm.exception.MapperNotExistException;
import edu.seu.ntorm.mapping.MappedStatement;
import edu.seu.ntorm.session.Configuration;
import edu.seu.ntorm.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration默认实现类
 * 用Mapper注册机 MapperRegistry 和 一个Map存储MappedStatement
 */
public class DefaultConfiguration implements Configuration {

    private final Map<String, MappedStatement> statements = new HashMap<>();

    private final MapperRegistry mapperRegistry = new DefaultMapperRegistry();

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
}
