package fun.gengzi.codecopy.business.classloader.tomcat;

public class ClassLoaderTest {


    public static void main(String[] args) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        MyClassLoader myClassLoader = new MyClassLoader(contextClassLoader);
        MyClassLoader2 myClassLoader2 = new MyClassLoader2(myClassLoader);
        System.out.println(myClassLoader.getParent());
        System.out.println(myClassLoader2.getParent());
    }

}
