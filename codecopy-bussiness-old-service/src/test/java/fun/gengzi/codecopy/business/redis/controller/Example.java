package fun.gengzi.codecopy.business.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.Arrays;
import java.util.Collections;

public class Example {

    @Autowired
    RedisScript<Boolean> script;

    // 构造一个 script 脚本
    @Bean
    public RedisScript<Boolean> script() {
        ScriptSource scriptSource = new ResourceScriptSource(new ClassPathResource("META-INF/scripts/checkandset.lua"));
        return RedisScript.of((Resource) scriptSource, Boolean.class);
    }

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean checkAndSet(String expectedValue, String newValue) {
        return (boolean) redisTemplate.execute(script, Collections.singletonList("key"), "1",Arrays.asList(expectedValue, newValue));
    }
}