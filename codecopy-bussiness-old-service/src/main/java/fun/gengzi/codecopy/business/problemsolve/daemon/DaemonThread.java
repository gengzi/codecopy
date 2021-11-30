package fun.gengzi.codecopy.business.problemsolve.daemon;

import lombok.SneakyThrows;

/**
 * 守护线程
 */
public class DaemonThread extends Thread{

    @SneakyThrows
    @Override
    public void run() {
       // 执行业务代码 假设需要 5000 秒
        System.out.println("开始工作");
        Thread.sleep(5000000);
        System.out.println("结束工作");
    }
}
