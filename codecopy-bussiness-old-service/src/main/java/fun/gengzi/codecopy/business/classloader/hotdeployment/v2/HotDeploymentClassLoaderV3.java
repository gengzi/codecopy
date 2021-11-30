package fun.gengzi.codecopy.business.classloader.hotdeployment.v2;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;

import java.io.File;
import java.net.URL;

/**
 * <h1>热部署classloader</h1>
 *
 * @author gengzi
 * @date 2021年3月31日22:25:52
 */
public class HotDeploymentClassLoaderV3 extends ClassLoader {
    public static String dir;

    static {
        URL resource = Thread.currentThread().getContextClassLoader().getResource("");
        dir = resource.getPath();
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        // 加锁
        synchronized (getClassLoadingLock(name)){
            // 判断当前包名类，是否在当前用户classpath 下，如果存在，就执行自定义的findclass
            // 不是，就走父类的类加载器
            String file = dir + File.separator + (name.replace(".", "/")) + ".class";
            if(FileUtil.exist(file)){
                Class<?> aClass = findClass(name);
                if(aClass == null){
                    throw new ClassNotFoundException("未找到该类");
                }
                return aClass;
            }
            return super.loadClass(name);
        }
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String file = dir + File.separator + (name.replace(".", "/")) + ".class";
        FileReader fileReader = new FileReader(file);
        byte[] bytes = fileReader.readBytes();
        return defineClass(name, bytes, 0, bytes.length);
    }

}
