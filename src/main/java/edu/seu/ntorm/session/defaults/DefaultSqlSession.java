package edu.seu.ntorm.session.defaults;

import edu.seu.ntorm.binding.method.MethodParams;
import edu.seu.ntorm.exception.MapperNotExistException;
import edu.seu.ntorm.executor.Executor;
import edu.seu.ntorm.mapping.BoundSql;
import edu.seu.ntorm.mapping.MappedStatement;
import edu.seu.ntorm.env.Configuration;
import edu.seu.ntorm.session.SqlSession;
import edu.seu.ntorm.type.SqlCommandType;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class DefaultSqlSession implements SqlSession {

    /**
     * Configuration -> MapperRegistry + MappedStatements
     */
    private final Configuration configuration;

    private final Executor executor;

    public DefaultSqlSession(Configuration configuration,
                             Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public <T> T selectOne(String statementId, List<MethodParams> parameters) {
        List<T> query = select(statementId, parameters);
        if (! CollectionUtils.isEmpty(query)) {
            return query.get(0);
        }
        return null;
    }

    @Override
    public <T> List<T> select(String statementId, List<MethodParams> parameters) {
        MappedStatement mappedStatement = configuration.getMappedStatement(statementId);
        if (mappedStatement == null) {
            return new ArrayList<>();
        }
        BoundSql boundSql = mappedStatement.getBoundSql();
        return executor.query(mappedStatement, parameters, boundSql);
    }

    @Override
    public Long update(String statementId, List<MethodParams> parameters) {
        MappedStatement mappedStatement = configuration.getMappedStatement(statementId);
        if (mappedStatement == null) {
            return 0L;
        }
        BoundSql boundSql = mappedStatement.getBoundSql();
        return executor.update(mappedStatement, parameters, boundSql);
    }

    @Override
    public <T> T getMapper(Class<T> type) throws MapperNotExistException {
        return configuration.getMapper(type, this);
    }

    @Override
    public SqlCommandType getSqlCommandType(String statementId) {
        return configuration.getMappedStatement(statementId).getSqlCommandType();
    }
}
