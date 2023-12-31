package edu.seu.ntorm.ntDb;

import edu.seu.ntorm.executor.defaults.factory.DefaultExecutorFactory;
import edu.seu.ntorm.executor.defaults.factory.DefaultResultSetHandlerFactory;
import edu.seu.ntorm.executor.defaults.factory.DefaultStatementHandlerFactory;
import edu.seu.ntorm.executor.defaults.factory.DefaultTypeHandlerFactory;
import edu.seu.ntorm.executor.defaults.paramResolver.*;
import edu.seu.ntorm.executor.factory.ExecutorFactory;
import edu.seu.ntorm.executor.factory.ResultSetHandlerFactory;
import edu.seu.ntorm.executor.factory.StatementHandlerFactory;
import edu.seu.ntorm.executor.factory.TypeHandlerFactory;
import edu.seu.ntorm.executor.typeHandler.ParamResolver;
import edu.seu.ntorm.type.TypeAliasRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class NtDbAutoConfigurator {

    @Bean
    public TypeAliasRegistry getTypeAliasMap() {
        Map<String, Class<?>> maps = new HashMap<>();
        // ntDb目前支持string, int32, int64
        maps.put("int32", Integer.class);
        maps.put("int64", Long.class);
        maps.put("string", String.class);
        return new TypeAliasRegistry(maps);
    }

    @Bean
    @ConditionalOnMissingBean(value = {DateFormat.class})
    public DateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    @Bean("resolvers")
    public Map<String, ParamResolver> getResolvers(DateFormat dateFormat) {
        Map<String, ParamResolver> result = new HashMap<>();
        ParamResolver numResolver = new NumberParamResolver();
        ParamResolver dateResolver = new DateParamResolver(dateFormat);
        ParamResolver mapResolver = new MapParamResolver(dateFormat);
        ParamResolver stringResolver = new StringParamResolver();
        result.put(Integer.class.getCanonicalName(), numResolver);
        result.put(Long.class.getCanonicalName(), numResolver);
        result.put(Map.class.getCanonicalName(), mapResolver);
        result.put(String.class.getCanonicalName(), stringResolver);
        result.put(Date.class.getCanonicalName(), dateResolver);
        return result;
    }

    @Bean("defaultParamResolver")
    public ParamResolver getDefaultParamResolver(DateFormat dateFormat) {
        // 默认参数解析器是pojo解析器
        return new PojoParamResolver(dateFormat);
    }

    @ConditionalOnMissingBean(value = {TypeHandlerFactory.class})
    @Bean
    public TypeHandlerFactory getTypeHandlerFactory(
            @Qualifier("resolvers") Map<String, ParamResolver> resolvers,
            @Qualifier("defaultParamResolver") ParamResolver resolver) {
        return new DefaultTypeHandlerFactory(resolvers, resolver);
    }

    @ConditionalOnMissingBean(value = {StatementHandlerFactory.class})
    @Bean
    public StatementHandlerFactory getStatementHandlerFactory() {
        return new DefaultStatementHandlerFactory();
    }

    @ConditionalOnMissingBean(value = {ResultSetHandlerFactory.class})
    @Bean
    public ResultSetHandlerFactory getResultSetHandlerFactory() {
        return new DefaultResultSetHandlerFactory();
    }

    @ConditionalOnMissingBean(value = {ExecutorFactory.class})
    @Bean
    public ExecutorFactory getExecutorFactory() {
        return new DefaultExecutorFactory();
    }

}
