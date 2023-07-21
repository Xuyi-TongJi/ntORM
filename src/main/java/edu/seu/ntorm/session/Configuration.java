package edu.seu.ntorm.session;

import edu.seu.ntorm.mapping.MappedStatement;

/**
 * Configuration是
 */
public interface Configuration {

    void addMappers(String packageName);

    <T> void addMapper(Class<T> type);

    <T> T getMapper(Class<T> type, SqlSession sqlSession);

    boolean hasMapper(Class<?> type);

    void addMappedStatement(MappedStatement statement);

    MappedStatement getMappedStatement(String id);
}
