package fun.gengzi.codecopy.business.luckdraw.config;

import fun.gengzi.codecopy.exception.RrException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池定义
 */
@Configuration
public class LuckdrawThreadPoolConfig {

    // 核心数 cpu 核心数
    private static final int CORE_POOL_SIZE = 6;
    // 最大线程数 cpu 核心数 +1
    private static final int MAX_POOL_SIZE = 7;
    // 队列大小
    private static final int QUEUE_CAPACITY = 10000;
    // 超时时间
    private static final Long KEEP_ALIVE_TIME = 60L;

    @Bean("luckdrawIntergralDaoThreadPool")
    public ThreadPoolExecutor getLuckdrawIntergralDaoThreadPoolExecutor() {
        return new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new LuckdrawTreadFactory("luckdraw-IntergralDao"),
                // 多出的任务由caller 执行
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Bean("luckdrawPrizeDaoThreadPool")
    public ThreadPoolExecutor getLuckdrawPrizeDaoThreadPoolExecutor() {
        return new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new LuckdrawTreadFactory("luckdraw-PrizeDao"),
                // 多出的任务由caller 执行
                new ThreadPoolExecutor.CallerRunsPolicy());
    }



    static class LuckdrawTreadFactory implements ThreadFactory {
        private final static AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final AtomicInteger mThreadNum = new AtomicInteger(1);
        private final String namePrefix;

        public LuckdrawTreadFactory(String name) {
            namePrefix = "pool-" + POOL_NUMBER.getAndIncrement() + "-" + name + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            if (r == null) {
                throw new RrException("没有可执行的任务");
            }
            return new Thread(r, namePrefix + mThreadNum.incrementAndGet());
        }
    }


}
