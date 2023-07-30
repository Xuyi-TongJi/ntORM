package edu.seu.ntorm.executor.typeHandler;

import java.util.Map;

public interface TypeHandler {



    /**
     * 解析参数
     */
    void resolveParams();


    /**
     * 获取（最终解析完成的ParamMap）
     * @return map
     */
    Map<String, String> getParamValueMap();
}
