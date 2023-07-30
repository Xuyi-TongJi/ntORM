package edu.seu.ntorm.binding.proxy;

import edu.seu.ntorm.binding.method.MethodParamsBuilder;
import edu.seu.ntorm.session.SqlSession;

import java.lang.reflect.Proxy;

/**
 * T MapperInterface的代理工厂类
 * 用以通过SqlSession构造Mapper Interface代理对象 MapperProxy
 */
public class MapperProxyFactory<T> {

    private final Class<T> mapperInterface;

    private final MethodParamsBuilder methodParamsBuilder;

    public MapperProxyFactory(Class<T> mapperInterface, MethodParamsBuilder methodParamsBuilder) {
        this.mapperInterface = mapperInterface;
        this.methodParamsBuilder = methodParamsBuilder;
    }

    /**
     * 构造代理类工厂方法
     * @return 代理T对象 Proxy
     */
    public T newInstance(SqlSession sqlSession) {
        // InvocationHandler
        MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface, methodParamsBuilder);
        // Proxy.newProxyInstance返回代理对象[mapperInterface的代理对象]
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }
}
