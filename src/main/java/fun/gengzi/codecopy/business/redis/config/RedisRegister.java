package fun.gengzi.codecopy.business.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import fun.gengzi.codecopy.business.redis.controller.RedisShowController;
import fun.gengzi.codecopy.business.redis.entity.RedissondbConfigEntity;
import fun.gengzi.codecopy.exception.RrException;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * <h1>redistemplate初始化</h1>
 * <p>
 * 作用：
 * <p>
 * 读取系统配置，系统启动时，读取redis 的配置，初始化所有的redistemplate
 * 并动态注册为bean
 *
 * @author gengzi
 * @date 2021年1月5日22:16:29
 */
@Configuration
// 实现 EnvironmentAware 用于获取环境配置
// 实现 ImportBeanDefinitionRegistrar 用于动态注册bean
public class RedisRegister implements EnvironmentAware, ImportBeanDefinitionRegistrar {

    private Logger logger = LoggerFactory.getLogger(RedisRegister.class);

    // 用于获取环境配置
    private Environment environment;
    // 用于绑定对象
    private Binder binder;

    /**
     * 设置环境
     *
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.binder = Binder.get(environment);
    }

    /**
     * 注册bean
     *
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        logger.info("《《《动态注册bean开始》》》");
        RedissondbConfigEntity redissondb;
        try {
            redissondb = binder.bind("redissondb", RedissondbConfigEntity.class).get();
        } catch (Exception e) {
            logger.error("读取redissondb环境配置失败", e);
            return;
        }
        List<Integer> databases = redissondb.getDatabases();
        if (CollectionUtils.isNotEmpty(databases)) {
            databases.forEach(db -> {
                // 单机模式，集群只能使用db0
                Config config = new Config();
                config.setCodec(StringCodec.INSTANCE);
                SingleServerConfig singleConfig = config.useSingleServer();
                singleConfig.setAddress(redissondb.getAddress());
                singleConfig.setPassword(redissondb.getPassword());
                singleConfig.setDatabase(db);
                RedissonClient redissonClient = Redisson.create(config);
                // 构造RedissonConnectionFactory
                RedissonConnectionFactory redisConnectionFactory = new RedissonConnectionFactory(redissonClient);
                // bean定义
                GenericBeanDefinition redisTemplate = new GenericBeanDefinition();
                // 设置bean 的类型
                redisTemplate.setBeanClass(RedisTemplate.class);
                // 设置自动注入的形式，根据名称
                redisTemplate.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_NAME);
                // redisTemplate 的属性配置
                redisTemplate(redisTemplate, redisConnectionFactory);
                // 注册Bean
                registry.registerBeanDefinition("redisTemplate" + db, redisTemplate);
            });
        }
        logger.info("《《《动态注册bean结束》》》");

    }

    /**
     * redisTemplate 的属性配置
     *
     * @param redisTemplate          泛型bean
     * @param redisConnectionFactory 连接工厂
     * @return
     */
    public GenericBeanDefinition redisTemplate(GenericBeanDefinition redisTemplate, RedisConnectionFactory redisConnectionFactory) {
        RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        // 解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // key采用String的序列化方式，value采用json序列化方式
        // 通过方法设置属性值
        redisTemplate.getPropertyValues().add("connectionFactory", redisConnectionFactory);
        redisTemplate.getPropertyValues().add("keySerializer", stringRedisSerializer);
        redisTemplate.getPropertyValues().add("hashKeySerializer", stringRedisSerializer);
        redisTemplate.getPropertyValues().add("valueSerializer", jackson2JsonRedisSerializer);
        redisTemplate.getPropertyValues().add("hashValueSerializer", jackson2JsonRedisSerializer);
        return redisTemplate;
    }


}
