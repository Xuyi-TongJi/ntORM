package edu.seu.ntorm.ntDb;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * SQL执行配置
 */
@Component
@Data
public class SqlStatementConfig {

    /**
     * 查询超时时间
     */
    @Value("query_time_out")
    private int queryTimeout = 350;
}
