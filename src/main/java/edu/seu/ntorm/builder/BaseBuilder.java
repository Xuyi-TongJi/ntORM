package edu.seu.ntorm.builder;

import edu.seu.ntorm.session.Configuration;

public class BaseBuilder {

    protected final Configuration configuration;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }
}
