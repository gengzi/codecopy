//package fun.gengzi.codecopy.business.redis.config;
//
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.client.codec.StringCodec;
//import org.redisson.config.Config;
//import org.redisson.config.SingleServerConfig;
//import org.redisson.spring.data.connection.RedissonConnectionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//
//public class RedissonConfig {
//
//    @Autowired
//    private RedissonClient redissonClient;
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @Bean(name = "redisSingleClient", destroyMethod = "shutdown")
//    RedissonClient redisSingleClientInit() {
//        Config config = new Config();
//        config.setCodec(StringCodec.INSTANCE);
//        SingleServerConfig singleConfig = config.useSingleServer();
//        singleConfig.setAddress("redis://127.0.0.1:6379");
//        singleConfig.setDatabase(2);
//        return Redisson.create(config);
//    }
//
//
//    @Bean
//    public RedisConnectionFactory getRedisConnectionFactory(RedissonClient redissonClient){
//        redisTemplate.setConnectionFactory(new RedissonConnectionFactory(redissonClient));
//
//        return  new RedissonConnectionFactory(redissonClient);
//    }
//
//
//
//
//
//}
