package fun.gengzi.codecopy.business.problemsolve.interrupt;


/**
 * 循环线程，中断
 */
public class ThreadInterrupt {

    public static void main(String[] args) throws InterruptedException {
        Runner runner = new Runner();

        Thread thread = new Thread(runner);
        thread.start();
        Thread.sleep(5000);
        // 中断线程，这里的中断，并不是jvm真正中断线程，只是未此线程增加了一个 中断标志（true）
        // 具体中断线程逻辑需要自己实现
        thread.interrupt();

        // 调用取消方法，来实现中断
        // runner.cancel();
    }

    static class Runner implements Runnable {

        private volatile int i = 0;
        // 信号标志
        private volatile boolean flag = true;

        @Override
        public void run() {
            // 检测到线程中断状态为 true 或者 信号标志为 false，就退出
            while (flag && !Thread.currentThread().isInterrupted()) {
                System.out.println("打印:" + i++);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
                     // 如果触发了异常，如果要中止线程，请再后面加 return
//                    return;
//                }
            }
        }

        /**
         * 中断
         */
        private void cancel() {
            flag = false;
        }
    }
}
