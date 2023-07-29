package edu.seu.ntorm.session;

import edu.seu.ntorm.transaction.TransactionIsolationLevel;

import java.sql.SQLException;

public interface SqlSessionFactory {

    /**
     * 工厂方法：打开一个SqlSession
     * @return SqlSession
     */
    SqlSession openSession(TransactionIsolationLevel level, boolean autoCommitted) throws SQLException;
}
