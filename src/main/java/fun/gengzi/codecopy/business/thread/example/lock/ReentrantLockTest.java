package fun.gengzi.codecopy.business.thread.example.lock;

import lombok.SneakyThrows;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    // 声明锁
    ReentrantLock lock = new ReentrantLock();

    // 声明变量
   volatile int sum = 0;

    @SneakyThrows
    public void add(int num) {
        lock.lock();
        try {
            System.out.println("num："+num);
            System.out.println(Thread.currentThread().getName());
            sum += num;
            Thread.sleep(100000);
            System.out.println(sum);
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
        // 10个线程相加
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(()->{
                reentrantLockTest.add(finalI);
            },"Thread"+finalI).start();
        }
    }


}
