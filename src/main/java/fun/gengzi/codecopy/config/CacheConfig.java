package fun.gengzi.codecopy.config;


import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * <h1>CacheConfig 缓存配置类</h1>
 * <p>缓存配置-缓存管理器</p>
 *
 * @author gengzi
 * @date 2020年6月10日15:16:36
 */
@Configuration
public class CacheConfig {

    @Bean("localhostCacheManager")
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager caffeineCacheManager = new SimpleCacheManager();
        ArrayList<CaffeineCache> caffeineCaches = new ArrayList<>();
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                //cache的初始容量值
                .initialCapacity(100)
                // maximumSize用来控制cache的最大缓存数量
                // maximumSize和maximumWeight不可以同时使用
                .maximumSize(1000)
                // 超时时间
                .expireAfterWrite(4, TimeUnit.HOURS);
        // 设置缓存名称
        CaffeineCache caffeineCache = new CaffeineCache("mycache", caffeine.build());
        caffeineCaches.add(caffeineCache);
        caffeineCacheManager.setCaches(caffeineCaches);
        return caffeineCacheManager;
    }


}
