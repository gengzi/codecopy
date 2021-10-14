package fun.gengzi.codecopy.business.problemsolve.future;

import lombok.SneakyThrows;

import java.util.concurrent.*;

public class Fun02FutureThread {


    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 6,
            60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(50));

    public static void main(String[] args) throws InterruptedException {

        // 不能获取返回结果
        // threadPoolExecutor.execute();

        for (int j = 0; j < 10; j++) {

            // 可以得到一个返回结果
            Future<?> future = threadPoolExecutor.submit(() -> {
                try {
                    for (int i = 0; i < 1000; i++) {
                        System.out.println("执行" + i);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            // submit 可以传一个string ，并响应回来

            try {
                future.get(50, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("中断线程");
                // TODO 必须加return；
                return;
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
                // 中断执行此任务线程的方式来试图停止任务，返回true 说明成功
                boolean cancel = future.cancel(true);
                System.out.println("中断状态" + cancel);
            } finally {

                System.out.println("销毁线程池");
                //            threadPoolExecutor.shutdownNow();
            }
            System.out.println("销毁线程池");
            long taskCount = threadPoolExecutor.getTaskCount();
            System.out.println("线程池已安排执行的大致任务总数:" + taskCount);
            //            threadPoolExecutor.shutdownNow();
        }


        Thread.sleep(50000000);


    }
}


