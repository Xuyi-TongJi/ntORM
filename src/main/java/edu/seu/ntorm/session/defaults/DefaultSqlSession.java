package edu.seu.ntorm.session.defaults;

import edu.seu.ntorm.exception.MapperNotExistException;
import edu.seu.ntorm.mapping.BoundSql;
import edu.seu.ntorm.session.env.Environment;
import edu.seu.ntorm.mapping.MappedStatement;
import edu.seu.ntorm.session.env.Configuration;
import edu.seu.ntorm.session.SqlSession;

import java.sql.Connection;
import java.sql.SQLException;

public class DefaultSqlSession implements SqlSession {

    /**
     * Configuration -> MapperRegistry + MappedStatements
     */
    private final Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T selectOne(String statement) {
        return null;
    }

    @Override
    public <T> T selectOne(String statement, Object parameters) {
        MappedStatement mappedStatement = configuration.getMappedStatement(statement);
        Environment env = configuration.getEnvironment();
        try {
            Connection conn = env.getDataSourceFactory().getDataSource().getConnection();
            BoundSql boundSql = mappedStatement.getBoundSql();
            // TODO
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public <T> T getMapper(Class<T> type) throws MapperNotExistException {
        return configuration.getMapper(type, this);
    }
}
