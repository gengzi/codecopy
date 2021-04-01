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
     * 建议使用findclass 是不想在破坏双亲委派模型的情况下，才这样
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
