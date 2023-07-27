package edu.seu.ntorm.session.defaults;

import edu.seu.ntorm.builder.BaseBuilder;
import edu.seu.ntorm.exception.ParseConfigurationException;
import edu.seu.ntorm.ntDb.DefaultBuilderAutoConfigurator;
import edu.seu.ntorm.session.AbstractSqlSessionFactoryBuilder;
import edu.seu.ntorm.session.env.Configuration;
import edu.seu.ntorm.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.io.Reader;

/**
 * 默认的SqlSessionFactoryBuilder
 * 默认为读取XML配置文件
 */
@ConditionalOnBean(value = {DefaultBuilderAutoConfigurator.class})
@Component
public class DefaultSqlSessionFactoryBuilder extends AbstractSqlSessionFactoryBuilder {

    @Autowired
    private BaseBuilder baseBuilder;

    /**
     * Read XML Configuration
     * @param reader 字符流
     * @return SqlSessionFactory
     */
    @Override
    public SqlSessionFactory build(Reader reader) {
        try {
            baseBuilder.parseByReader(reader);
            Configuration configuration = baseBuilder.getConfiguration();
            return new DefaultSqlSessionFactory(configuration);
        } catch (ParseConfigurationException e) {
            throw new ParseConfigurationException();
        }
    }
}
