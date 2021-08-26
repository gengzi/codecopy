package fun.gengzi.codecopy.business.thread.example.count;


import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <H1>线程安全的计数器</H1>
 *
 * @author gengzi
 * @date 2021年6月29日10:20:57
 */
public class ThreadSafeCounters {


    // 原子化的integer
    AtomicInteger atomicInteger = new AtomicInteger(0);

    // 非原子化的int
    private int i = 0;


    public static void main(String[] args) {
        ThreadSafeCounters threadSafeCounters = new ThreadSafeCounters();
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    threadSafeCounters.safeCount();
                    threadSafeCounters.count();
                }
            });
            threads.add(thread);
        }

        // 并发执行
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                // 加入
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(threadSafeCounters.atomicInteger.get());  // 100000
        System.out.println(threadSafeCounters.i);   // 97778
    }

    private void safeCount() {
        for (; ; ) {
            // 自旋
            //  获取数据
            int i = atomicInteger.get();
            // 比较并替换，判断 i 是否一致，一致就更新为新值
            boolean flag = atomicInteger.compareAndSet(i, ++i);
            if (flag) {
                // 成功退出
                break;
            }
        }
    }

    private void count() {
        i++;
    }


}
