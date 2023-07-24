package edu.seu.ntorm.mapping;

import edu.seu.ntorm.dataSource.DataSourceFactory;
import edu.seu.ntorm.transaction.TransactionFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class Environment {

    @Value("ntORM.id")
    private String id;

    @Autowired
    private TransactionFactory transactionFactory;

    @Autowired
    private DataSourceFactory dataSourceFactory;
}
