package edu.seu.ntorm.executor.defaults;

import edu.seu.ntorm.executor.Executor;
import edu.seu.ntorm.executor.resultsetHandler.ResultSetHandler;
import edu.seu.ntorm.executor.statementHandler.BaseStatementHandler;
import edu.seu.ntorm.mapping.BoundSql;
import edu.seu.ntorm.mapping.MappedStatement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class SimpleStatementHandler extends BaseStatementHandler {

    public SimpleStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameterObject, ResultSetHandler resultSetHandler, BoundSql boundSql) {
        super(executor, mappedStatement, parameterObject, resultSetHandler, boundSql);
    }

    @Override
    protected Statement instantiateStatement(Connection connection) throws SQLException {
        return null;
    }

    @Override
    protected void configureStatement(Statement statement) {

    }


    @Override
    public void parameterize(Statement statement) throws SQLException {

    }

    @Override
    public <E> List<E> query(Statement statement) {
        return null;
    }
}
