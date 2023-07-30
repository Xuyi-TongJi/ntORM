package edu.seu.ntorm.session;

import edu.seu.ntorm.binding.method.MethodParams;
import edu.seu.ntorm.exception.MapperNotExistException;
import edu.seu.ntorm.type.SqlCommandType;

import java.util.List;

public interface SqlSession {

    /**
     * 支持参数传递的获取一条记录(Select)的接口
     * @param statementId SqlID -> Mapper方法ID
     * @param parameters 参数 交给TypeHandler处理
     * @return 查询结果 DTO or Map
     */
    <T> T selectOne(String statementId, List<MethodParams> parameters);

    /**
     *
     * @param statementId sqlID -> Mapper方法ID
     * @param parameters 参数 交给TypeHandler处理
     * @return 查询结果 DTO or Map
     */
    <T> List<T> select(String statementId, List<MethodParams> parameters);

    /**
     * update
     * @param statementId sqlId -> Mapper方法ID
     * @param parameters 参数 交给TypeHandler处理
     * @return 改变的行数
     */
    Long update(String statementId, List<MethodParams> parameters);

    /**
     * 得到映射器 -> (通过MapperRegistry得到映射器的代理类)
     * @param type Mapper.class
     * @return 映射器的代理类
     */
    <T> T getMapper(Class<T> type) throws MapperNotExistException;

    SqlCommandType getSqlCommandType(String statementId);
}
