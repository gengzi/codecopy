package fun.gengzi.codecopy.business.redis.config;

import fun.gengzi.codecopy.business.redis.entity.RedissondbConfigEntity;
import fun.gengzi.codecopy.exception.RrException;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * <h1>RedisTemplate 初始化类 </h1>
 *
 * @author gengzi
 * @date 2020年12月16日22:38:46
 */
@AutoConfigureBefore({RedisAutoConfiguration.class})  // 要在RedisAutoConfiguration 自动配置前执行
@Import(RedisRegister.class) // 配置该类前，先加载 RedisRegister 类
@Configuration // 配置类
// 实现 EnvironmentAware 用于获取全局环境
// 实现 ApplicationContextAware 用于获取Spring Context 上下文
public class RedisBeanInit implements EnvironmentAware, ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(RedisBeanInit.class);

    // 用于获取环境配置
    private Environment environment;
    // 用于绑定对象
    private Binder binder;
    // Spring context
    private ApplicationContext applicationContext;
    // 线程安全的hashmap
    private Map<String, RedisTemplate> redisTemplateMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
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


    @PostConstruct
    public void initAllRedisTemlate() {
        logger.info("<<<初始化系统的RedisTemlate开始>>>");
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
                Object bean = applicationContext.getBean("redisTemplate" + db);
                if (bean != null && bean instanceof RedisTemplate) {
                    redisTemplateMap.put("redisTemplate" + db, (RedisTemplate) bean);
                } else {
                    throw new RrException("初始化RedisTemplate" + db + "失败，请检查配置");
                }
            });
        }
        logger.info("已经装配的redistempleate，map:{}", redisTemplateMap);
        logger.info("<<<初始化系统的RedisTemlate完毕>>>");
    }

    @Bean
    public RedisManager getRedisManager() {
        return new RedisManager(redisTemplateMap);
    }
}
