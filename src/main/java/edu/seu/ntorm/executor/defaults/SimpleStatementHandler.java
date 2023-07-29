package edu.seu.ntorm.executor.defaults;

import com.alibaba.druid.util.StringUtils;
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
import java.util.Map;

/**
 * Statement处理器
 * 不能处理PreparedStatement和CallableStatement
 */
public class SimpleStatementHandler extends BaseStatementHandler {

    public SimpleStatementHandler(Executor executor, MappedStatement mappedStatement, Map<String, Object> parameterObject, BoundSql boundSql) {
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
        // N/A
    }

    @Override
    public ResultSet execute(Statement statement) {
        String sql = doParameterizeSql();
        try {
            // 真正执行SQL
            statement.execute(sql);
            return statement.getResultSet();
        } catch (SQLException e) {
            throw new StatementHandlerException();
        }
    }

    /**
     * 参数化boundSql中带有?的原始sql, 基于字符串拼接(并不能解决sql注入的问题)
     * 这一步真正把parameter匹配到sql中
     * boundSql -> 提供sql和parameterMapping(?与参数名的匹配)
     * @return 匹配后的sql
     */
    private String doParameterizeSql() {
        String sql = boundSql.getSql();
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (char c : sql.toCharArray()) {
            if (c == '?') {
                String parameterName = boundSql.getParameterMappings().get(index++);
                if (StringUtils.isEmpty(parameterName)) {
                    throw new StatementHandlerException();
                }
                Object value = parameterObject.get(parameterName);
                if (value == null) {
                    throw new StatementHandlerException();
                }
                sb.append(value);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
