package fun.gengzi.codecopy.business.luckdraw.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntergralDaoTest {


    @Autowired
    private IntergralDao intergralDao;

    //创建线程池  调整队列数 拒绝服务
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5 + 1, 10l, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000));

    /**
     *
     */
    @Test
    public void fun01() {
        int skillNum = 1000;
        final CountDownLatch latch = new CountDownLatch(skillNum);//N个购买者
        for (int i = 0; i < 1000; i++) {
            final long userId = i;
            Runnable task = () -> {
                //这里使用的乐观锁、可以自定义抢购数量、如果配置的抢购人数比较少、比如120:100(人数:商品) 会出现少买的情况
                //用户同时进入会出现更新失败的情况
                Integer hd_001 = intergralDao.deductionIntergral("hd_001", "2", 25);
                latch.countDown();
            };
            executor.execute(task);
        }
        try {
            latch.await();// 等待所有人任务结束
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}