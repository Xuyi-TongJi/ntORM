package edu.seu.ntorm.binding.proxy;

import edu.seu.ntorm.binding.method.MethodParams;
import edu.seu.ntorm.binding.method.MethodParamsBuilder;
import edu.seu.ntorm.exception.MapperProxyException;
import edu.seu.ntorm.session.SqlSession;
import edu.seu.ntorm.type.SqlCommandType;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO(Mapper) 动态代理InvocationHandler -> 动态代理逻辑
 * JDK动态代理（只能代理实现了接口的类）
 * @param <T>
 */
public class MapperProxy<T> implements InvocationHandler {

    /**
     * 所有数据库语句操作，都是通过接口名称+方法名称作为key
     */
    private final SqlSession sqlSession;
    private final Class<T> mapperInterface;

    private final MethodParamsBuilder methodParamsBuilder;

    public MapperProxy(SqlSession sqlSession,
                       Class<T> mapperInterface,
                       MethodParamsBuilder methodParamsBuilder) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
        this.methodParamsBuilder = methodParamsBuilder;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Object类的方法不需要代理执行，直接执行该方法
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        } else {
            // build method params
            List<MethodParams> methodParams = methodParamsBuilder.buildParams(method);
            if (args.length != methodParams.size()) {
                throw new MapperProxyException();
            }
            int len = args.length;
            for (int i = 0; i < len; ++i) {
                methodParams.get(i).setParams(args[i]);
            }
            // statementId
            String statementId = mapperInterface.getCanonicalName() + "." + method.getName(); // String methodId = namespace + "." + id;
            // select or update
            SqlCommandType commandType = sqlSession.getSqlCommandType(statementId);
            if (SqlCommandType.SELECT.getTypeName().equals(commandType.getTypeName())) {
                Class<?> returnType = method.getReturnType();
                if (List.class.isAssignableFrom(returnType)) {
                    return sqlSession.select(statementId, methodParams);
                } else {
                    return sqlSession.selectOne(statementId, methodParams);
                }
            } else {
                Class<?> returnType = method.getReturnType();
                Long changeRow = sqlSession.update(statementId, methodParams);
                if (Long.class.isAssignableFrom(returnType)) {
                    return changeRow;
                } else {
                    return null;
                }
            }
        }
    }
}
