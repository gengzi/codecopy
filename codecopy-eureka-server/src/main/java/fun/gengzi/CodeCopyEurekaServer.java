package fun.gengzi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class CodeCopyEurekaServer {
    public static void main(String[] args) {
        SpringApplication.run(CodeCopyEurekaServer.class, args);
    }
}
