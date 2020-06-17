package fun.gengzi.codecopy.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * <h1>CacheConfig 缓存配置类</h1>
 * <p>本地缓存-caffeine</p>
 * <p>分布式缓存-reids</p>
 * <p>
 * <p>
 * redis缓存与 caffeine 本地缓存，组成多级缓存。
 * 在微服务项目中，很多数据会被存放到 分布式缓存中，为了降低分布式缓存的读压力，会将某些缓存数据，缓存在本地
 * 先从本地缓存中读取，如果读不到，去分布式缓存中读取，还读不到，查询真正的数据，并填充到 本地缓存和分布式缓存
 *
 * @author gengzi
 * @date 2020年6月10日15:16:36
 */
@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    private Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    /**
     * caffeineCacheManager 本地缓存
     * 根据 CacheConfigEnum 来初始化缓存项
     * <p>
     * 注意点：
     *
     * @return
     * @Primary 标识默认实现
     * 因为在 CacheConfig 中，两个方法都返回了 CacheManager 类型，需要标识一下那个是默认实现，防止
     */
    @Primary
    @Bean("localhostCacheManager")
    public CacheManager caffeineCacheManager() {
        logger.info("初始化 -> [{}]", "CacheManger localhostCacheManager Start ----- caffeineCacheManager");
        final SimpleCacheManager caffeineCacheManager = new SimpleCacheManager();
        ArrayList<CaffeineCache> caffeineCaches = new ArrayList<>(CacheConfigEnum.values().length);
        // 根据 CacheConfigEnum 生成缓存项
        Arrays.stream(CacheConfigEnum.values()).forEach(
                cacheConfigEnum -> {
                    final Caffeine<Object, Object> caffeine = Caffeine.newBuilder();
                    final long ttl = cacheConfigEnum.getTtl();
                    final long maxSize = cacheConfigEnum.getMaxSize();
                    if (ttl != -1 && ttl > 0) {
                        // 设置过期时间
                        // 设置写入多久之后过期
                        caffeine.expireAfterWrite(cacheConfigEnum.getTtl(), cacheConfigEnum.getTimeUnit());
                    }
                    if (maxSize != -1) {
                        // 设置最大条数
                        caffeine.maximumSize(cacheConfigEnum.getMaxSize());
                    }
                    caffeineCaches.add(new CaffeineCache(cacheConfigEnum.name(), caffeine.build()
                    ));
                }
        );
        caffeineCacheManager.setCaches(caffeineCaches);
        return caffeineCacheManager;
    }


    /**
     * 配置 带Jackson2JsonRedisSerializer 序列化值的 redisTemplate
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean("initredisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // key 序列化
        RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
        // value 序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        // 解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    /**
     * redisCacheManager redis缓存
     * redis 缓存管理器
     * <p>
     * 注意点：
     * spring boot 默认存储的redis key 是：
     * 注解中 value::key 组成的
     * 例如：DATADIC_GLOBAL_MENU::result_pid_310512
     * <p>
     * Spring boot 默认存储的redis value 是：
     * 由JdkSerializationRedisSerializer 序列化的，在redis 客户端程序中，会出现 各种\x0\x0\x00\x000\x0\x0 类似的
     * 所以需要使用 Jackson2JsonRedisSerializer 进行转换，方便查看
     *
     * @param redisConnectionFactory redis连接工厂，spring boot 默认注入
     * @return {@link RedisCacheManager}
     */
    @Bean("localhostRedisCacheManager")
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        logger.info("初始化 -> [{}]", "CacheManger RedisCacheManger Start ----- serialCacheManger");
        // key 序列化
        RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
        // value 序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        // 解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // Redis 缓存默认配置
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()   // 读取默认的缓存配置
                .entryTtl(Duration.ofSeconds(500)) // 过期时间
                // .disableCachingNullValues()  // 不缓存空值
                // .disableKeyPrefix()  // 禁用前缀
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer))  // 序列化key
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));  // 序列化value

        // 构建redis 的缓存管理器
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration) // 缓存配置
                .build();
        return redisCacheManager;
    }


    /**
     * 自定义redis缓存管理器
     * <p>
     * 使用 CacheRedisConfigEnum 来配置多个 redis 缓存
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean("loclRedisCacheManagers")
    public CacheManager loclRedisCacheManagers(RedisConnectionFactory redisConnectionFactory) {
        logger.info("初始化 -> [{}]", "CacheManger RedisCacheManger Start ----- loclRedisCacheManagers");
        // key 序列化
        RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
        // value 序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        // 解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // Redis 缓存默认配置  默认配置的config defaultCacheConfig 使用这个方法创建
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()   // 读取默认的缓存配置
                //.entryTtl(Duration.ofSeconds(20)) // 过期时间
                // .disableCachingNullValues()  // 不缓存空值
                // .disableKeyPrefix()  // 禁用前缀
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer))  // 序列化key
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));  // 序列化value

        // Redis 缓存自定义配置
        HashMap<String, RedisCacheConfiguration> redisCacheConfigurationHashMap = new HashMap<>(CacheRedisConfigEnum.values().length);
        Arrays.stream(CacheRedisConfigEnum.values()).forEach(cacheRedisConfigEnum -> {
            RedisCacheConfiguration redisCacheConfiguration2 = RedisCacheConfiguration
                    .defaultCacheConfig()
                    .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer))  // 序列化key
                    .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));  // 序列化value

            long ttl = cacheRedisConfigEnum.getTtl();
            ChronoUnit chronoUnit = cacheRedisConfigEnum.getChronoUnit();
            boolean cacheNullValues = cacheRedisConfigEnum.isCacheNullValues();
            boolean useKeyPrefix = cacheRedisConfigEnum.isUseKeyPrefix();
            // 特别注意：每次设置config，在源码中都重新创建了一个 config，所以要把 对象重新拿到。
            // 如果不拿新创建的对象，你的 config 都只是第一次创建的，也就是不带额外配置，默认配置的config
            if (ttl != -1 && ttl > 0) {
                // 设置过期时间
                redisCacheConfiguration2 = redisCacheConfiguration2.entryTtl(Duration.of(ttl, chronoUnit));
            }
            if (!cacheNullValues) {
                // 不缓存空值
                redisCacheConfiguration2 = redisCacheConfiguration2.disableCachingNullValues();
            }
            if (!useKeyPrefix) {
                // 禁用前缀
                redisCacheConfiguration2 = redisCacheConfiguration2.disableKeyPrefix();
            }
            // 追加到Map 中
            redisCacheConfigurationHashMap.put(cacheRedisConfigEnum.name(), redisCacheConfiguration2);

        });
        // redis 的写入策略
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.lockingRedisCacheWriter(redisConnectionFactory);
        // 创建RedisCacheManager
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter, redisCacheConfiguration, redisCacheConfigurationHashMap);
        // 初始化这些caches
        redisCacheManager.initializeCaches();
        return redisCacheManager;
    }

}
