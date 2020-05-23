package fun.gengzi.codecopy;

import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTest{

    // 数据量
    private static final int num = 2000 * 1000;

    // 设置栅栏是为了防止子线程还没结束就执行main线程输出耗时时间
    private static final CountDownLatch countDownLatch = new CountDownLatch(4);

    private static ExecutorService service = Executors.newFixedThreadPool(4);

    private static final String filePath1 = "D:\\idea_workspace\\codecopy\\src\\test\\java\\fun\\gengzi\\test1.txt";
    private static final String filePath2 = "D:\\idea_workspace\\codecopy\\src\\test\\java\\fun\\gengzi\\test2.txt";
    private static final String filePath3 = "D:\\idea_workspace\\codecopy\\src\\test\\java\\fun\\gengzi\\test3.txt";
    private static final String filePath4 = "D:\\idea_workspace\\codecopy\\src\\test\\java\\fun\\gengzi\\test4.txt";

    private static File file1 = new File(filePath1);
    private static File file2 = new File(filePath2);
    private static File file3 = new File(filePath3);
    private static File file4 = new File(filePath4);

    public static void main(String[] args) throws InterruptedException, IOException {
        // 开始时间
        long startTime = System.currentTimeMillis();

        new Thread(new WriteFileThread(file1),"线程1").start();
        new Thread(new WriteFileThread(file2),"线程2").start();
        new Thread(new WriteFileThread(file3),"线程3").start();
        new Thread(new WriteFileThread(file4),"线程4").start();
        new Thread(new WriteFileThread(file3),"线程5").start();
        new Thread(new WriteFileThread(file4),"线程6").start();
        new Thread(new WriteFileThread(file3),"线程7").start();
        new Thread(new WriteFileThread(file4),"线程8").start();
        new Thread(new WriteFileThread(file4),"线程9").start();
        new Thread(new WriteFileThread(file4),"线程10").start();
        new Thread(new WriteFileThread(file3),"线程11").start();
        new Thread(new WriteFileThread(file4),"线程12").start();
        new Thread(new WriteFileThread(file4),"线程13").start();




        try {
            countDownLatch.await();
        } finally {
            service.shutdown();
        }

        // 结束时间
        long endTime = System.currentTimeMillis();
        System.out.println();
        System.out.println("总耗时间为：" + (endTime - startTime) / 1000.0 + "s");

    }

    static class WriteFileThread implements Runnable {

        private File file;

        public WriteFileThread(File file) {
            this.file = file;
        }

        @Override
        public void run() {
            writeFile(file);
        }
    }

    static void writeFile(File file){
        // 判断是否有该文件
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long startTime = System.currentTimeMillis();
        //创建输出缓冲流对象
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < num; i++) {
            try {
                bufferedWriter.write(i);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "执行完成，耗时 : " + (endTime - startTime) / 1000 + "s");
        countDownLatch.countDown();
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}