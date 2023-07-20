package edu.seu.ntorm.session;

import edu.seu.ntorm.exception.MapperNotExistException;

public interface SqlSession {

    /**
     * 查询一条记录的接口(根据SqlID)
     * @param statement SqlID
     * @return 查询结果，可能为null
     */
    <T> T selectOne(String statement);

    /**
     * 支持参数传递的获取一条记录(Select)的接口
     * @param statement SqlID
     * @param parameters 支持参数传递 POJO or Map
     * @return 查询结果，可能为null
     */
    <T> T selectOne(String statement, Object parameters);

    /**
     * 得到映射器 -> (通过MapperRegistry得到映射器的代理类)
     * @param type Mapper.class
     * @return 映射器的代理类
     */
    <T> T getMapper(Class<T> type) throws MapperNotExistException;
}
