package edu.seu.ntorm.executor.defaults;

import edu.seu.ntorm.binding.method.MethodParams;
import edu.seu.ntorm.executor.typeHandler.ParamResolver;
import edu.seu.ntorm.executor.typeHandler.TypeHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参数解析器
 * 策略模式
 */
public class DefaultTypeHandler implements TypeHandler {

    private final List<MethodParams> methodParams;

    /**
     * 最终映射完成后的paramValue(paramName 与 paramValue的映射)
     */
    private final Map<String, String> paramValue = new HashMap<>();

    private final Map<String, ParamResolver> resolvers;

    private final ParamResolver defaultParamResolver;

    public DefaultTypeHandler(Map<String, ParamResolver> resolvers,
                              ParamResolver defaultParamResolver,
                              List<MethodParams> methodParams) {
        this.resolvers = resolvers;
        this.defaultParamResolver = defaultParamResolver;
        this.methodParams = methodParams;
    }

    @Override
    public void resolveParams() {
        for (MethodParams mp : methodParams) {
            resolveParams(mp.getParamName(), mp.getParams(), mp.getClazz());
        }
    }

    @Override
    public Map<String, String> getParamValueMap() {
        return paramValue;
    }

    private void resolveParams(String paramName, Object params, Class<?> clazz) {
        String name = clazz.getCanonicalName();
        if (!resolvers.containsKey(name)) {
            // 用默认处理器处理(pojo)
            defaultParamResolver.resolve(paramName, params, clazz, paramValue);
        }
        // 用相应的处理器处理(Integer Long Date Map)
        ParamResolver resolver = resolvers.get(name);
        resolver.resolve(paramName, params, clazz, paramValue);
    }
}
