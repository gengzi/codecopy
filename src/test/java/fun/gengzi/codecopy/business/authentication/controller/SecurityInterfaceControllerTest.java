package fun.gengzi.codecopy.business.authentication.controller;

import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class SecurityInterfaceControllerTest {

    @Test
    public void fun01() {
        // 使用java 代码调用 js 实现加密解密。目的： 模拟前端对请求参数的加密和解密
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
        try {
            Object eval = nashorn.eval(String.valueOf("'hello world'.length()"));
            System.out.println(eval);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void fun02() {
        // 使用java 代码调用 js 实现加密解密。目的： 模拟前端对请求参数的加密和解密
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
        try {
//            String path = ClassLoader.getSystemClassLoader().getResource("js/crypto-js-4.0.0/core.js").getPath();

            String path = "D:\\ideaworkspace\\codecopy\\codecopy\\src\\main\\resources\\js\\crypto-js-4.0.0\\crypto-js.js";
            String path1 = "D:\\ideaworkspace\\codecopy\\codecopy\\src\\main\\resources\\js\\other.js";

            Object eval = nashorn.eval(Files.newBufferedReader(Paths.get(path)));
            Object eval1 = nashorn.eval(Files.newBufferedReader(Paths.get(path1)));

            Invocable in = (Invocable) nashorn;
//            Object o = nashorn.get("CryptoJS");
            Object o = in.invokeFunction("getAesString", "hello", "aaaa", "123456789");
            System.out.println(o);
            Object o1 = in.invokeFunction("getDAesString", "ybNhAzXC1FDKFbN/DIB2gw==", "aaaa", "123456789");
            System.out.println(o1);

//            Object o1 = in.invokeMethod(o, "MD5", "hello world");
//            System.out.println(o1);
//            Object toString = in.invokeMethod(o1, "toString", "16");
//            System.out.println(toString);




        } catch (ScriptException | IOException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


}