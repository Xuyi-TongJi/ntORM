package edu.seu.ntorm.executor.defaults;

import edu.seu.ntorm.exception.ExecutorException;
import edu.seu.ntorm.executor.BaseExecutor;
import edu.seu.ntorm.executor.resultsetHandler.ResultSetHandler;
import edu.seu.ntorm.executor.statementHandler.StatementHandler;
import edu.seu.ntorm.mapping.BoundSql;
import edu.seu.ntorm.mapping.MappedStatement;
import edu.seu.ntorm.session.env.Configuration;
import edu.seu.ntorm.transaction.Transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class DefaultExecutor extends BaseExecutor {

    public DefaultExecutor(Transaction transaction) {
        super(transaction);
    }

    /**
     * Facade方法
     * @param ms mappedStatement
     * @param parameter 真正的参数值
     * @param boundSql boundSql
     * @return 结果实体类集合
     */
    @Override
    protected <E> List<E> doQuery(MappedStatement ms, Map<String, Object> parameter, BoundSql boundSql) {
        try {
            Configuration config = ms.getConfiguration();
            // StatementHandler
            StatementHandler statementHandler = config.buildStatementHandler(this, ms, parameter, boundSql);
            Connection connection = transaction.getConnection();
            // 在连接中实例化statement并执行SQL
            Statement statement = statementHandler.prepare(connection);
            ResultSet rs = statementHandler.execute(statement);

            // ResultSetHandler
            ResultSetHandler resultSetHandler = config.buildResultSetHandler(this, ms, boundSql);
            return resultSetHandler.handleQueryResultSets(statement);
        } catch (Exception e) {
            throw new ExecutorException();
        }
    }
}
