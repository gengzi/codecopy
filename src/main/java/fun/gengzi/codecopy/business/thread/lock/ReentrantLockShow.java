package fun.gengzi.codecopy.business.thread.lock;

import lombok.SneakyThrows;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * <h1>演示ReentrantLock</h1>
 *
 * @author itw_gengsj
 * @date 2021年8月26日21:42:34
 */
public class ReentrantLockShow {
    // 求和
    private Integer sum = 0;
    // 重入锁
    private ReentrantLock lock = new ReentrantLock();
    // 日志打印
    PrintStream log = System.out;


    /**
     * 加法
     *
     * @param num
     * @return
     */
    @SneakyThrows
    private Integer add(Integer num) {
        log.println("<<<准备获取锁线程：" + Thread.currentThread().getName());
        // 获取锁
        lock.lock();
        log.println("当前获取锁线程>>>：" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
            return sum += num;
        } finally {
            System.out.println("和：" + sum);
            // 释放锁
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        ReentrantLockShow reentrantLockShow = new ReentrantLockShow();
        IntStream.range(1, 5).forEach(index -> {
            new Thread(() -> reentrantLockShow.add(index), ThreadNameEnum.getThreadNameByIndex(index)).start();
        });
    }


    /**
     * 线程名称枚举
     */
    enum ThreadNameEnum {
        THREAD_A(1, "ThreadA"),
        THREAD_B(2, "ThreadB"),
        THREAD_C(3, "ThreadC"),
        THREAD_D(4, "ThreadD");
        private int i;
        private String name;

        ThreadNameEnum(int i, String name) {
            this.i = i;
            this.name = name;
        }

        public int getI() {
            return i;
        }

        public String getName() {
            return name;
        }

        public static Map<Integer, String> indexToThreadNameMap = Arrays.stream(ThreadNameEnum.values()).collect(
                Collectors.toMap(ThreadNameEnum::getI, ThreadNameEnum::getName));

        public static String getThreadNameByIndex(Integer index) {
            return indexToThreadNameMap.get(index);
        }
    }

}
