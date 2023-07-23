package edu.seu.ntorm.session;

import edu.seu.ntorm.mapping.MappedStatement;

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
}
