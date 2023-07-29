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
     * BoundSql和MappedStatement并不会记录真实的parameter值
     * 并不是实际的parameter, 只是parameter name 与 sql语句中 ? 次序的映射
     * 与？对应的参数名称 (i.e: 0->id 1->username)
     * select * from user where id = ? and username = ?
     */
    private Map<Integer, String> parameterMappings;

    /**
     * 参数类型 可以是Map, 可以是实体类等等
     */
    private String parameterType;

    /**
     * 结果实体类型，可以是Map，也可以是实体类(DTO), 对于update指令，还可以是Long
     */
    private String resultType;
}
