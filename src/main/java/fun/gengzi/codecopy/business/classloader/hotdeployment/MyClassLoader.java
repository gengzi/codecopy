package fun.gengzi.codecopy.business.classloader.hotdeployment;

import java.io.*;

public class MyClassLoader extends ClassLoader {

    private String codePath;

    public MyClassLoader(ClassLoader parent, String codePath) {
        super(parent);
        this.codePath = codePath;
    }

    public MyClassLoader(String codePath) {
        this.codePath = codePath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        BufferedInputStream bis = null;
        ByteArrayOutputStream baos = null;
        try {
            //1.字节码路径
            String fileName = codePath + File.separator + (name.replace(".", "/")) + ".class";
            //2.获取输入流
            bis = new BufferedInputStream(new FileInputStream(fileName));
            //3.获取输出流
            baos = new ByteArrayOutputStream();
            //4.io读写
            int len;
            byte[] data = new byte[1024];
            while ((len = bis.read(data)) != -1) {
                baos.write(data, 0, len);
            }
            //5.获取内存中字节数组
            byte[] byteCode = baos.toByteArray();
            //6.调用defineClass 将字节数组转成Class对象
            Class<?> defineClass = defineClass(null, byteCode, 0, byteCode.length);
            return defineClass;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}