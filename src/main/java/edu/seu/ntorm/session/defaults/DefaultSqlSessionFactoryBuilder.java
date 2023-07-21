package edu.seu.ntorm.session.defaults;

import edu.seu.ntorm.builder.xml.DefaultXmlConfigBuilder;
import edu.seu.ntorm.exception.ParseConfigurationException;
import edu.seu.ntorm.session.AbstractSqlSessionFactoryBuilder;
import edu.seu.ntorm.session.Configuration;
import edu.seu.ntorm.session.SqlSessionFactory;

import java.io.Reader;

/**
 * 默认的SqlSessionFactoryBuilder
 * 默认为读取XML配置文件
 */
public class DefaultSqlSessionFactoryBuilder extends AbstractSqlSessionFactoryBuilder {

    /**
     * Read XML Configuration
     * @param reader 字符流
     * @return
     */
    @Override
    public SqlSessionFactory build(Reader reader) {
        try {
            Configuration configuration = new DefaultXmlConfigBuilder(reader).parse();
            return new DefaultSqlSessionFactory(configuration);
        } catch (ParseConfigurationException e) {
            throw new ParseConfigurationException();
        }
    }
}
