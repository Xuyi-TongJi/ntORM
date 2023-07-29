package edu.seu.ntorm.builder;

import edu.seu.ntorm.exception.ParseConfigurationException;
import edu.seu.ntorm.session.env.Configuration;

import java.io.Reader;

public abstract class BaseBuilder {

    protected Configuration configuration;

    public Configuration getConfiguration() {
        return this.configuration;
    }

    /**
     * 通过字符流解析Configuration
     * @param reader 字符流，i.e: XML
     * @return Configuration
     */
    public abstract void parseByReader(Reader reader) throws ParseConfigurationException;

}
