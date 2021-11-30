package fun.gengzi.codecopy.business.problemsolve.future;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Runner implements Runnable{

    @Override
    public void run() {
            int i = 0;


//        for (int j = 0; j < 1000000000; j++) {
//            System.out.println(j);
//        }

//            try {
//                for (int i = 0; i < 1000; i++) {
//                    System.out.println("执行" + i);
//                    Thread.sleep(1000);
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

//            for(;;){
//                if((i++) % 100000000 == 0){
//                    System.out.println("haha");
//                    System.out.println(Thread.currentThread().isInterrupted());
//                }
//
//            }
//            CountDownLatch countDownLatch = new CountDownLatch(1);
            InputStream inputStream = null;
            try {
                inputStream = new URL("http://localhost:8080").openStream();
//                countDownLatch.countDown();
            } catch (IOException e) {
                e.printStackTrace();
            }
//
//            try {
//                boolean await = countDownLatch.await(5, TimeUnit.SECONDS);
//                System.out.println("haha");
//            } catch (InterruptedException e) {
//                System.out.println("h---");
//                e.printStackTrace();
//                return;
//            }
    }
}
