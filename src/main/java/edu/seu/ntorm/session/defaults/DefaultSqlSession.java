package edu.seu.ntorm.session.defaults;

import edu.seu.ntorm.exception.MapperNotExistException;
import edu.seu.ntorm.executor.Executor;
import edu.seu.ntorm.mapping.BoundSql;
import edu.seu.ntorm.mapping.MappedStatement;
import edu.seu.ntorm.session.env.Configuration;
import edu.seu.ntorm.session.SqlSession;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public <T> T selectOne(String statementId, Map<String, Object> parameters) {
        List<T> query = select(statementId, parameters);
        if (! CollectionUtils.isEmpty(query)) {
            return query.get(0);
        }
        return null;
    }

    @Override
    public <T> List<T> select(String statementId, Map<String, Object> parameters) {
        MappedStatement mappedStatement = configuration.getMappedStatement(statementId);
        if (mappedStatement == null) {
            return new ArrayList<>();
        }
        BoundSql boundSql = mappedStatement.getBoundSql();
        return executor.query(mappedStatement, parameters, boundSql);
    }

    @Override
    public <T> T getMapper(Class<T> type) throws MapperNotExistException {
        return configuration.getMapper(type, this);
    }
}
