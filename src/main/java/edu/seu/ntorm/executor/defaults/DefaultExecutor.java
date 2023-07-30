package edu.seu.ntorm.executor.defaults;

import edu.seu.ntorm.binding.method.MethodParams;
import edu.seu.ntorm.exception.ExecutorException;
import edu.seu.ntorm.executor.BaseExecutor;
import edu.seu.ntorm.executor.resultsetHandler.ResultSetHandler;
import edu.seu.ntorm.executor.statementHandler.StatementHandler;
import edu.seu.ntorm.executor.typeHandler.TypeHandler;
import edu.seu.ntorm.mapping.BoundSql;
import edu.seu.ntorm.mapping.MappedStatement;
import edu.seu.ntorm.env.Configuration;
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
     * @param ms mappedStatement 不可修改
     * @param methodParams 由sqlSession传递来的MethodParams
     * @param boundSql boundSql 可以修改
     * @return 结果实体类集合
     */
    @Override
    protected <E> List<E> doQuery(MappedStatement ms, List<MethodParams> methodParams, BoundSql boundSql) {
        try {
            Configuration config = ms.getConfiguration();
            Statement stmt = doExecute(ms, methodParams, boundSql);

            // ResultSetHandler
            ResultSetHandler resultSetHandler = config.buildResultSetHandler(this, ms, boundSql);
            return resultSetHandler.handleQueryResultSets(stmt);
        } catch (Exception e) {
            throw new ExecutorException();
        }
    }

    @Override
    protected Long doUpdate(MappedStatement ms, List<MethodParams> methodParams, BoundSql boundSql) {
        try {
            Configuration config = ms.getConfiguration();
            Statement stmt = doExecute(ms, methodParams, boundSql);

            // ResultSetHandler
            ResultSetHandler resultSetHandler = config.buildResultSetHandler(this, ms, boundSql);
            return resultSetHandler.handleUpdateResultSets(stmt);
        } catch (Exception e) {
            throw new ExecutorException();
        }
    }


    private Statement doExecute(MappedStatement ms, List<MethodParams> methodParams, BoundSql boundSql) {
        try {
            Configuration config = ms.getConfiguration();
            // TypeHandler
            TypeHandler typeHandler = config.buildTypeHandler(methodParams);
            typeHandler.resolveParams();
            Map<String, String> paramValueMap = typeHandler.getParamValueMap();

            // StatementHandler
            StatementHandler statementHandler = config.buildStatementHandler(this, ms, paramValueMap, boundSql);
            Connection connection = transaction.getConnection();
            // 在连接中实例化statement并执行SQL
            Statement statement = statementHandler.prepare(connection);
            statementHandler.execute(statement);
            return statement;
        } catch (Exception e) {
            throw new ExecutorException();
        }
    }
}
