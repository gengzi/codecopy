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
        Thread.sleep(3000);
    }
}
