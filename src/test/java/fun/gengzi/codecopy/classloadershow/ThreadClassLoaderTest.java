package fun.gengzi.codecopy.classloadershow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ThreadClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

        // 加载驱动程 序
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection("", "user", "info");

        // 设置线程的上下文类加载器
        Thread.currentThread().setContextClassLoader(ClassLoader.getSystemClassLoader());

        // 获取当前线程的上下文类加载器
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();


    }
}
