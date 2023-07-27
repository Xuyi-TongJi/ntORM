package edu.seu.ntorm.executor;

import edu.seu.ntorm.exception.ExecutorException;
import edu.seu.ntorm.mapping.BoundSql;
import edu.seu.ntorm.mapping.MappedStatement;
import edu.seu.ntorm.session.Configuration;
import edu.seu.ntorm.transaction.Transaction;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public abstract class BaseExecutor implements Executor {

    /**
     * ORM配置(Mapper + Environment + MappedStatements)
     */
    protected Configuration configuration;

    protected Transaction transaction;

    /**
     * 包装类
     */
    protected Executor wrapper;

    private boolean closed = false;

    public BaseExecutor(Configuration configuration, Transaction transaction) {
        this.configuration = configuration;
        this.transaction = transaction;
        this.wrapper = this;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, BoundSql boundSql) {
        closeOperation();
        return doQuery(ms, parameter, boundSql);
    }

    @Override
    public Transaction getTransaction() {
        closeOperation();
        return transaction;
    }

    @Override
    public void commit(boolean required) throws SQLException {
        if (required) {
            transaction.commit();
        }
    }

    @Override
    public void rollback(boolean required) throws SQLException {
        if (required) {
            transaction.rollback();
        }
    }

    @Override
    public void close(boolean forceRollback) {
        try {
            try {
                rollback(forceRollback);
            } finally {
                transaction.close();
            }
        } catch (Exception e) {
            throw new ExecutorException();
        } finally {
            transaction = null;
            closed = true;
        }
    }

    protected abstract <E> List<E> doQuery(MappedStatement ms, Object parameter, BoundSql boundSql);

    /**
     * 判断该执行器是否已经关闭
     */
    private void closeOperation() {
        if (closed || transaction == null) {
            throw new ExecutorException();
        }
    }
}
