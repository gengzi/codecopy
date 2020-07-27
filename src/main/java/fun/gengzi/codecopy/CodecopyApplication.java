package fun.gengzi.codecopy;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class, JtaAutoConfiguration.class})
// 启用缓存机制
@EnableCaching
public class CodecopyApplication {
    public static void main(String[] args) {
        // 解决es的netty冲突问题
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(CodecopyApplication.class, args);
    }

    /**
     * 网络请求
     *
     * @return
     */
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
