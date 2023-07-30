package edu.seu.ntorm.binding.method;

import com.alibaba.druid.util.StringUtils;
import edu.seu.ntorm.annotation.ParamName;
import io.netty.util.internal.StringUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Mapper方法参数解析器，支持@ParamName注解
 */
@ConditionalOnMissingBean(value = {MethodParamsBuilder.class})
@Component
public class DefaultMethodParamsBuilder implements MethodParamsBuilder {

    @Override
    public List<MethodParams> buildParams(Method method) {
        List<MethodParams> params = new ArrayList<>();
        if (method != null) {
            Parameter[] parameters = method.getParameters();
            for (Parameter p : parameters) {
                MethodParams mp = new MethodParams();
                ParamName annotation = p.getAnnotation(ParamName.class);
                if (annotation != null && StringUtils.isEmpty(annotation.name())) {
                    mp.setParamName(annotation.name());
                } else {
                    mp.setParamName(p.getName());
                }
                mp.setClazz(resolveClazz(p.getType()));
            }
        }
        return params;
    }

    private Class<?> resolveClazz(Class<?> clazz) {
        if (Map.class.isAssignableFrom(clazz)) {
            return Map.class;
        } else if (Date.class.isAssignableFrom(clazz)) {
            return Date.class;
        }
        return clazz;
    }
}
