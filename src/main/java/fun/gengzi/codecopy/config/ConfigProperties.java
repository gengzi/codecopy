package fun.gengzi.codecopy.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;


/**
 * 多个 yml 配置加载类
 * 参考：https://www.cnblogs.com/ligh-test/p/10752072.html
 *
 * @author gengzi
 * @date 2020年6月29日17:44:50
 */
//@Configuration
public class ConfigProperties {

    private static Logger logger = LoggerFactory.getLogger(ConfigProperties.class);

    @Value("${yml-include.names}")
    private static String names;

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        logger.info("-- loading yml start --");
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("redis.yml"));
        configurer.setProperties(yaml.getObject());
        return configurer;
    }

}