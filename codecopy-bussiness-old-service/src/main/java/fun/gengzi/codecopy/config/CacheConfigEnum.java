package fun.gengzi.codecopy.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.checkerframework.checker.units.qual.A;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * <h1>本地缓存配置枚举类</h1>
 * 该类配置系统的 缓存策略  缓存名称，缓存保存时间，时间单位，最大条数
 *
 * @author gengzi
 * @date 2020年6月11日13:32:15
 */
@Getter
@AllArgsConstructor
public enum CacheConfigEnum {

    // 菜单项信息
    DATADIC_GLOBAL_MENU(-1, TimeUnit.DAYS, -1),
    // 菜单项信息类型为F的
    DATADIC_GLOBAL_MENU_TYPE_F(10, TimeUnit.MINUTES, 5),
    // 菜单项信息类型为O的
    DATADIC_GLOBAL_MENU_TYPE_O(200, TimeUnit.SECONDS, 100),
    // 服务接口信息  设置缓存策略在4小时未写入，过期缓存
    DATADIC_SERVER_INFO(4, TimeUnit.HOURS, 100),
    // 产品信息 设置最大缓存10000 条
    PRODUCT_INFO_ID_CACHE_AVALANCHE(50,TimeUnit.SECONDS, 1000),
    // 活动信息
    LUCKDRAW_LOCALCACHE(10,TimeUnit.SECONDS, -1);

    // 过期时间 -1 不过期
    private long ttl;
    // 时间单位
    private TimeUnit timeUnit;
    // 最大条数  -1 不限制
    private long maxSize;


}
