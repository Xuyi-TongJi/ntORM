package edu.seu.ntorm.session.defaults;

import edu.seu.ntorm.binding.registry.MapperRegistry;
import edu.seu.ntorm.exception.MapperNotExistException;
import edu.seu.ntorm.session.SqlSession;

public class DefaultSqlSession implements SqlSession {

    /**
     * 映射注册机
     */
    private final MapperRegistry mapperRegistry;

    public DefaultSqlSession(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    @Override
    public <T> T selectOne(String statement) {
        // TODO
        return null;
    }

    @Override
    public <T> T selectOne(String statement, Object parameters) {
        // TODO
        return null;
    }

    @Override
    public <T> T getMapper(Class<T> type) throws MapperNotExistException {
        return mapperRegistry.getMapper(type, this);
    }
}
