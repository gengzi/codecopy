package fun.gengzi.codecopy.business.authentication.controller;

import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 参考：https://www.cnblogs.com/jifeng/p/8901168.html
 *
 *
 *
 */
public class SecurityInterfaceControllerTest {

    @Test
    public void fun01() {
        // 使用java 代码调用 js 实现加密解密。目的： 模拟前端对请求参数的加密和解密
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        // 获得引擎
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
        try {
            // 执行js 语法
            Object eval = nashorn.eval(String.valueOf("'hello world'.length()"));
            System.out.println(eval);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取js 文件，并执行js function
     */
    @Test
    public void fun02() {
        // 使用java 代码调用 js 实现加密解密。目的： 模拟前端对请求参数的加密和解密
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
        try {
            // String path = ClassLoader.getSystemClassLoader().getResource("js/crypto-js-4.0.0/core.js").getPath();
            String path = "D:\\ideaworkspace\\codecopy\\codecopy\\src\\main\\resources\\js\\crypto-js-4.0.0\\crypto-js.js";
            String path1 = "D:\\ideaworkspace\\codecopy\\codecopy\\src\\main\\resources\\js\\other.js";
            // 载入需要执行的js
            Object eval = nashorn.eval(Files.newBufferedReader(Paths.get(path)));
            Object eval1 = nashorn.eval(Files.newBufferedReader(Paths.get(path1)));

            Invocable in = (Invocable) nashorn;
            // 调用 js 的function 方法
            Object o = in.invokeFunction("getAesString", "hello", "aaaa", "123456789");
            // 输出function 的返回结果
            System.out.println(o);
            Object o1 = in.invokeFunction("getDAesString", "ybNhAzXC1FDKFbN/DIB2gw==", "aaaa", "123456789");
            System.out.println(o1);

            // 获取js中的某一个属性
            // Object o = nashorn.get("CryptoJS");
            // 执行js对象的某个方法  比如 CryptoJS.MD5("hello world") 可以这样理解
            // Object o1 = in.invokeMethod(o, "MD5", "hello world");
            // System.out.println(o1);
            // Object toString = in.invokeMethod(o1, "toString", "16");
            // System.out.println(toString);

        } catch (ScriptException | IOException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


}