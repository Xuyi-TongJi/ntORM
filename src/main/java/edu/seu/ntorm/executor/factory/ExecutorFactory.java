package edu.seu.ntorm.executor.factory;

import edu.seu.ntorm.executor.Executor;
import edu.seu.ntorm.transaction.Transaction;

public interface ExecutorFactory {

    Executor getExecutor(Transaction transaction);
}
