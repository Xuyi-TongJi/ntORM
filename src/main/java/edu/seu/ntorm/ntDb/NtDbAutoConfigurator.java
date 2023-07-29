package edu.seu.ntorm.ntDb;

import edu.seu.ntorm.executor.defaults.factory.DefaultExecutorFactory;
import edu.seu.ntorm.executor.defaults.factory.DefaultResultSetHandlerFactory;
import edu.seu.ntorm.executor.defaults.factory.DefaultStatementHandlerFactory;
import edu.seu.ntorm.executor.factory.ExecutorFactory;
import edu.seu.ntorm.executor.factory.ResultSetHandlerFactory;
import edu.seu.ntorm.executor.factory.StatementHandlerFactory;
import edu.seu.ntorm.type.TypeAliasRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
