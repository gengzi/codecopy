package fun.gengzi.codecopy.business.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * <h1>redis lua 脚本配置类</h1>
 */
@Configuration
public class RedisLuaScriptConfig {

    /**
     * 构造一个 script 脚本
     *
     * @return
     */
    @Bean
    public RedisScript<String> script() {
        Resource resource = new ClassPathResource("/redislua/zset_add_expire.lua");
        return RedisScript.of(resource, String.class);
    }

}
