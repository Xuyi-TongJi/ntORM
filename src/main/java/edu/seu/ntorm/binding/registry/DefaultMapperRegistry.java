package edu.seu.ntorm.binding.registry;

import cn.hutool.core.lang.ClassScanner;
import edu.seu.ntorm.binding.proxy.MapperProxyFactory;
import edu.seu.ntorm.binding.method.MethodParamsBuilder;
import edu.seu.ntorm.exception.AddMapperException;
import edu.seu.ntorm.exception.MapperNotExistException;
import edu.seu.ntorm.ntDb.DefaultBuilderAutoConfigurator;
import edu.seu.ntorm.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Mapper注册机
 * 上层在使用时可以提供一个包的路径即可完成扫描与Mapper(接口对象)的注册(生成代理类并注册)
 * 本质是一个MapperProxyFactory的Map集合
 */
@ConditionalOnBean(value = {DefaultBuilderAutoConfigurator.class})
@Component
public class DefaultMapperRegistry extends MapperRegistry {

    /**
     * Mapper.class -> MapperProxyFactory
     */
    private final Map<Class<?>, MapperProxyFactory<?>> mapperFactories = new HashMap<>();


    @Autowired
    private MethodParamsBuilder methodParamsBuilder;

    @Override
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

    @Override
    public <T> void addMapper(Class<T> type) throws AddMapperException {
        if (type.isInterface() && !hasMapper(type)) {
            MapperProxyFactory<T> factory = new MapperProxyFactory<>(type, methodParamsBuilder);
            mapperFactories.put(type, factory);
        } else {
            throw new AddMapperException();
        }
    }

    @Override
    public void addMappers(String packageName) throws AddMapperException {
        Set<Class<?>> mappers = ClassScanner.scanPackage(packageName);
        for (Class<?> mapper : mappers) {
            addMapper(mapper);
        }
    }

    @Override
    public <T> boolean hasMapper(Class<T> type) {
        return mapperFactories.containsKey(type);
    }

}
