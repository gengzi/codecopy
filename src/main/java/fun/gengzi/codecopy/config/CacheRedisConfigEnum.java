package fun.gengzi.codecopy.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * <h1>Redis缓存配置枚举类</h1>
 * 该类配置系统的 缓存策略  缓存名称，过期时间，时间单位，是否允许保存空值，是否禁用前缀
 *
 * 还可以更加的自定义的，比如 key value 的序列化策略
 * RedisCacheWriter 写入策略
 * 设定key 的前缀是什么
 *
 *
 * @author gengzi
 * @date 2020年6月17日13:22:5
 */
@Getter
@AllArgsConstructor
public enum CacheRedisConfigEnum {

    // 产品信息 测试1
    PRODUCT_INFO_ID_TEST(-1, ChronoUnit.DAYS, true,true),
    // 产品信息 测试2
    PRODUCT_INFO_ID(20, ChronoUnit.SECONDS, true,true),
    // 产品信息,不使用前缀
    PRODUCT_INFO_ID_NOPREFIX(500, ChronoUnit.SECONDS, false,false);

    // 过期时间 -1 不过期
    private long ttl;
    // 时间单位
    private ChronoUnit chronoUnit;
    // 是否允许保存空值  true 允许，false 不允许
    private boolean cacheNullValues;
    // 是否使用key前缀  true 使用，false 不使用
    private boolean useKeyPrefix;

}
