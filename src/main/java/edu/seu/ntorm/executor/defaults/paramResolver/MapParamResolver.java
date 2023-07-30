package edu.seu.ntorm.executor.defaults.paramResolver;

import edu.seu.ntorm.exception.TypeHandlerException;
import edu.seu.ntorm.executor.typeHandler.ParamResolver;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

public class MapParamResolver implements ParamResolver {


    private final DateFormat dateFormat;

    public MapParamResolver(DateFormat dateFormat) {
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
        if (params instanceof Map) {
            Map<Object, Object> map = (Map<Object, Object>) params;
            for (Object key : map.keySet()) {
                Object value = map.get(key);
                if (value != null) {
                    // key != null && value != null
                    paramValue.put(objectToString(key), objectToString(value));
                }
            }
        } else {
            throw new TypeHandlerException();
        }
    }

    private String objectToString(Object obj) {
        String value;
        if (obj instanceof Integer || obj instanceof Long) {
            value = String.valueOf(obj);
        } else if (obj instanceof String) {
            value = (String) obj;
        } else if (obj instanceof Date) {
            value = dateFormat.format(obj);
        } else {
            value = obj.toString();
        }
        return value;
    }
}
