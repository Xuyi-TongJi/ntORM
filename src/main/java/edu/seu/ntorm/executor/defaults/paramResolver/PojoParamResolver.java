package edu.seu.ntorm.executor.defaults.paramResolver;

import edu.seu.ntorm.exception.TypeHandlerException;
import edu.seu.ntorm.executor.typeHandler.ParamResolver;
import edu.seu.ntorm.reflect.ReflectUtil;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PojoParamResolver implements ParamResolver {

    private final DateFormat dateFormat;

    public PojoParamResolver(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * 解析pojo
     * 用不到paramName
     */
    @Override
    public void resolve(String paramName,
                        Object params,
                        Class<?> clazz,
                        Map<String, String> paramValue) {
        if (! clazz.isInstance(params)) {
            // check
            // params一定是clazz的实体类
            throw new TypeHandlerException();
        }
        try {
            List<Method> allGetMethods = ReflectUtil.getAllGetMethods(clazz);
            for (Method method : allGetMethods) {
                // 执行get方法
                Object value = method.invoke(params);
                if (value == null) {
                    continue;
                }
                String param = method.getName().substring(3).toLowerCase(); // getId -> Id
                if (value instanceof Integer || value instanceof Long) {
                    paramValue.put(param, String.valueOf(value));
                } else if (value instanceof String) {
                    paramValue.put(param, (String) value);
                } else if (value instanceof Date) {
                    String format = dateFormat.format((Date) value);
                    paramValue.put(param, format);
                } else {
                    // 强制调用toString
                    paramValue.put(param, value.toString());
                }
            }
        } catch (Exception e) {
            throw new TypeHandlerException();
        }
    }
}
