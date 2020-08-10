package fun.gengzi.codecopy.business.authentication.controller;

import fun.gengzi.codecopy.utils.RSAUtils;
import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

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

    // rsa 密钥
    private static final String secretkey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAttx0so61Mo6NqV9+/8sss6p/KHwSnhceO86K8xTc1vU3Sm4bj41OtcFLwYNzOCxn7snoPsVNwfwkicwqnPzkswIDAQABAkBGw9Xda+Cvaf9kdnJdZzErbmW7Mxi5WVT37BxVqdM01BTjudKSADlLn53fEeWl7pmfMkMuXZ7uPNdqmLWVLMNxAiEA6LXvDTKtEZNyTvjXs4nDJweiIT9kZtZmYD3hVcQueJUCIQDJKV+PcdKehVw8U+hdeE4/NZDFCHRzaGM4Zs5YRRbuJwIgSG0fSn9EKB04zWVbVNCCgWo5xplBOVRvJnL758KYKAUCIDdpmzZDb3ZVXCwOHRMqYbuNwNxV0OY9mh9eSncMSR2/AiEApSModT03Kr+nHxhgzAyOvzLcKE0IPMJ+ny3mjdyBjWc=";


    @Test
    public void fun03() {
        Optional<String> sp = RSAUtils.decryptByPublic("d/8TgJb/+G9VwBKNJVphRH4MnlCnPRX0Iqz7f7MysxCaFMvTnOn8wOWmzWfuCfEeRmkSIexBbzHcTS30UbRB4g==", secretkey);

        System.out.println(sp.orElse(""));

        if (sp.orElse("").contains("1597037902899" + "\t" + "5XP86"+ "\n")) {
            String s = sp.get();
            String[] split = s.split("\n");
            String ss = split[split.length-1];
            System.out.println(ss);
        }

    }


}