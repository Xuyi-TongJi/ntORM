package edu.seu.ntorm.mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * SQL包装类，实质上就是一个简化的MappedStatement，可由MappedStatement构造
 *
 */
@Getter
@AllArgsConstructor
public class BoundSql {

    private String sql;
    private Map<Integer, String> parameterMappings;
    private String parameterType;
    private String resultType;
}
