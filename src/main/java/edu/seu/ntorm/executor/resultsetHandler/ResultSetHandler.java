package edu.seu.ntorm.executor.resultsetHandler;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface ResultSetHandler {

    /**
     * 将Statement中的ResultSet（查询结果）映射成List<E>
     * @param statement statement 表达一次数据库查询
     * @return 查询结果集
     */
    <E> List<E> handleQueryResultSets(Statement statement) throws SQLException;
}
