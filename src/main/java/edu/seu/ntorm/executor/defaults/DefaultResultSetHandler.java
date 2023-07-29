package edu.seu.ntorm.executor.defaults;

import edu.seu.ntorm.exception.ResultHandlerException;
import edu.seu.ntorm.executor.Executor;
import edu.seu.ntorm.executor.resultsetHandler.ResultSetHandler;
import edu.seu.ntorm.mapping.BoundSql;
import edu.seu.ntorm.mapping.MappedStatement;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DefaultResultSetHandler implements ResultSetHandler {

    private final Executor executor;

    private final MappedStatement mappedStatement;

    private final BoundSql boundSql;

    public DefaultResultSetHandler(Executor executor, MappedStatement mappedStatement, BoundSql boundSql) {
        this.executor = executor;
        this.mappedStatement = mappedStatement;
        this.boundSql = boundSql;
    }

    @Override
    public <E> List<E> handleQueryResultSets(Statement statement) throws SQLException {
        try {
            ResultSet rs = statement.getResultSet();
            // 根据全类名加载并获取Class对象
            Class<?> clazz = Class.forName(boundSql.getResultType());
            return resultSetToObject(rs, clazz);
        } catch (ClassNotFoundException e) {
            throw new ResultHandlerException();
        }
    }

    /**
     * ResultSet映射成clazz类的结果集
     * @param resultSet resultSet
     * @param clazz clazz 结果实体的class对象, 通过反射创建实体类
     * @return 实体类集合
     */
    private <E> List<E> resultSetToObject(ResultSet resultSet, Class<?> clazz) {
        if (resultSet == null || clazz == null) {
            throw new ResultHandlerException();
        }
        try {
            List<E> res = new ArrayList<>();
            int colCount = resultSet.getMetaData().getColumnCount();
            // 迭代器遍历每行
            while (resultSet.next()) {
                E obj = (E) clazz.newInstance();
                Map<String, Object> map = null;
                if (obj instanceof Map) {
                    map = (Map<String, Object>) obj;
                }
                for (int i = 1; i <= colCount; ++i) {
                    Object value = resultSet.getObject(i);
                    String colName = resultSet.getMetaData().getColumnName(i);
                    // E是Map还是实体类
                    if (obj instanceof Map) {
                        map.put(colName, value);
                    } else {
                        // 通过反射set
                        String setMethod = "set" + colName.substring(0, 1).toUpperCase() + colName.substring(1);
                        Method method;
                        if (value instanceof Timestamp) {
                            method = clazz.getMethod(setMethod, Date.class);
                        } else {
                            method = clazz.getMethod(setMethod, value.getClass());
                        }
                        method.invoke(obj, value);
                    }
                }
                res.add(obj);
            }
            return res;
        } catch (Exception e) {
            throw new ResultHandlerException();
        }
    }
}
