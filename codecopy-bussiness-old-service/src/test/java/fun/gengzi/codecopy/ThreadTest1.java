package fun.gengzi.codecopy;

public class ThreadTest1 {

    private static final int num = 1000 * 1000;

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            for (int i = 0; i < num; i++) {
                System.out.println(i);
            }
        },"线程1").start();

        new Thread(()->{
            for (int i = 0; i < num; i++) {
                System.out.println(i);
            }
        },"线程2").start();

        new Thread(()->{
            for (int i = 0; i < num; i++) {
                System.out.println(i);
            }
        },"线程3").start();

        new Thread(()->{
            for (int i = 0; i < num; i++) {
                System.out.println(i);
            }
        },"线程4").start();
    }
}