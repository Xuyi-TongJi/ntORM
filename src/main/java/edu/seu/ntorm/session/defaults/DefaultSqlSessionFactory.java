package edu.seu.ntorm.session.defaults;

import edu.seu.ntorm.binding.registry.MapperRegistry;
import edu.seu.ntorm.session.SqlSession;
import edu.seu.ntorm.session.SqlSessionFactory;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final MapperRegistry mapperRegistry;

    public DefaultSqlSessionFactory(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(mapperRegistry);
    }
}
