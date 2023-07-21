package edu.seu.ntorm.binding.registry;

import edu.seu.ntorm.exception.AddMapperException;
import edu.seu.ntorm.exception.MapperNotExistException;
import edu.seu.ntorm.session.SqlSession;

public interface MapperRegistry {

    /**
     * 获取MapperProxy (Mapper代理类)
     * @param type Mapper(DAO) 类型
     * @param sqlSession  sqlSession
     * @return Mapper的代理类，如果不存在则抛出异常
     */
    <T> T getMapper(Class<T> type, SqlSession sqlSession) throws MapperNotExistException;

    /**
     * 添加MapperFactory
     * Mapper必须是接口
     * @param type Mapper.class
     */
    <T> void addMapper(Class<T> type) throws AddMapperException;

    /**
     * 根据包名添加MapperFactory
     * @param packageName 包名
     */
    void addMappers(String packageName) throws AddMapperException;

    <T> boolean hasMapper(Class<T> type);
}
