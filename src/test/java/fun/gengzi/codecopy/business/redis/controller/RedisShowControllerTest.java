package fun.gengzi.codecopy.business.redis.controller;

import fun.gengzi.codecopy.vo.ReturnData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisShowControllerTest {

    private Logger logger = LoggerFactory.getLogger(RedisShowController.class);


    @Autowired
    private RedisShowController redisShowController;


    @Test
    public void timedChangeLeaderboard() {
        ReturnData aa = redisShowController.timedChangeLeaderboard("aa");
        logger.info("{}", aa);


    }
}