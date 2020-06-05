package fun.gengzi.codecopy.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyPair;
import java.util.Optional;

/**
 * <h1>RSA 工具类</h1>
 *
 * @author gengzi
 * @date 2020年6月5日11:17:10
 */
public class RSAUtils {
    private static final Logger logger = LoggerFactory.getLogger(RSAUtils.class);
    // RSA
    private final static String ALGORITHM_RSA = "RSA";
    private final static int KEY_SIZE = 512;

    /**
     * 生成公钥和密钥
     *
     * @return aes key
     */
    public static void generatekey() {
        KeyPair pair = SecureUtil.generateKeyPair(ALGORITHM_RSA, KEY_SIZE);
        byte[] pri = pair.getPrivate().getEncoded();
        byte[] pub = pair.getPublic().getEncoded();
        String priStr = Base64.encode(pri);
        String pubStr = Base64.encode(pub);
        System.out.println("密钥：" + priStr);
        System.out.println("公钥：" + pubStr);
    }


    /**
     * 加密，再使用base64 转码
     *
     * @param content 需要加密内容
     * @param rsakey  密钥
     * @return 加密后的字符串
     */
    public static Optional<String> encrypt(String content, String rsakey) {
        try {
            RSA rsa = new RSA(rsakey, null);
            byte[] encrypt = rsa.encrypt(content, KeyType.PrivateKey);
            String encode = Base64.encode(encrypt);
            return Optional.ofNullable(encode);
        } catch (Exception e) {
            logger.error("RSA 加密失败！", e.getMessage());
        }
        return Optional.empty();
    }


    /**
     * base64转码后，解密
     *
     * @param content 需要解密的内容
     * @param rsakey  公钥
     * @return 解密后的字符串
     */
    public static Optional<String> decrypt(String content, String rsakey) {
        try {
            byte[] decode = Base64.decode(content);
            RSA rsa = new RSA(null, rsakey);
            byte[] infobyte = rsa.decrypt(decode, KeyType.PublicKey);
            String infostr = StrUtil.str(infobyte, CharsetUtil.CHARSET_UTF_8);
            Optional.ofNullable(infostr);
        } catch (Exception e) {
            logger.error("RSA 解密失败！", e.getMessage());
        }
        return Optional.empty();
    }


    public static void main(String[] args) {
        generatekey();
    }


}
