package fun.gengzi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h1>自定义线程池</h1>
 *
 * @author gengzi
 * @date 2021年12月13日23:00:23
 */
@Configuration
public class ThreadPoolConfig {

    // 核心数 cpu 核心数
    private static final int CORE_POOL_SIZE = 6;
    // 最大线程数 cpu 核心数 +1
    private static final int MAX_POOL_SIZE = 7;
    // 队列大小
    private static final int QUEUE_CAPACITY = 10000;
    // 超时时间
    private static final Long KEEP_ALIVE_TIME = 60L;

    @Bean("asyncOneThreadPool")
    public ThreadPoolExecutor getAsyncOneThreadPoolExecutor() {
        return new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ShardingTreadFactory("async"),
                // 多出的任务由caller 执行
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    static class ShardingTreadFactory implements ThreadFactory {
        private final static AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final AtomicInteger mThreadNum = new AtomicInteger(1);
        private final String namePrefix;

        public ShardingTreadFactory(String name) {
            namePrefix = "ShardingTreadpool-" + POOL_NUMBER.getAndIncrement() + "-" + name + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            if (r == null) {
                throw new RuntimeException("没有可执行的任务");
            }
            return new Thread(r, namePrefix + mThreadNum.incrementAndGet());
        }
    }


}
