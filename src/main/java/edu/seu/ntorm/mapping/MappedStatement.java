package edu.seu.ntorm.mapping;

import edu.seu.ntorm.session.env.Configuration;
import edu.seu.ntorm.type.SqlCommandType;

import java.util.Map;

public class MappedStatement {

    private Configuration configuration;

    private String id;

    private SqlCommandType sqlCommandType;

    private String parameterType;

    private String resultType;

    private String sql;

    private Map<Integer, String> parameter;

    private MappedStatement() {}

    public static MappedStatement build(Configuration configuration,
                                        String id,
                                        SqlCommandType sqlCommandType,
                                        String parameterType,
                                        String resultType,
                                        String sql,
                                        Map<Integer, String> parameter) {
        MappedStatement mappedStatement = new MappedStatement();
        mappedStatement.configuration = configuration;
        mappedStatement.id = id;
        mappedStatement.sqlCommandType = sqlCommandType;
        mappedStatement.parameterType = parameterType;
        mappedStatement.resultType = resultType;
        mappedStatement.sql = sql;
        mappedStatement.parameter = parameter;
        return mappedStatement;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(SqlCommandType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Map<Integer, String> getParameter() {
        return parameter;
    }

    public void setParameter(Map<Integer, String> parameter) {
        this.parameter = parameter;
    }

    public BoundSql getBoundSql() {
        return new BoundSql(sql, parameter, parameterType, resultType);
    }
}
