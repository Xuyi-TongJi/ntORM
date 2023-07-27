package edu.seu.ntorm.executor.factory;

import edu.seu.ntorm.executor.Executor;
import edu.seu.ntorm.executor.resultsetHandler.ResultSetHandler;
import edu.seu.ntorm.executor.statementHandler.StatementHandler;
import edu.seu.ntorm.mapping.BoundSql;
import edu.seu.ntorm.mapping.MappedStatement;


public interface StatementHandlerFactory {

    StatementHandler getStatementHandler(Executor executor,
                                         MappedStatement mappedStatement,
                                         Object parameter,
                                         ResultSetHandler resultHandler,
                                         BoundSql boundSql);

    StatementHandler getPreparedStatementHandler(Executor executor,
                                                 MappedStatement mappedStatement,
                                                 Object parameter,
                                                 ResultSetHandler resultHandler,
                                                 BoundSql boundSql);
}
