package edu.seu.ntorm.executor.defaults;

import edu.seu.ntorm.exception.ExecutorException;
import edu.seu.ntorm.executor.BaseExecutor;
import edu.seu.ntorm.mapping.BoundSql;
import edu.seu.ntorm.mapping.MappedStatement;
import edu.seu.ntorm.session.Configuration;
import edu.seu.ntorm.transaction.Transaction;

import java.util.List;

public class DefaultExecutor extends BaseExecutor {

    public DefaultExecutor(Configuration configuration, Transaction transaction) {
        super(configuration, transaction);
    }

    @Override
    protected <E> List<E> doQuery(MappedStatement ms, Object parameter, BoundSql boundSql) {
        try {
            Configuration config = ms.getConfiguration();

        } catch (Exception e) {
            throw new ExecutorException();
        }

        return null;
    }
}
