package edu.seu.ntorm.executor.factory;

import edu.seu.ntorm.executor.Executor;
import edu.seu.ntorm.executor.resultsetHandler.ResultSetHandler;
import edu.seu.ntorm.mapping.BoundSql;
import edu.seu.ntorm.mapping.MappedStatement;

public interface ResultSetHandlerFactory {

    ResultSetHandler getResultSetHandler(Executor executor,
                                         MappedStatement mappedStatement,
                                         BoundSql boundSql);
}
