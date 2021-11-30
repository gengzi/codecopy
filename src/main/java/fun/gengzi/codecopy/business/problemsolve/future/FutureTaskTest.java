package fun.gengzi.codecopy.business.problemsolve.future;

import com.vip.vjtools.vjkit.concurrent.threadpool.ThreadPoolBuilder;
import lombok.SneakyThrows;

import java.util.concurrent.*;

public class FutureTaskTest {

    public static void main(String[] args) {
        fun02();
    }

    public static void fun02(){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<?> submit = executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("哈哈");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            Object o = submit.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void fun01(){
        FutureTask futureTaskCodeAnalysis = new FutureTask(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("哈哈");
                Thread.sleep(50000);
            }
        }, Void.class);
        // 同步执行，仅仅执行 其中的方法
        futureTaskCodeAnalysis.run();
        try {
            Object o = futureTaskCodeAnalysis.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
