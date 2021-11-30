package fun.gengzi.codecopy.business.shorturl.service.impl;

import fun.gengzi.codecopy.business.shorturl.dao.ShortUrlGeneratorTestDao;
import fun.gengzi.codecopy.business.shorturl.entity.Shorturl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortUrlGeneratorServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(ShortUrlGeneratorServiceImplTest.class);

    @Autowired
    private ShortUrlGeneratorTestDao shortUrlGeneratorTestDao;

    @Test
    public void fun01(){
        List<Shorturl> byShorturl = shortUrlGeneratorTestDao.findByShorturl("http://localhost:8089/u/1H");
        // 打印结果：Shorturl(id=105, shorturl=http://localhost:8089/u/1H, longurl=https://gengzi666.gitee.io/code-animal/#/321, termtime=2020-05-26, isoverdue=1, createtime=2020-05-26, updatetime=2020-05-26)
        byShorturl.forEach(shorturl -> logger.info("打印结果：{}",shorturl.toString()));
    }


}