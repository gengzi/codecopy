package fun.gengzi.codecopy.business.redis.config;

import fun.gengzi.codecopy.exception.RrException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * <h1>redis管理</h1>
 *
 * @author gengzi
 * @date 2020年12月16日22:37:18
 */
public class RedisManager {

    private Map<String, RedisTemplate> redisTemplateMap = new ConcurrentHashMap<>();

    /**
     * 构造方法初始化 redisTemplateMap 的数据
     *
     * @param redisTemplateMap
     */
    public RedisManager(Map<String, RedisTemplate> redisTemplateMap) {
        this.redisTemplateMap = redisTemplateMap;
    }

    /**
     * 根据数据库序号，返回对应的RedisTemplate
     *
     * @param dbIndex 序号
     * @return {@link RedisTemplate}
     */
    public RedisTemplate getRedisTemplate(Integer dbIndex) {
        RedisTemplate redisTemplate = redisTemplateMap.get("redisTemplate" + dbIndex);
        if (redisTemplate == null) {
            throw new RrException("Map不存在该redisTemplate");
        }
        return redisTemplate;
    }

}