package fun.gengzi.codecopy.business.starter;

import fun.gengzi.codecopy.business.luckdraw.controller.LuckdrawController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.HashMap;

/**
 *  最先被加载
 *
 *  设置环境变量
 */
public class EnvironmentPostProcessorExample implements EnvironmentPostProcessor {

    private Logger logger = LoggerFactory.getLogger(EnvironmentPostProcessorExample.class);

    private final YamlPropertySourceLoader loader = new YamlPropertySourceLoader();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        logger.info("加载默认配置开始");

        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name","hello");

        MapPropertySource propertySource = new MapPropertySource("key", objectObjectHashMap);
        environment.getPropertySources().addLast(propertySource);

        logger.info("加载默认配置完毕");
    }

    private PropertySource<?> loadYaml(Resource path) {
        if (!path.exists()) {
            throw new IllegalArgumentException("Resource " + path + " does not exist");
        }
        try {
            return this.loader.load("custom-resource", path).get(0);
        }
        catch (IOException ex) {
            throw new IllegalStateException("Failed to load yaml configuration from " + path, ex);
        }
    }

}