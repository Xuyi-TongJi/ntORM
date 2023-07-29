package edu.seu.ntorm.executor.defaults.factory;

import edu.seu.ntorm.executor.Executor;
import edu.seu.ntorm.executor.defaults.DefaultResultSetHandler;
import edu.seu.ntorm.executor.factory.ResultSetHandlerFactory;
import edu.seu.ntorm.executor.resultsetHandler.ResultSetHandler;
import edu.seu.ntorm.mapping.BoundSql;
import edu.seu.ntorm.mapping.MappedStatement;

public class DefaultResultSetHandlerFactory implements ResultSetHandlerFactory {

    @Override
    public ResultSetHandler getResultSetHandler(Executor executor, MappedStatement mappedStatement, BoundSql boundSql) {
        return new DefaultResultSetHandler(executor, mappedStatement, boundSql);
    }
}
