package edu.seu.ntorm.executor.defaults;

import edu.seu.ntorm.exception.StatementHandlerException;
import edu.seu.ntorm.executor.Executor;
import edu.seu.ntorm.executor.statementHandler.BaseStatementHandler;
import edu.seu.ntorm.mapping.BoundSql;
import edu.seu.ntorm.mapping.MappedStatement;
import edu.seu.ntorm.ntDb.SqlStatementConfig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Statement处理器
 * 不能处理PreparedStatement和CallableStatement
 */
public class SimpleStatementHandler extends BaseStatementHandler {

    public SimpleStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        super(executor, mappedStatement, parameterObject, boundSql);
    }

    @Override
    protected Statement instantiateStatement(Connection connection) throws SQLException {
        return connection.createStatement();
    }

    @Override
    protected void configureStatement(Statement statement) {
        SqlStatementConfig sqlConfig = configuration.getSqlStatementConfig();
        if (sqlConfig == null) {
            return;
        }
        try {
            statement.setQueryTimeout(sqlConfig.getQueryTimeout());
        } catch (SQLException e) {
            throw new StatementHandlerException();
        }
    }


    @Override
    public void parameterize(Statement statement) throws SQLException {

    }

    @Override
    public ResultSet execute(Statement statement) {
        String sql = boundSql.getSql();
        try {
            // 真正执行SQL
            statement.execute(sql);
            return statement.getResultSet();
        } catch (SQLException e) {
            throw new StatementHandlerException();
        }
    }
}
