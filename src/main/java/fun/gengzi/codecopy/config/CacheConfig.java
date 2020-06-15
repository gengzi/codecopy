package fun.gengzi.codecopy.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * <h1>CacheConfig 缓存配置类</h1>
 * <p>缓存配置-缓存管理器</p>
 * <p>
 * 分为本地缓存，分布式缓存reids
 *
 * @author gengzi
 * @date 2020年6月10日15:16:36
 */
@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    private Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    /**
     * 根据CacheConfigEnum 来初始化缓存项
     *
     * @return
     */
    @Primary
    @Bean("localhostCacheManager")
    public CacheManager caffeineCacheManager() {
        final SimpleCacheManager caffeineCacheManager = new SimpleCacheManager();
        ArrayList<CaffeineCache> caffeineCaches = new ArrayList<>(CacheConfigEnum.values().length);
        // 根据 CacheConfigEnum 生成缓存项
        Arrays.stream(CacheConfigEnum.values()).forEach(
                cacheConfigEnum -> {
                    final Caffeine<Object, Object> caffeine = Caffeine.newBuilder();
                    final long ttl = cacheConfigEnum.getTtl();
                    final long maxSize = cacheConfigEnum.getMaxSize();
                    if (ttl != -1) {
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


//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory = null;

    /**
     * redis 缓存管理器
     * 与 caffeine 本地缓存，组成多级缓存。
     * 在微服务项目中，很多数据会被存放到 分布式缓存中，为了降低分布式缓存的读压力，会将某些缓存数据，缓存在本地
     * 先从本地缓存中读取，如果读不到，去分布式缓存中读取，还读不到，查询真正的数据，并填充到 本地缓存和分布式缓存
     * <p>
     * <p>
     * 默认的redis 存储的key
     * DATADIC_GLOBAL_MENU::result_pid_310512
     * 是注解中 value::key 组成的
     * <p>
     * 如果获取结果不进行转换，会报一下错误
     * Caused by: java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to fun.gengzi.codecopy.sys.entity.SysPermission
     *
     * @return
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
                .entryTtl(Duration.ofHours(10)) // 过期时间
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer))  // 序列化key
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))  // 序列化value
                .disableCachingNullValues();  // 不缓存空值


        // 构建redis 的缓存管理器
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration) // 缓存配置
                .build();
        return redisCacheManager;
    }


//    @Bean("initredisTemplate")
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        // key 序列化
//        RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
//        // value 序列化
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//
//        // 解决查询缓存转换异常的问题
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        redisTemplate.setKeySerializer(stringRedisSerializer);
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setHashKeySerializer(stringRedisSerializer);
//        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }


    // 将缓存设置为不过期缓存数据 和 过期缓存数据
//
//    @Bean("localhostCacheManager")
//    public CacheManager caffeineCacheManager() {
//        SimpleCacheManager caffeineCacheManager = new SimpleCacheManager();
//        ArrayList<CaffeineCache> caffeineCaches = new ArrayList<>();
//        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
//                //cache的初始容量值
//                .initialCapacity(100)
//                // maximumSize用来控制cache的最大缓存数量
//                // maximumSize和maximumWeight不可以同时使用
//                .maximumSize(1000)
//                // 超时时间
//                .expireAfterWrite(4, TimeUnit.HOURS);
//        // 设置缓存名称
//        CaffeineCache caffeineCache = new CaffeineCache("mycache", caffeine.build());
//        caffeineCaches.add(caffeineCache);
//        caffeineCacheManager.setCaches(caffeineCaches);
//        return caffeineCacheManager;
//    }

//         Cache<Object, Object> build = Caffeine.newBuilder()
//          .expireAfterWrite(cacheConfigEnum.getTtl(), cacheConfigEnum.getTimeUnit())
//          .maximumSize(cacheConfigEnum.getMaxSize())
//          .build();

}
