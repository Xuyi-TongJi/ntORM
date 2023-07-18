package edu.seu.ntorm.binding;

import edu.seu.ntorm.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * DAO(Mapper) 动态代理InvocationHandler -> 动态代理逻辑
 * JDK动态代理（只能代理实现了接口的类）
 * @param <T>
 */
public class MapperProxy<T> implements InvocationHandler {

    /**
     * 所有数据库语句操作，都是通过接口名称+方法名称作为key
     */
    private SqlSession sqlSession;
    private final Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Object类的方法不需要代理执行，直接执行该方法
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        } else {
            // 代理方法的逻辑
            System.out.println(mapperInterface.getName());
            System.out.println(method.getName());
            System.out.println((String)args[0]);
            return null;
        }
    }
}
