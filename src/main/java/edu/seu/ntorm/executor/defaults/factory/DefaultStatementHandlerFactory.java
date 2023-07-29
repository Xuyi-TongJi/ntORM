package edu.seu.ntorm.executor.defaults.factory;

import edu.seu.ntorm.executor.Executor;
import edu.seu.ntorm.executor.defaults.PreparedStatementHandler;
import edu.seu.ntorm.executor.defaults.SimpleStatementHandler;
import edu.seu.ntorm.executor.factory.StatementHandlerFactory;
import edu.seu.ntorm.executor.statementHandler.StatementHandler;
import edu.seu.ntorm.mapping.BoundSql;
import edu.seu.ntorm.mapping.MappedStatement;

import java.util.Map;

public class DefaultStatementHandlerFactory implements StatementHandlerFactory {
    @Override
    public StatementHandler getStatementHandler(Executor executor,
                                                MappedStatement mappedStatement,
                                                Map<String, Object> parameter,
                                                BoundSql boundSql) {
        return new SimpleStatementHandler(executor, mappedStatement, parameter, boundSql);
    }

    @Override
    public StatementHandler getPreparedStatementHandler(Executor executor,
                                                        MappedStatement mappedStatement,
                                                        Map<String, Object> parameter,
                                                        BoundSql boundSql) {
        return new PreparedStatementHandler(executor, mappedStatement, parameter, boundSql);
    }
}
