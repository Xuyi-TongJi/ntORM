package edu.seu.ntorm.session.defaults;

import edu.seu.ntorm.session.Configuration;
import edu.seu.ntorm.session.SqlSession;
import edu.seu.ntorm.session.SqlSessionFactory;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
