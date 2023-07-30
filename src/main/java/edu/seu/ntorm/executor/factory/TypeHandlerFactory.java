package edu.seu.ntorm.executor.factory;

import edu.seu.ntorm.executor.typeHandler.TypeHandler;

public interface TypeHandlerFactory {

    /**
     * 获取参数解析器
     * @return 参数解析器
     */
    TypeHandler getTypeHandler();
}
