package fun.gengzi.codecopy.business.luckdraw.service.impl;

import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class LuckdrawServiceImplTest {


//    @Test
//    public void fun02() throws InterruptedException {
//        // connects to 127.0.0.1:6379 by default
//        RedissonClient redisson = Redisson.create();
//
//        RLock lock = redisson.getLock("lock");
//        lock.lock(2, TimeUnit.SECONDS);
//
//        Thread t = new Thread() {
//            public void run() {
//                RLock lock1 = redisson.getLock("lock");
//                lock1.lock();
//                lock1.unlock();
//            }
//
//            ;
//        };
//
//        t.start();
//        t.join();
//
//        lock.unlock();
//
//        redisson.shutdown();
//    }


    //    @Test
    public static void main(String[] args) throws InterruptedException {

//
        TrainDemo td = new TrainDemo();
        for (int i = 1; i <= 2; i++) {
            new Thread(td, i + "号窗口").start();
            ;
        }

        // connects to 127.0.0.1:6379 by default
//        RedissonClient redisson = Redisson.create();
//
//        RLock lock = redisson.getLock("lock");
//        lock.lock(2, TimeUnit.SECONDS);
//
//
//
//        lock.unlock();
//
//        redisson.shutdown();
    }


    static class TrainDemo implements Runnable {

        private static int total_count = 100;// 一共一百张火车票
        RedissonClient redisson = Redisson.create();

        @Override
        public void run() {
            while (true) {
                if (total_count > 0) {
                    try {
                        // 为了能看出效果，我们在这停歇一百毫秒
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sale();
                } else {
                    break;// 票已经没有了，跳出while true循环
                }
            }
            System.out.println(Thread.currentThread().getName() + " 的票已售空");

        }

        //如果将当前方法声明为static，那么能替换this锁的方式就是 SyncThread.class
        private void sale() {
            //synchronized使用的是this对象锁，如果某个线程获得了锁，那么其他线程只可以执行当前对象的非临界区（非加锁程序块）
            //获得锁的线程在进入下一个加锁临界区（加锁块）则不用再次获取锁，这就是锁的可重如性。
            //JVM负责跟踪对象被加锁的次数，在任务第一次给对象加锁的时候，计数变成1，每当这个相同的任务在这个对象上获得锁时，计数都会递增（只有首先获得锁的任务才能继续获取锁）
            //每当任务离开一个synchronized的方法，计数递减，当计数为0时，锁被完全释放，此时别的任务线程就可以使用此资源。
//            synchronized (this) {

            RLock lock = redisson.getLock("lock");
            lock.lock(2, TimeUnit.SECONDS);
            if (total_count > 0) {

                total_count--;//已经卖出，票的总数减去1
                System.out.println(Thread.currentThread().getName() + " 卖出 " + (100 - total_count) + " 张票");
            }
            lock.unlock();

//            }
        }

    }

}