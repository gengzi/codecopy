package fun.gengzi.codecopy.business.thread.scheduled;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolTest {


    public static void main(String[] args) {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);


        scheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("hahah");
            }

        }, 10, 20, TimeUnit.SECONDS);// 10 表示第一次延迟时间，20 表示每隔多长时间执行，时间单位


    }
}
