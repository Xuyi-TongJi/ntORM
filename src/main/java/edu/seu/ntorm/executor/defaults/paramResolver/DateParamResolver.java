package edu.seu.ntorm.executor.defaults.paramResolver;

import edu.seu.ntorm.exception.TypeHandlerException;
import edu.seu.ntorm.executor.typeHandler.ParamResolver;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

public class DateParamResolver implements ParamResolver {

    private final DateFormat dateFormat;

    public DateParamResolver(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public void resolve(String paramName,
                        Object params,
                        Class<?> clazz,
                        Map<String, String> paramValue) {
        if (params == null) {
            return;
        }
        if (params instanceof Date) {
            String format = dateFormat.format((Date) params);
            paramValue.put(paramName, format);
        } else {
            throw new TypeHandlerException();
        }
    }
}
