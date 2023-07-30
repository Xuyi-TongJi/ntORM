package edu.seu.ntorm.executor.typeHandler;

import java.util.Map;

public interface ParamResolver {

    void resolve(String paramName, Object params, Class<?> clazz, Map<String, String> paramValue);
}
