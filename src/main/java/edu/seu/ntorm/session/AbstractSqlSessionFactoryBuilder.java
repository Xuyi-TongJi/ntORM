package edu.seu.ntorm.session;

import edu.seu.ntorm.session.defaults.DefaultSqlSessionFactory;

public abstract class AbstractSqlSessionFactoryBuilder implements SqlSessionFactoryBuilder {

    @Override
    public final SqlSessionFactory build(Configuration configuration) {
        return new DefaultSqlSessionFactory(configuration);
    }
}
