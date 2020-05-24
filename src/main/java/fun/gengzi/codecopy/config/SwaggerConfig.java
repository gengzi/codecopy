package fun.gengzi.codecopy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/** 
 * <h1>Swagger接口配置</h1>
 *
 *  knife4j 增强了swagger 的页面效果
 *  https://doc.xiaominfo.com/  可以考虑使用
 *
 *  本地的接口文档地址
 *  http://localhost:port/swagger-ui.html
 *
 *  文档：
 *  https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X#quick-annotation-overview
 *
 * @author gengzi
 * @date 2020年5月23日19:12:22
 *
 */
@Configuration
@EnableSwagger2
//@EnableKnife4j
// 生效的配置文件
@Profile({"dev","test"})
public class SwaggerConfig{
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 指定 controller包路径
                .apis(RequestHandlerSelectors.basePackage(ConfigConsts.SWAGGER_CONTROLLER_PACKAGE))
                // 指定扫描 @RestController 注解
//                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(ConfigConsts.APP_NAME_TITLE)
                .description(ConfigConsts.APP_EXPLANATION)
                .version(ConfigConsts.APP_VERSION)
                .build();
    }

}