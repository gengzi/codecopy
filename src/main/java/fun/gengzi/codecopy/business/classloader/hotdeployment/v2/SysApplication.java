package fun.gengzi.codecopy.business.classloader.hotdeployment.v2;

import lombok.SneakyThrows;
import org.aspectj.weaver.ast.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SysApplication {


    public   void test(){
        new TestCode();
    }

    /**
     * run
     */
    @SneakyThrows
    public static void run(Class clazz){
        //  获取当前类的全限定名称
        //  创建自定义类加载器
        //  利用全盘委托机制，加载当前类。那么当前类下面 new 对象的类，都会使用加载类的类加载器
        restart();
        // 开启监听
        CheckListener.listener();
    }


    public static  void restart() throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        String name = SysApplication.class.getName();
        HotDeploymentClassLoaderV3 hotDeploymentClassLoaderV3 = new HotDeploymentClassLoaderV3();
        Class<?> aClass = hotDeploymentClassLoaderV3.loadClass(name);
        Object object = aClass.newInstance();
        Method test = aClass.getMethod("test");
        test.invoke(object);
    }
}
