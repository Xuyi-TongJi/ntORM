package edu.seu.ntorm.session;

import java.io.Reader;

/**
 * SqlSessionFactory(工厂)构造器 -> 构造SqlSessionFactory
 * 构建者模式
 */
public interface SqlSessionFactoryBuilder {

    /**
     * 通过字符流构造SqlSessionFactory i.e: XML Mapper
     * @param reader 字符流
     * @return SqlSessionFactory -> 用以构造SqlSession
     */
    SqlSessionFactory build(Reader reader);

    /**
     * 通过Configuration直接构造
     * @param configuration 配置
     * @return SqlSessionFactory -> 用以构造SqlSession
     */
    SqlSessionFactory build(Configuration configuration);
}
