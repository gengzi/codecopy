package fun.gengzi.codecopy.business.redis.config;

import fun.gengzi.codecopy.exception.RrException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class RedisManager {

    private Map<String, RedisTemplate> redisTemplateMap = new ConcurrentHashMap<>();

    public RedisManager(Map<String, RedisTemplate> redisTemplateMap) {
        this.redisTemplateMap = redisTemplateMap;
    }

    public RedisTemplate getRedisTemplate(Integer dbIndex) {
        RedisTemplate redisTemplate = redisTemplateMap.get("redisTemplate" + dbIndex);
        if (redisTemplate == null) {
            throw new RrException("Map不存在该redisTemplate");
        }
        return redisTemplate;
    }

}