package fun.gengzi.codecopy;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// 启用缓存机制
@EnableCaching
public class CodecopyApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodecopyApplication.class, args);
    }
}
