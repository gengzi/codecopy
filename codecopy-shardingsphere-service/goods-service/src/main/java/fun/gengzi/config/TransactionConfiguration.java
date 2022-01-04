package fun.gengzi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * <h1>分布式事务配置</h1>
 *
 * @author gengzi
 * @date 2021年12月1日16:50:44
 */
// 声明配置类
@Configuration
// 启用事务管理器
@EnableTransactionManagement
@Slf4j
public class TransactionConfiguration {

    /**
     * 初始化事务管理器
     *
     * @param dataSource 数据源
     * @return 事务管理器
     */
    @Bean("transactionManager")
    public PlatformTransactionManager txManager(final DataSource dataSource) {
        log.info("初始化txManager");
        return new DataSourceTransactionManager(dataSource);
    }
}