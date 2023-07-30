package edu.seu.ntorm.executor.defaults.paramResolver;

import edu.seu.ntorm.exception.TypeHandlerException;
import edu.seu.ntorm.executor.typeHandler.ParamResolver;

import java.util.Map;

/**
 * 解析NumberParam
 */
public class NumberParamResolver implements ParamResolver {

    @Override
    public void resolve(String paramName,
                        Object params,
                        Class<?> clazz,
                        Map<String, String> paramValue) {
        if (params == null) {
            return;
        }
        if (params instanceof Long || params instanceof Integer) {
            paramValue.put(paramName, String.valueOf(params));
        } else {
            throw new TypeHandlerException();
        }
    }
}
