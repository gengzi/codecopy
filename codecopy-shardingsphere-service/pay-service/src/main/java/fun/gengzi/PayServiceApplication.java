package fun.gengzi;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 *  <h1>支付微服务</h1>
 *
 * @author gengzi
 * @date 2021年12月1日23:10:00
 */
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class, JtaAutoConfiguration.class})
// 忽略自动配置的类，排除 数据源自动配置，数据量连接池的自动配置
@EnableEurekaClient
@SpringBootApplication
public class PayServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(PayServiceApplication.class, args);
    }
}