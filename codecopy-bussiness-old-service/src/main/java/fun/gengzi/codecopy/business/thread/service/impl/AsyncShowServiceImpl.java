package fun.gengzi.codecopy.business.thread.service.impl;

import fun.gengzi.codecopy.business.thread.service.AsyncShowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncShowServiceImpl implements AsyncShowService {

    private Logger logger = LoggerFactory.getLogger(AsyncShowServiceImpl.class);

    /**
     * 演示使用异步的方法
     * <p>
     * 注意点  @Async 如果不指定线程池，会使用默认的线程池（ SimpleAsyncTaskExecutor 每次执行客户提交给它的任务时，它会启动新的线程）
     * 不建议使用
     *
     * @param imgUrl 图片地址
     */
    @Override
    @Async("asyncOneThreadPool")
    public void doSomething(String imgUrl) {
        logger.info("当前线程名称：{}", Thread.currentThread().getName());

        logger.info("做一些事情");
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
