package edu.seu.ntorm.dataSource.druid;

import com.alibaba.druid.pool.DruidDataSource;
import edu.seu.ntorm.dataSource.DataSourceFactory;
import edu.seu.ntorm.ntDb.DefaultBuilderAutoConfigurator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

@ConditionalOnMissingBean(value = {DataSourceFactory.class})
@ConditionalOnBean(value = {DefaultBuilderAutoConfigurator.class})
@Configuration
@Deprecated
public class DruidDataSourceFactory implements DataSourceFactory {

    private Properties props;

    @Override
    public void setProperties(Properties properties) {
        this.props = properties;
    }

    @Override
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(props.getProperty("driver"));
        dataSource.setUrl(props.getProperty("url"));
        dataSource.setUsername(props.getProperty("username"));
        dataSource.setPassword(props.getProperty("password"));
        return dataSource;
    }
}
