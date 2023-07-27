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

    /**
     * 原始SQL
     */
    private String sql;

    /**
     *
     */
    private Map<Integer, String> parameterMappings;

    /**
     * 参数类型 可以是Map, 可以是实体类等等
     */
    private String parameterType;

    /**
     * 结果实体类型，可以是Map，也可以是实体类(DTO)
     */
    private String resultType;
}
