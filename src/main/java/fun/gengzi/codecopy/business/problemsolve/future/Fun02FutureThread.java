package fun.gengzi.codecopy.business.problemsolve.future;

import lombok.SneakyThrows;

import java.util.concurrent.*;

public class Fun02FutureThread {


    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 6,
            60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(50));

    public static void main(String[] args) throws InterruptedException {

        // 不能获取返回结果
        // threadPoolExecutor.execute();

        for (int j= 0; j< 10; j++) {

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
                future.get(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                // TODO 必须加return；
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
                boolean cancel = future.cancel(true);
                System.out.println(cancel);
            } finally {
//            threadPoolExecutor.shutdownNow();
            }
        }


        Thread.sleep(50000000);


    }

}
