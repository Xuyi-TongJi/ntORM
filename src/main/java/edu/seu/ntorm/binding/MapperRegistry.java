package edu.seu.ntorm.binding;

import cn.hutool.core.lang.ClassScanner;
import edu.seu.ntorm.exception.AddMapperException;
import edu.seu.ntorm.exception.MapperNotExistException;
import edu.seu.ntorm.session.SqlSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Mapper注册机
 * 上层在使用时可以提供一个包的路径即可完成扫描与Mapper(接口对象)的注册(生成代理类并注册)
 */
public class MapperRegistry {

    /**
     * Mapper.class -> MapperProxyFactory
     */
    private final Map<Class<?>, MapperProxyFactory<?>> mapperFactories = new HashMap<>();

    /**
     * 获取MapperProxy (Mapper代理类)
     * @param type Mapper(DAO) 类型
     * @param sqlSession  sqlSession
     * @return Mapper的代理类，如果不存在则抛出异常
     */
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) throws MapperNotExistException {
        MapperProxyFactory<T> factory = (MapperProxyFactory<T>) mapperFactories.get(type);
        if (factory == null) {
            throw new MapperNotExistException();
        } else {
            try {
                return factory.newInstance(sqlSession);
            } catch (Exception e) {
                throw new MapperNotExistException();
            }
        }
    }

    /**
     * 添加MapperFactory
     * Mapper必须是接口
     * @param type Mapper.class
     */
    public <T> void addMapper(Class<T> type) throws AddMapperException {
        if (type.isInterface() && !hasMapperType(type)) {
            MapperProxyFactory<T> factory = new MapperProxyFactory<>(type);
            mapperFactories.put(type, factory);
        } else {
            throw new AddMapperException();
        }
    }

    public void addMappers(String packageName) throws AddMapperException {
        Set<Class<?>> mappers = ClassScanner.scanPackage(packageName);
        for (Class<?> mapper : mappers) {
            addMapper(mapper);
        }
    }

    private <T> boolean hasMapperType(Class<T> type) {
        return mapperFactories.containsKey(type);
    }
}
