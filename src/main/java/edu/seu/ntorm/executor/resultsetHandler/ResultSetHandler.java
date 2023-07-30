package edu.seu.ntorm.executor.resultsetHandler;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface ResultSetHandler {

    /**
     * 将Statement中的ResultSet（查询结果）映射成List<E>
     * @param statement statement 表达一次JDBC层数据库查询
     * @return 查询结果集
     */
    <E> List<E> handleQueryResultSets(Statement statement) throws SQLException;

    /**
     * 将Statement中的更新操作结果映射成Long
     * @param statement statement
     * @return Long 表示影响的行数
     */
    Long handleUpdateResultSets(Statement statement) throws SQLException;
}
