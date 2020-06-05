package fun.gengzi.codecopy.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import fun.gengzi.codecopy.business.payment.controller.PaymentActionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * <h1>AES 工具类</h1>
 *
 * @author gengzi
 * @date 2020年6月5日09:03:57
 */
public class AESUtils {

    private static final Logger logger = LoggerFactory.getLogger(AESUtils.class);

    private static final char HexCharArr[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final String HexStr = "0123456789abcdef";

    /**
     * 字节数组转16进制字符串
     *
     * @param btArr
     * @return
     */
    public static String byteArrToHex(byte[] btArr) {
        char strArr[] = new char[btArr.length * 2];
        int i = 0;
        for (byte bt : btArr) {
            strArr[i++] = HexCharArr[bt >>> 4 & 0xf];
            strArr[i++] = HexCharArr[bt & 0xf];
        }
        return new String(strArr);
    }

    /**
     * 16进制字符串转字节数组
     *
     * @param hexStr
     * @return
     */
    public static byte[] hexToByteArr(final String hexStr) {
        char[] charArr = hexStr.toCharArray();
        byte btArr[] = new byte[charArr.length / 2];
        int index = 0;
        for (int i = 0; i < charArr.length; i++) {
            int highBit = HexStr.indexOf(charArr[i]);
            int lowBit = HexStr.indexOf(charArr[++i]);
            btArr[index] = (byte) (highBit << 4 | lowBit);
            index++;
        }
        return btArr;
    }

    /**
     * 随机生成AES 密钥
     *
     * @return aes key
     */
    public static String generatekey() {
        // 随机生成密钥
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        return byteArrToHex(key);
    }

    /**
     * 加密，再使用base64 转码
     *
     * @param content 需要加密内容
     * @param aeskey  密钥
     * @return 加密后的字符串
     */
    public static Optional<String> encrypt(String content, String aeskey) {
        try {
            byte[] key = hexToByteArr(aeskey);
            AES aes = SecureUtil.aes(key);
            byte[] encrypt = aes.encrypt(content);
            String encode = Base64.encode(encrypt);
            return Optional.ofNullable(encode);
        } catch (Exception e) {
            logger.error("AES 加密失败！", e.getMessage());
        }
        return Optional.empty();
    }


    /**
     * base64转码后，解密
     *
     * @param content 需要解密的内容
     * @param aeskey  密钥
     * @return 解密后的字符串
     */
    public static Optional<String> decrypt(String content, String aeskey) {
        try {
            byte[] key = hexToByteArr(aeskey);
            AES aes = SecureUtil.aes(key);
            byte[] decode = Base64.decode(content);
            String decryptStr = aes.decryptStr(decode);
            return Optional.ofNullable(decryptStr);
        } catch (Exception e) {
            logger.error("AES 解密失败！", e.getMessage());
        }
        return Optional.empty();
    }


    public static void main(String[] args) {
        String generatekey = generatekey();
        System.out.println(generatekey);
    }


}
