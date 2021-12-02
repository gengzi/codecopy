package fun.gengzi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

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
//@EnableTransactionManagement
@Slf4j
public class TransactionConfiguration {

//    /**
//     * 初始化事务管理器
//     *
//     * @param dataSource 数据源
//     * @return 事务管理器
//     */
//    @Bean
//    public PlatformTransactionManager txManager(final DataSource dataSource) {
//        log.info("初始化txManager");
//        return new DataSourceTransactionManager(dataSource);
//    }

//    @Bean
//    public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Transactional
//    @ShardingSphereTransactionType(TransactionType.XA)  // 支持TransactionType.LOCAL, TransactionType.XA, TransactionType.BASE
//    public void insert() {
//        jdbcTemplate.execute("INSERT INTO t_order (user_id, status) VALUES (?, ?)", (PreparedStatementCallback<Object>) ps -> {
//            ps.setObject(1, "1");
//            ps.setObject(2, "init");
//            ps.executeUpdate();
//            return ps;
//        });
//    }
}