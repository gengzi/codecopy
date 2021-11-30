package fun.gengzi.codecopy.business.classloader.hotdeployment;

import cn.hutool.core.io.file.FileReader;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * <h1>热部署classloader</h1>
 *
 * @author gengzi
 * @date 2021年3月31日22:25:52
 */
public class HotDeploymentClassLoaderV2 extends URLClassLoader {


    public HotDeploymentClassLoaderV2(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    /**
     *
     * 如果所加载得类库，不在当前用户路径（classpath）下，才会执行重写的 findclass 加载
     *
     * 建议重写findclass 是尽量遵循双亲委派模型的情况下，才这样使用
     * 也就是你在当前应用下写的代码，使用自定义类加载器，该类加载器只重写了findclass方法，这些代码类不会被自定义类加载器加载，都会被appclassloader加载。
     * 或者设置自定义类加载器的父加载器是null 或者 ext 来避免父类加载器是 appclassClassloader，这样bootstrap 和 ext 类加载器，因无法加载你写的类，就会执行findclass逻辑
     *
     *
     * 如果你想设置用户路径下的某些类加载，使用自定义类加载器，最好重写loaderclass方法，更加自由
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if ("fun.gengzi.codecopy.business.classloader.hotdeployment.TestCode".equals(name)) {
            return findClass(name);
        }
        return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }
}
