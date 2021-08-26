package fun.gengzi.codecopy.business.thread.example.dbpool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;

public class ConnectionTest {
    // 默认的线程池资源
    static ConnectionPool connectionPool  = new  ConnectionPool(10);
    static CountDownLatch start = new CountDownLatch(1);

    public static void main(String[] args) {
        // 调用
        for (int i = 0; i < 200; i++) {
          new Thread(new ConnectionRuner()).start();
        }
        start.countDown();
    }


    static class  ConnectionRuner implements Runnable{
        @Override
        public void run() {
            try {
                // 等待所有线程一起触发
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                //  获取一个数据库连接
                Connection connection = connectionPool.fethchConnection(1000);
                if(connection != null){
                    try {
                        // 业务耗时
                        Thread.sleep(100);
                    }finally {
                        // 释放连接
                        connectionPool.reseleConnection(connection);
                        System.out.println("conn");
                    }
                }else {
                    System.out.println("null");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }


}
