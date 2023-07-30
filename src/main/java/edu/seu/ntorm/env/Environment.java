package edu.seu.ntorm.env;

import edu.seu.ntorm.dataSource.DataSourceFactory;
import edu.seu.ntorm.transaction.TransactionFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 执行环境
 * 包括事务工厂(getTransaction)以及数据源工厂(getDataSource)
 * TODO 可以不可以删除这个类，直接注入TransactionFactory 和 DataSourceFactory
 * TODO 可不可以在配置类里直接@Bean这个类来配置TransactionFactory和DataSourceFactory
 */
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
