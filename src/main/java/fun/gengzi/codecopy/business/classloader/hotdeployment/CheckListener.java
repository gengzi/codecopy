package fun.gengzi.codecopy.business.classloader.hotdeployment;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.RuntimeUtil;
import lombok.SneakyThrows;
import org.aspectj.weaver.ast.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

public class CheckListener {

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, MalformedURLException, IllegalAccessException, InstantiationException {
        // 使用自定义类加载器加载类
        File file1 = new File("D:\\ideaworkspace");
        URI uri = file1.toURI();
        URL url = uri.toURL();
        HotDeploymentClassLoaderV2 hotDeploymentClassLoaderV2 = new HotDeploymentClassLoaderV2(new URL[]{url}, Thread.currentThread().getContextClassLoader());
        Class<?> aClass = hotDeploymentClassLoaderV2.loadClass("fun.gengzi.codecopy.business.classloader.hotdeployment.TestCode");
        Console.log("类加载器：{}",aClass.getClassLoader());
        Object instance = aClass.newInstance();
        Console.log("hashCode：{}",instance.hashCode());


        // 持续监听
        // 检索代码变化
        File file = FileUtil.file("D:\\idea_workspace\\codecopy\\src\\main\\java\\fun\\gengzi\\codecopy\\business\\classloader\\hotdeployment\\");
        //这里只监听文件或目录的修改事件
        WatchMonitor watchMonitor = WatchMonitor.create(file, WatchMonitor.ENTRY_MODIFY);
        watchMonitor.setWatcher(new Watcher() {
            @Override
            public void onCreate(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("创建：{}-> {}", currentPath, obj);

            }

            @SneakyThrows
            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("修改：{}-> {}", currentPath, obj);
                // 调用命令行工具 javac 编译java文件为class文件，存储到运行目录下
                String str = RuntimeUtil.execForStr("javac -d D:\\ideaworkspace D:\\idea_workspace\\codecopy\\src\\main\\java\\fun\\gengzi\\codecopy\\business\\classloader\\hotdeployment\\TestCode.java");
                // 调用自定义类加载器，重新加载class文件
//                HotDeploymentClassLoader hotDeploymentClassLoader = new HotDeploymentClassLoader(Thread.currentThread().getContextClassLoader(), "D:\\ideaworkspace");
//                Class<?> aClass = hotDeploymentClassLoader.findClass("fun.gengzi.codecopy.business.classloader.hotdeployment.TestCode");
//                Console.log("重新加载：{}", aClass);
//                Object instance = aClass.newInstance();
                //
                // TestCode testCode = new TestCode();

                hotDeploymentClassLoaderV2.close();

                Console.log("close类加载器");
                Console.log("instance:{}", instance);


                File file1 = new File("D:\\ideaworkspace");
                URI uri = file1.toURI();
                URL url = uri.toURL();
                HotDeploymentClassLoaderV2 hotDeploymentClassLoaderV2 = new HotDeploymentClassLoaderV2(new URL[]{url}, Thread.currentThread().getContextClassLoader());
                Class<?> aClass = hotDeploymentClassLoaderV2.loadClass("fun.gengzi.codecopy.business.classloader.hotdeployment.TestCode");
                Console.log("类加载器：{}",aClass.getClassLoader());
                Object instance = aClass.newInstance();
                Console.log("hashCode：{}",instance.hashCode());

            }

            @Override
            public void onDelete(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("删除：{}-> {}", currentPath, obj);
            }

            @Override
            public void onOverflow(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("Overflow：{}-> {}", currentPath, obj);
            }
        });

        //设置监听目录的最大深入，目录层级大于制定层级的变更将不被监听，默认只监听当前层级目录
        watchMonitor.setMaxDepth(3);
        //启动监听
        watchMonitor.start();
    }
}
