package fun.gengzi.codecopy.business.problemsolve.daemon;


/**
 * <H1>使用守护线程解决线程超时问题</H1>
 * <p>
 * <p>
 * 守护线程执行业务代码，用户线程控制时间，当用户线程完毕，守护线程会自动退出
 * <p>
 * 需要两个线程
 * <p>
 * 当所有的非守护线程退出后，整个JVM 的进程就会退出，
 *
 *
 * 这个方法不可能用于生产环境，条件过于苛刻，当所有的非守护线程退出后，整个JVM 进程才会退出。一个系统包含了很多线程，执行不同业务，用户线程不只一个。
 *
 * @author gengzi
 * @date 2021年5月31日13:54:40
 */
public class Fun01DaemonThread {

    public static void main(String[] args) {
        DaemonThread deprecated = new DaemonThread();
        deprecated.setDaemon(true);
        deprecated.start();
        CheckThread checkThread = new CheckThread();
        checkThread.start();
    }

}
