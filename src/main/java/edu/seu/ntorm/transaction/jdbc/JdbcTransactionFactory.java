package edu.seu.ntorm.transaction.jdbc;

import edu.seu.ntorm.ntDb.DefaultBuilderAutoConfigurator;
import edu.seu.ntorm.transaction.Transaction;
import edu.seu.ntorm.transaction.TransactionFactory;
import edu.seu.ntorm.transaction.TransactionIsolationLevel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@ConditionalOnBean(value = {DefaultBuilderAutoConfigurator.class})
@Component
public class JdbcTransactionFactory implements TransactionFactory {

    @Override
    public Transaction newTransaction(Connection conn) {
        return new JdbcTransaction(conn);
    }

    @Override
    public Transaction newTransaction(DataSource ds, TransactionIsolationLevel level, boolean autoCommitted) {
        return new JdbcTransaction(ds, level, autoCommitted);
    }
}
