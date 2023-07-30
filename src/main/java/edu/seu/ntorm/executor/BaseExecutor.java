package edu.seu.ntorm.executor;

import edu.seu.ntorm.exception.ExecutorException;
import edu.seu.ntorm.mapping.BoundSql;
import edu.seu.ntorm.mapping.MappedStatement;
import edu.seu.ntorm.transaction.Transaction;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *  外观模式 Facade
 */
@Slf4j
public abstract class BaseExecutor implements Executor {

    /**
     * 当前事务 (java.sql.Connection包装类)
     */
    protected Transaction transaction;

    /**
     * 包装类 TODO ?
     */
    protected Executor wrapper;

    private boolean closed = false;

    public BaseExecutor(Transaction transaction) {
        this.transaction = transaction;
        this.wrapper = this;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Map<String, String> parameter, BoundSql boundSql) {
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

    protected abstract <E> List<E> doQuery(MappedStatement ms, Map<String, String> parameter, BoundSql boundSql);

    /**
     * 判断该执行器是否已经关闭
     */
    private void closeOperation() {
        if (closed || transaction == null) {
            throw new ExecutorException();
        }
    }
}
