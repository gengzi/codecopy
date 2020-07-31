package fun.gengzi.codecopy.business.authentication.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import fun.gengzi.codecopy.business.authentication.service.SecurityInterfaceService;
import fun.gengzi.codecopy.utils.AESUtils;
import fun.gengzi.codecopy.utils.RSAUtils;
import org.springframework.stereotype.Service;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SecurityInterfaceServiceImpl implements SecurityInterfaceService {
    /**
     * 生成 RSA 密钥 和 公钥
     *
     * @return publickey 公钥  secretkey  密钥
     */
    @Override
    public Map<String, String> getRSASecretkeyAndPublickey() {
        return RSAUtils.generatekeyToMap();
    }

    /**
     * 生成 aes 密钥
     *
     * @return aes 密钥
     */
    @Override
    public String getAESkey() {
        return AESUtils.generatekey();
    }

    /**
     * 使用 js rsa 算法私钥，对内容进行加密
     *
     * @param content  数据
     * @param secrekey 私钥
     * @return 加密后的内容
     */
    @Override
    public Optional<String> encryptJSByClientSecrekeyStr(String content, String secrekey) {
        // 使用java 代码调用 js 实现加密解密。目的： 模拟前端对请求参数的加密和解密
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
        try {

            String cryptoPath = "D:\\ideaworkspace\\codecopy\\codecopy\\src\\main\\resources\\js\\crypto-js-4.0.0\\crypto-js.js";
            String functionPath = "D:\\ideaworkspace\\codecopy\\codecopy\\src\\main\\resources\\js\\other.js";
//            String cryptoPath = ClassLoader.getSystemClassLoader().getResource("js/crypto-js-4.0.0/crypto-js.js").getPath();
//            String functionPath = ClassLoader.getSystemClassLoader().getResource("js/other.js").getPath();
            nashorn.eval(Files.newBufferedReader(Paths.get(cryptoPath)));
            nashorn.eval(Files.newBufferedReader(Paths.get(functionPath)));
            Invocable in = (Invocable) nashorn;
            // 调用 js 的function 方法
            Object o = in.invokeFunction("getRSAString", content, secrekey);
            return Optional.of(o.toString());
        } catch (ScriptException | IOException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
