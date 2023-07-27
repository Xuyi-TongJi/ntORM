package edu.seu.ntorm.mapping;

import edu.seu.ntorm.session.Configuration;
import edu.seu.ntorm.type.JdbcType;

/**
 * TODO 暂时不需要
 * 参数映射
 */
public class ParameterMapping {

    private Configuration configuration;

    private String property;

    private Class<?> javaType = Object.class;

    private JdbcType jdbcType;

}
