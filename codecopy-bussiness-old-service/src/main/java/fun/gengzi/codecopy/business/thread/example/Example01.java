package fun.gengzi.codecopy.business.thread.example;


public class Example01 {


    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            Singleton instance = Singleton.getInstance();
            System.out.println(instance);
        });

        Thread thread1 = new Thread(() -> {
            Singleton instance = Singleton.getInstance();
            System.out.println(instance);
        });

        thread.start();
        thread1.start();

    }


}
