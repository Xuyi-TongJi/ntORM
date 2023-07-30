package edu.seu.ntorm.binding.method;

import lombok.Data;

/**
 * 将调用的方法参数即TypeHandler需要的信息映射到MethodParams
 */
@Data
public class MethodParams {

    private String paramName;

    private Object params;

    private Class<?> clazz;
}
