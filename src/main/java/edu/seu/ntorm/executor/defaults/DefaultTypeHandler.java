package edu.seu.ntorm.executor.defaults;

import edu.seu.ntorm.executor.typeHandler.ParamResolver;
import edu.seu.ntorm.executor.typeHandler.TypeHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * 参数解析器
 * 策略模式
 */
public class DefaultTypeHandler implements TypeHandler {

    /**
     * 最终映射完成后的paramValue(paramName 与 paramValue的映射)
     */
    private final Map<String, String> paramValue = new HashMap<>();

    private final Map<String, ParamResolver> resolvers;

    private final ParamResolver defaultParamResolver;

    public DefaultTypeHandler(Map<String, ParamResolver> resolvers, ParamResolver defaultParamResolver) {
        this.resolvers = resolvers;
        this.defaultParamResolver = defaultParamResolver;
    }

    @Override
    public void resolveParams(String paramName, Object params, Class<?> clazz) {
        String name = clazz.getCanonicalName();
        if (!resolvers.containsKey(name)) {
            // 用默认处理器处理(pojo)
            defaultParamResolver.resolve(paramName, params, clazz, paramValue);
        }
        // 用相应的处理器处理(Integer Long Date Map)
        ParamResolver resolver = resolvers.get(name);
        resolver.resolve(paramName, params, clazz, paramValue);
    }

    @Override
    public Map<String, String> getParamValueMap() {
        return paramValue;
    }
}
