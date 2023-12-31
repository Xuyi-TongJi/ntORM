package edu.seu.ntorm.session;

import edu.seu.ntorm.env.Configuration;

import java.io.Reader;

/**
 * SqlSessionFactory(工厂)构造器 -> 构造SqlSessionFactory
 * 整个ORM框架的入口
 * 构建者模式，构建SqlSessionFactory -> 基于XML等构建方式
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
