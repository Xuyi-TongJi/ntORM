package edu.seu.ntorm.executor.defaults.factory;

import edu.seu.ntorm.executor.Executor;
import edu.seu.ntorm.executor.defaults.DefaultExecutor;
import edu.seu.ntorm.executor.factory.ExecutorFactory;
import edu.seu.ntorm.transaction.Transaction;

public class DefaultExecutorFactory implements ExecutorFactory {

    @Override
    public Executor getExecutor(Transaction transaction) {
        return new DefaultExecutor(transaction);
    }
}
