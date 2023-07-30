package edu.seu.ntorm.executor.statementHandler;

import edu.seu.ntorm.exception.ExecutorException;
import edu.seu.ntorm.executor.Executor;
import edu.seu.ntorm.mapping.BoundSql;
import edu.seu.ntorm.mapping.MappedStatement;
import edu.seu.ntorm.env.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public abstract class BaseStatementHandler implements StatementHandler {

    protected final Configuration configuration;

    protected final Executor executor;

    protected final MappedStatement mappedStatement;

    /**
     * 真正的参数值 -> 来自于Mapper中的方法参数
     * User queryUserById(Long id) -> 方法参数中的Long即为parameterObject
     */
    protected final Map<String, Object> parameterObject;

    protected final BoundSql boundSql;

    public BaseStatementHandler(Executor executor,
                                MappedStatement mappedStatement,
                                Map<String, Object> parameterObject,
                                BoundSql boundSql) {
        this.executor = executor;
        this.mappedStatement = mappedStatement;
        this.configuration = mappedStatement.getConfiguration();
        this.parameterObject = parameterObject;
        this.boundSql = boundSql;
    }

    @Override
    public Statement prepare(Connection connection) throws SQLException {
        try {
            // 实例化Statement
            Statement statement = instantiateStatement(connection);
            configureStatement(statement);
            return statement;
        } catch (Exception e) {
            throw new ExecutorException();
        }
    }

    /**
     * 实例化SQL
     * @param connection 连接
     * @return SQL Statement
     */
    protected abstract Statement instantiateStatement(Connection connection) throws SQLException;

    /**
     * 配置statement参数
     * @param statement statement
     */
    protected abstract void configureStatement(Statement statement);
}
