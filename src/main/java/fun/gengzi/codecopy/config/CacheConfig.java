package fun.gengzi.codecopy.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
public class CacheConfig {

    /**
     * 根据CacheConfigEnum 来初始化缓存项
     *
     * @return
     */
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
