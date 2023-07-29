package edu.seu.ntorm.session;

import edu.seu.ntorm.exception.MapperNotExistException;

import java.util.List;

public interface SqlSession {

    /**
     * 支持参数传递的获取一条记录(Select)的接口
     * @param statementId SqlID -> Mapper方法ID
     * @param parameters 参数 POJO or Map
     * @return 查询结果 DTO or Map
     */
    <T> T selectOne(String statementId, Object parameters);

    /**
     *
     * @param statementId sqlID -> Mapper方法ID
     * @param parameters 参数 POJO or Map
     * @return 查询结果 DTO or Map
     */
    <T> List<T> select(String statementId, Object parameters);

    /**
     * 得到映射器 -> (通过MapperRegistry得到映射器的代理类)
     * @param type Mapper.class
     * @return 映射器的代理类
     */
    <T> T getMapper(Class<T> type) throws MapperNotExistException;
}
