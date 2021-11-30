package fun.gengzi.codecopy.business.problemsolve;


import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileWriter;
import lombok.SneakyThrows;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 流是否关闭排查
 */
public class InOrOutPutStream {


    public static void main(String[] args) {
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(new File("E:\\md_work\\命令.txt")))) {
//            System.out.println(reader.readLine());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        fun01();


    }


    @SneakyThrows
    public static void fun01() {

        InOrOutPutStreamThreadPoolExecute threadPoolExecutor = new InOrOutPutStreamThreadPoolExecute(5, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(50));


        new Thread(() -> {
            while (true) {
                check(threadPoolExecutor);
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int size = threadPoolExecutor.hashMap.entrySet().size();
                if (size == 0) {
                    System.out.println("当前已经没有线程了");
                }
            }
        }).start();


//        HttpURLConnection.HttpInputStream
        threadPoolExecutor.execute(() -> {
            while (!Thread.interrupted()) {
                InputStream inputStream = null;
                try {
                    inputStream = new URL("http://localhost:8080").openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileWriter fileWriter = FileWriter.create(new File("D:\\ideaworkspace\\1.jpg"));
                File file = fileWriter.writeFromStream(inputStream);
            }
            throw new RuntimeException("中断");
        });


//
//        URL url = new URL("https://w.wallhaven.cc");
//        URLConnection urlConnection = url.openConnection();
//        urlConnection.connect();
//        urlConnection.getInputStream();
//        int connectTimeout = urlConnection.getConnectTimeout();
//        System.out.println(connectTimeout);
//        int readTimeout = urlConnection.getReadTimeout();
//        System.out.println(readTimeout);


    }

    public static void check(InOrOutPutStreamThreadPoolExecute threadPoolExecutor) {
        int activeCount = threadPoolExecutor.getActiveCount();

        System.out.println("线程存活个数：" + activeCount);
        System.out.println("开始检测--------------");

        List<Map.Entry<Long, Task>> collect = threadPoolExecutor.hashMap.entrySet().stream().filter(map -> {
            return map.getKey() + 100000 > System.currentTimeMillis();
        }).collect(Collectors.toList());

        for (Map.Entry<Long, Task> map :
                collect) {
            System.out.println("准备杀死线程");
            Task task = map.getValue();
            Runnable runnable = task.getR();
            Thread thread = task.getT();

            // 停止
            thread.interrupt();
            boolean interrupted = thread.isInterrupted();
            threadPoolExecutor.remove(runnable);
            System.out.println(interrupted);
            threadPoolExecutor.hashMap.remove(map.getKey());
        }
        System.out.println("结束检测--------------");

    }


}
