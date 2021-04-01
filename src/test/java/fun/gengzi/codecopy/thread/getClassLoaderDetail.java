package fun.gengzi.codecopy.thread;

public class getClassLoaderDetail {
    public static void main(String[] args) {
        String pathBoot = System.getProperty("sun.boot.class.path");
        System.out.println("BootStrapClassLoader实例范围开始--------------------");
        System.out.println(pathBoot.replaceAll(";", System.lineSeparator()));
        System.out.println("BootStrapClassLoader实例范围结束--------------------");

        System.out.println("ExtClassLoader实例范围开始--------------------");

        String pathExt = System.getProperty("java.ext.dirs");
        System.out.println(pathExt.replaceAll(";", System.lineSeparator()));
        System.out.println("ExtClassLoader实例范围结束--------------------");

        System.out.println("AppClassLoader实例范围开始--------------------");
        String pathApp = System.getProperty("java.class.path");
        System.out.println(pathApp.replaceAll(";", System.lineSeparator()));
        System.out.println("AppClassLoader实例范围结束--------------------");

    }
}
