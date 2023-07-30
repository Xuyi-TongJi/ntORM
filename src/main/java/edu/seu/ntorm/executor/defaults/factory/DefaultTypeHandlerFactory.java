package edu.seu.ntorm.executor.defaults.factory;

import edu.seu.ntorm.binding.method.MethodParams;
import edu.seu.ntorm.executor.defaults.DefaultTypeHandler;
import edu.seu.ntorm.executor.factory.TypeHandlerFactory;
import edu.seu.ntorm.executor.typeHandler.ParamResolver;
import edu.seu.ntorm.executor.typeHandler.TypeHandler;

import java.util.List;
import java.util.Map;

public class DefaultTypeHandlerFactory implements TypeHandlerFactory {

    private final Map<String, ParamResolver> paramResolvers;

    private final ParamResolver defaultParamResolver;

    public DefaultTypeHandlerFactory(Map<String, ParamResolver> paramResolvers, ParamResolver defaultParamResolver) {
        this.paramResolvers = paramResolvers;
        this.defaultParamResolver = defaultParamResolver;
    }

    @Override
    public TypeHandler getTypeHandler(List<MethodParams> methodParams) {
        return new DefaultTypeHandler(paramResolvers, defaultParamResolver, methodParams);
    }
}
