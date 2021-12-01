package fun.gengzi;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import fun.gengzi.config.TransactionConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;


/**
 * <H1>Mysql分库分表实践</H1>
 *
 * @author gengzi
 * @date 2021年12月1日16:48:47
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class, JtaAutoConfiguration.class})
// 忽略自动配置的类，排除 数据源自动配置，数据量连接池的自动配置
@EnableEurekaClient
// 导入分布式事务配置
@Import(TransactionConfiguration.class)
public class CodecopyShardingsphereService {
    public static void main(String[] args) {
        SpringApplication.run(CodecopyShardingsphereService.class, args);
    }
}
