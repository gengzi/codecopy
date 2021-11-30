package fun.gengzi.codecopy.business.thread.example.interrupt;

/**
 *  演示 interrupt 中断，用于唤醒轻量级阻塞
 *
 *
 */
public class Fun01 {

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        myThread.start();
        myThread.interrupt();

    }


    static class MyThread extends Thread{
        @Override
        public void run() {
            try {
                this.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("打印--");

        }
    }
}
