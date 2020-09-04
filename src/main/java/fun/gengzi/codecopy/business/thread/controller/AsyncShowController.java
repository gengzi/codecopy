package fun.gengzi.codecopy.business.thread.controller;


import fun.gengzi.codecopy.business.thread.service.AsyncShowService;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;


@Api(value = "多线程-异步", tags = {"多线程-异步"})
@Controller
@RequestMapping("/asyncShow")
public class AsyncShowController {

    private Logger logger = LoggerFactory.getLogger(AsyncShowController.class);

    @Resource(name = "asyncOneThreadPool")
    private ThreadPoolExecutor asyncOneThreadPool;

    @Autowired
    private AsyncShowService asyncShowService;


    /**
     * 注意点：@Async异步方法跟调用它的同步方法在同一个类中，会导致异步方法不生效
     *
     * @return
     */
    @ApiOperation(value = "异步-避免io操作影响主线程", notes = "异步-避免io操作影响主线程")
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/asyncShow")
    @ResponseBody
    public ReturnData async() {
        for (int i = 0; i < 500; i++) {
            asyncShowService.doSomething("xxx");
        }
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }

    // 检测线程池运行状况

    @ApiOperation(value = "异步-检测线程池运行状况", notes = "异步-检测线程池运行状况")
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/asyncShowInfo")
    public void asyncShowInfo() {

        while (true) {
            int queueSize = asyncOneThreadPool.getQueue().size();
            long taskCount = asyncOneThreadPool.getTaskCount();
            int activeCount = asyncOneThreadPool.getActiveCount();
            long completedTaskCount = asyncOneThreadPool.getCompletedTaskCount();
            logger.info("总线程数|当前活动线程数|当前排队线程数|执行完成线程数");
            logger.info("   {}  |      {}    |      {}     |      {}    ", taskCount, activeCount, queueSize, completedTaskCount);

            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}

