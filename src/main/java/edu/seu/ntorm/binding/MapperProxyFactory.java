package edu.seu.ntorm.binding;

import edu.seu.ntorm.session.SqlSession;

import java.lang.reflect.Proxy;

/**
 * T MapperInterface的代理工厂类
 * 用以通过SqlSession构造Mapper Interface代理对象 MapperProxy
 */
public class MapperProxyFactory<T> {

    private final Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    /**
     * 构造代理类工厂方法
     * @return 代理T对象 Proxy
     */
    public T newInstance(SqlSession sqlSession) {
        // InvocationHandler
        MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface);
        // Proxy.newProxyInstance返回代理对象[mapperInterface的代理对象]
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }
}
