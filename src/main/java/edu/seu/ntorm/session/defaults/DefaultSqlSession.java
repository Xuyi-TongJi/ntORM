package edu.seu.ntorm.session.defaults;

import edu.seu.ntorm.exception.MapperNotExistException;
import edu.seu.ntorm.session.Configuration;
import edu.seu.ntorm.session.SqlSession;

public class DefaultSqlSession implements SqlSession {

    /**
     * 映射注册机
     */
    private final Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
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
        return configuration.getMapper(type, this);
    }
}
