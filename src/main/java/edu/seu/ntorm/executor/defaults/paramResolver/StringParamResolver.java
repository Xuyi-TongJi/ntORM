package edu.seu.ntorm.executor.defaults.paramResolver;

import edu.seu.ntorm.exception.TypeHandlerException;
import edu.seu.ntorm.executor.typeHandler.ParamResolver;

import java.util.Map;

public class StringParamResolver implements ParamResolver {

    @Override
    public void resolve(String paramName,
                        Object params,
                        Class<?> clazz,
                        Map<String, String> paramValue) {
        if (params == null) {
            return;
        }
        if (params instanceof String) {
            paramValue.put(paramName, (String) params);
        } else {
            throw new TypeHandlerException();
        }
    }
}
