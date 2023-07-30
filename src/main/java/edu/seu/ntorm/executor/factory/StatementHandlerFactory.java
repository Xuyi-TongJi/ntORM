package edu.seu.ntorm.executor.factory;

import edu.seu.ntorm.executor.Executor;
import edu.seu.ntorm.executor.resultsetHandler.ResultSetHandler;
import edu.seu.ntorm.executor.statementHandler.StatementHandler;
import edu.seu.ntorm.mapping.BoundSql;
import edu.seu.ntorm.mapping.MappedStatement;

import java.util.Map;


public interface StatementHandlerFactory {

    StatementHandler getStatementHandler(Executor executor,
                                         MappedStatement mappedStatement,
                                         Map<String, String> parameter,
                                         BoundSql boundSql);

    StatementHandler getPreparedStatementHandler(Executor executor,
                                                 MappedStatement mappedStatement,
                                                 Map<String, String> parameter,
                                                 BoundSql boundSql);
}
