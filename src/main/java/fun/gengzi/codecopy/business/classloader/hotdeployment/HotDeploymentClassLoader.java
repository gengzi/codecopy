package fun.gengzi.codecopy.business.classloader.hotdeployment;

import cn.hutool.core.io.file.FileReader;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * <h1>热部署classloader</h1>
 *
 * @author gengzi
 * @date 2021年3月31日22:25:52
 */
public class HotDeploymentClassLoader extends ClassLoader {


    private String dir;

    /**
     * 指定当前加载器的父加载器
     *
     * @param parent 父加载器
     */
    public HotDeploymentClassLoader(ClassLoader parent, String dir) {
        super(parent);
        this.dir = dir;
    }


    /**
     * 重写findclass
     * <p>
     * 使用findclass 来重新加载同一类，避免使用loadClass来加载，会使用缓存中的类
     * loadClass方法第一步就是调用findLoadedClass(String)来检查该类是否已经加载。
     * class类被同一个类加载器加载多次会报错
     *
     * @param name 二进制类名
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String file = dir + File.separator + (name.replace(".", "/")) + ".class";
        FileReader fileReader = new FileReader(file);
        byte[] bytes = fileReader.readBytes();
        return defineClass(name, bytes, 0, bytes.length);
    }
}
