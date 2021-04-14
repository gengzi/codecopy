package fun.gengzi.codecopy.business.classloader.hotdeployment.v2;

import cn.hutool.core.io.FileUtil;
import lombok.SneakyThrows;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

public class SysApplication {


    public void test() {
        new TestCode();
    }

    /**
     * run
     * <p>
     * 利用全盘委托机制，加载当前类。那么当前类下面 new 对象的类，都会使用加载当前类的类加载器
     */
    @SneakyThrows
    public static void run(Class clazz) {

        URL resource = Thread.currentThread().getContextClassLoader().getResource("");
        String path = resource.getPath();
        String filepath = path + clazz.getPackage().getName().replace(".", "/");
        System.out.println("启动--------");
        restart();
        // 开启文件监听
        File file = FileUtil.file(filepath);
        CheckListener.listener(file);
    }


    public static void restart() throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        // 获取当前类的全限定名称
        String name = SysApplication.class.getName();
        // 创建自定义类加载器
        HotDeploymentClassLoaderV3 hotDeploymentClassLoaderV3 = new HotDeploymentClassLoaderV3();
        Class<?> aClass = hotDeploymentClassLoaderV3.loadClass(name);
        // 实例化
        Object object = aClass.newInstance();
        // 调用test方法
        Method test = aClass.getMethod("test");
        test.invoke(object);
    }
}
