package fun.gengzi.codecopy.utils.datamask;


import fun.gengzi.codecopy.business.authentication.controller.SecurityInterfaceController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

public class BeanDataMaskUtils {
    private static Logger logger = LoggerFactory.getLogger(BeanDataMaskUtils.class);

    public static Object maskObj(Object value) {
        if (null == value) {
            return null;
        }
        try {
            Class<?> aClass = value.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field field : declaredFields) {
                if (field.getType() == String.class) {
                    // 字符串处理
                    Sensitive sensitive = field.getAnnotation(Sensitive.class);
                    SensitiveType sensitiveType = getSensitiveType(field, sensitive);
                    boolean accessible = field.isAccessible();
                    // 修改访问控制权限
                    field.setAccessible(true);
                    Object o = field.get(value);
                    String newValue = mask((String) o, sensitive, sensitiveType);
                    field.set(value, newValue);
                    // 恢复访问控制权限
                    field.setAccessible(accessible);
                } else if (Collection.class.isAssignableFrom(field.getType())) {
                    // 集合处理
                    boolean accessible = field.isAccessible();
                    field.setAccessible(true);
                    Collection collection = (Collection) field.get(value);
                    if (collection != null) {
                        if (field.getGenericType() instanceof ParameterizedType) {
                            ParameterizedType pt = (ParameterizedType) field.getGenericType();
                            Type[] listActualTypeArguments = pt.getActualTypeArguments();
                            logger.info("类型:{}", listActualTypeArguments[listActualTypeArguments.length - 1]);
                            collection.stream().forEach(obj -> {
                                maskObj(obj);
                            });
                            // 恢复访问控制权限
                            field.setAccessible(accessible);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return value;
        }
        return value;
    }

    /**
     * 对字段进行mask
     *
     * @param value 值
     * @return 脱敏后的值
     */
    private static String mask(String value, Sensitive sensitive, SensitiveType type) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        if (sensitive == null) {
            if (type != null) {
                return DataMask.mask(value, type);
            } else {
                return value;
            }
        } else {
            return sensitive.type().getStrategy().mask(value, sensitive.keepChars());
        }
    }


    private static SensitiveType getSensitiveType(Field field, Sensitive sensitive) {
        SensitiveType type = null;
        if (sensitive == null) {
            type = null;
        } else {
            type = sensitive.type();
        }
        return type;
    }


    public enum SensitiveType {
        Name(new NameMask()),//中文名
        Phone(new PartMask(), 3),//电话
        IDCard(new PartMask(), 5, 2),//身份证号
        BankCard(new PartMask(), 4, 2),//银行卡号
        Address(new PartMask(), 9, 0),//地址
        Email(new EmailMask()),//电子邮件
        Captcha(new PartMask(), 1),//验证码
        Passport(new PartMask(), 2),//护照/军官证
        Account(new PartMask(), 1),//账号
        Password(new PartMask(), 0),//密码
        /**
         * 散列，这种掩码方式，用户可以手工计算Hash值来精确查询日志。
         */
        Hash(new HashMask()),

        Default(new PartMask(), 1, 0); //缺省,只显示第一个字符串

        private MaskStrategy strategy;
        private int[] params;

        SensitiveType(MaskStrategy strategy, int... params) {
            this.strategy = strategy;
            this.params = params;
        }

        public MaskStrategy getStrategy() {
            return strategy;
        }


        public int[] getParams() {
            return params;
        }
    }


}
