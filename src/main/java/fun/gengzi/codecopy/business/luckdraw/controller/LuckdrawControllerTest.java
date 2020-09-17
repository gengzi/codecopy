package fun.gengzi.codecopy.business.luckdraw.controller;

import fun.gengzi.codecopy.business.luckdraw.dao.IntergralDao;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Api(value = "luckdrawTest", tags = {"luckdrawTest"})
@Controller
@RequestMapping("/luckdrawTest")
public class LuckdrawControllerTest {

    @Autowired
    private IntergralDao intergralDao;

    //创建线程池  调整队列数 拒绝服务
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5 + 1, 10l, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000));

    private Logger logger = LoggerFactory.getLogger(LuckdrawControllerTest.class);

    /**
     * // 如果设置积分字段 无符号 ，当积分设置为 负数，会报以下异常
     [SQL]UPDATE tb_integral
     SET integral = integral - 25
     WHERE
     uid = '2'
     AND activityid = 'hd_001'
     AND integral - 25 >= 0

     [Err] 1690 - BIGINT UNSIGNED value is out of range in '(`luckdraw_db`.`tb_integral`.`integral` - 25)'
     *
     *
     *
     * @return
     */
    @ApiOperation(value = "测试这种乐观锁会不会出现超发", notes = "测试这种乐观锁会不会出现超发")
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/dbTest")
    @ResponseBody
    public ReturnData dbTest() {
        int skillNum = 100;
        final CountDownLatch latch = new CountDownLatch(skillNum);//N个购买者
        for (int i = 0; i < 100; i++) {
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

        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }


}

