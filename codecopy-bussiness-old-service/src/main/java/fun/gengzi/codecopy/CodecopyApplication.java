package fun.gengzi.codecopy;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class, JtaAutoConfiguration.class})
// 启用缓存机制
@EnableCaching
@EnableAsync
@ServletComponentScan
public class CodecopyApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        // 解决es的netty冲突问题
        System.setProperty("es.set.netty.runtime.available.processors", "false");

        //启用非web模式，但是没看出来啥用
        //SpringApplication springApplication = new SpringApplication();
        //springApplication.setWebApplicationType(WebApplicationType.NONE);
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

    // 支持web 工程
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CodecopyApplication.class);
    }
}
