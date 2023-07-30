package edu.seu.ntorm.reflect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 反射工具类
 */
public class ReflectUtil {

    private static final Map<String, List<Method>> getMethodsCache = new ConcurrentHashMap<>();

    public static List<Method> getAllGetMethods(Class<?> clazz) {
        String name = clazz.getCanonicalName();
        if (getMethodsCache.containsKey(name)) {
            return getMethodsCache.get(name);
        }
        Method[] methods = clazz.getMethods();
        List<Method> result = new ArrayList<>();
        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                result.add(method);
            }
        }
        getMethodsCache.put(name, result);
        return result;
    }
}
