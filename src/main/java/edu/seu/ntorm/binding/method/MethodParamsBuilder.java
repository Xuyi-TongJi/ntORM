package edu.seu.ntorm.binding.method;

import java.lang.reflect.Method;
import java.util.List;

public interface MethodParamsBuilder {

    /**
     * Method的参数build成MethodParams列表
     * @param method method对象
     * @return MethodParams列表
     */
    List<MethodParams> buildParams(Method method);
}
