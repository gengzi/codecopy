package fun.gengzi.codecopy.business.authentication.service.impl;

import fun.gengzi.codecopy.business.authentication.service.SecurityInterfaceService;
import fun.gengzi.codecopy.utils.AESUtils;
import fun.gengzi.codecopy.utils.RSAUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

@Service
public class SecurityInterfaceServiceImpl implements SecurityInterfaceService {

    private Logger logger = LoggerFactory.getLogger(SecurityInterfaceServiceImpl.class);

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
     * <p>
     * TODO  对于 ClassLoader.getSystemClassLoader().getResource 中的路径分隔符 必须写成  /  如果写成 \\ 或者 \ 路径中将会出现 %5C
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
//            String cryptoPath = "D:\\ideaworkspace\\codecopy\\codecopy\\src\\main\\resources\\js\\crypto-js-4.0.0\\crypto-js.js";
//            String functionPath = "D:\\ideaworkspace\\codecopy\\codecopy\\src\\main\\resources\\js\\other.js";
            String basePath = ClassLoader.getSystemClassLoader().getResource("js/base.js").getPath();
            String cryptoPath = ClassLoader.getSystemClassLoader().getResource("js/jsencrypt/jsencrypt.js").getPath();
            String functionPath = ClassLoader.getSystemClassLoader().getResource("js/RSAUtils.js").getPath();

            logger.info("cryptoPath rsa加密js路径 {}", cryptoPath);
            logger.info("functionPath 实际执行加密方法的js路径 {}", functionPath);
            nashorn.eval(Files.newBufferedReader(Paths.get(basePath.substring(1))));
            nashorn.eval(Files.newBufferedReader(Paths.get(cryptoPath.substring(1))));
            nashorn.eval(Files.newBufferedReader(Paths.get(functionPath.substring(1))));

            Invocable in = (Invocable) nashorn;
            // 调用 js 的function 方法

            content = "hello world";
            secrekey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALbcd" +
                    "LKOtTKOjalffv/LLLOqfyh8Ep4XHjvOivMU3Nb1N0puG4+" +
                    "NTrXBS8GDczgsZ+7J6D7FTcH8JInMKpz85LMCAwEAAQ==";
//            https://www.cnblogs.com/wsss/p/11516318.html
            Object o = in.invokeFunction("encryptRSAByPublicKey", content, secrekey);
            return Optional.of(o.toString());
        } catch (ScriptException | IOException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
