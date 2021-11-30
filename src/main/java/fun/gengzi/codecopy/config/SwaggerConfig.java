package fun.gengzi.codecopy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

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

//        //=====添加head参数start============================
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<Parameter>();
//        tokenPar.name("Authorization").description("AccessToken令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//        pars.add(tokenPar.build());
//        // =========添加head参数end===================

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 指定 controller包路径
                .apis(RequestHandlerSelectors.basePackage(ConfigConsts.SWAGGER_CONTROLLER_PACKAGE))
                // 指定扫描 @RestController 注解
//                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
//                .globalOperationParameters(pars);
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(ConfigConsts.APP_NAME_TITLE)
                .description(ConfigConsts.APP_EXPLANATION)
                .version(ConfigConsts.APP_VERSION)
                .build();
    }

}