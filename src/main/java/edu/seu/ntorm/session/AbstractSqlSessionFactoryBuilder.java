package edu.seu.ntorm.session;

import edu.seu.ntorm.session.defaults.DefaultSqlSessionFactory;
import edu.seu.ntorm.env.Configuration;

public abstract class AbstractSqlSessionFactoryBuilder implements SqlSessionFactoryBuilder {

    @Override
    public final SqlSessionFactory build(Configuration configuration) {
        return new DefaultSqlSessionFactory(configuration);
    }
}
