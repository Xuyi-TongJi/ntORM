package edu.seu.ntorm.session.defaults;

import edu.seu.ntorm.exception.SqlSessionException;
import edu.seu.ntorm.executor.Executor;
import edu.seu.ntorm.env.Configuration;
import edu.seu.ntorm.session.SqlSession;
import edu.seu.ntorm.session.SqlSessionFactory;
import edu.seu.ntorm.env.Environment;
import edu.seu.ntorm.transaction.Transaction;
import edu.seu.ntorm.transaction.TransactionIsolationLevel;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    /**
     * Configuration -> MapperRegistry + TypeAlias + StatementMapper
     */
    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession(TransactionIsolationLevel level, boolean autoCommitted) {
        // 打开SqlSession的方法: 开启一个事务->创建一个执行器->创建一个SqlSession
        Environment env = configuration.getEnvironment();
        DataSource ds = env.getDataSourceFactory().getDataSource();
        Transaction tx = env.getTransactionFactory().newTransaction(ds, level, autoCommitted);
        try {
            Executor executor = configuration.buildExecutor(tx);
            return new DefaultSqlSession(configuration, executor);
        } catch (Exception e) {
            throw new SqlSessionException();
        } finally {
            try {
                tx.close();
            } catch (SQLException e) {
                throw new SqlSessionException();
            }
        }
    }
}
