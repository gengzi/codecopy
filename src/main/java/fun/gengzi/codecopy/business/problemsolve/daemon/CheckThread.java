package fun.gengzi.codecopy.business.problemsolve.daemon;

import lombok.SneakyThrows;

/**
 * 用户线程
 */
public class CheckThread  extends Thread{

    @SneakyThrows
    @Override
    public void run() {
        // 仅允许3秒
        System.out.println("检测开始");
        Thread.sleep(3000);
        System.out.println("超时-检测结束");
    }
}
