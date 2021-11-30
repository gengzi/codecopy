package fun.gengzi.codecopy.utils.datamask;

import com.alibaba.fastjson.JSON;

/**
 * 脱敏工具类
 */
public class DataMask {

    /**
     * 设置Hash脱敏的salt
     */
    public static void setHashSalt(String salt) {
        HashMask.setSalt(salt);
    }

    /**
     * 默认的脱敏方式，只显示第一个字符串
     *
     * @param source 源字符串
     * @return 脱敏后的字符串
     */
    public static String mask(String source) {
        try {
            return mask(source, BeanDataMaskUtils.SensitiveType.Default);
        } catch (Exception e) {
            return source;
        }
    }

    /**
     * 根据类型来脱敏
     *
     * @param source 源字符串
     * @param type   类型
     * @return 掩码后的字符串
     */
    public static String mask(String source, BeanDataMaskUtils.SensitiveType type) {
        try {
            return type.getStrategy().mask(source, type.getParams());
        } catch (Exception e) {
            return source;
        }
    }

    /**
     * 自定义脱敏策略来脱敏
     *
     * @param source   源字符串
     * @param strategy 策略
     * @param param    策略参数
     * @return 脱敏后的字符串
     */
    public static String mask(String source, MaskStrategy strategy, int... param) {
        try {
            return strategy.mask(source, param);
        } catch (Exception e) {
            return source;
        }
    }
}