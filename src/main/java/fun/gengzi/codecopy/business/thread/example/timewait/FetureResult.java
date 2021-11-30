package fun.gengzi.codecopy.business.thread.example.timewait;


import cn.hutool.core.date.DateUtil;
import lombok.SneakyThrows;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * <h1>限定时间获取结果</h1>
 * <p>
 * 限定时间获取结果，如果没有超时，返回默认结果
 */
public class FetureResult {

    Object lock = new Object();

    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(1000);
                System.out.println("执行了");
            }
        }, "test");
        thread.start();

//        Thread thread1 = new Thread(new Runnable() {
//            @SneakyThrows
//            @Override
//            public void run() {
//
//            }
//        });
//        thread1.start();

        FetureResult fetureResult = new FetureResult();
        boolean b = fetureResult.get(5, TimeUnit.SECONDS, thread);
        System.out.println(b);

    }

    private boolean get(long timeout, TimeUnit unit, Thread thread) throws InterruptedException {
        synchronized (lock) {
            final long nanos = unit.toNanos(timeout);
            // 超时时间
            final long deadline = System.nanoTime() + nanos;
            long temp = nanos;

            // 获取业务代码执行结果
            while (!thread.getState().equals(Thread.State.TERMINATED) && temp > 0) {
                temp = deadline - System.nanoTime();
                System.out.println(thread.getState() + ":" + temp);
                // wait 在lock 环境下才能使用
                lock.wait( temp < 1000000 ? 1000: temp / 1000000);
            }

            if (thread.getState().equals(Thread.State.TERMINATED) && temp >= 0) {
                // 执行成功
                return true;
            } else {
                thread.interrupt();
                return false;
            }
        }

    }


}
