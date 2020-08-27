package fun.gengzi.codecopy.utils.datamask;


import java.lang.reflect.Field;

public class BeanDataMaskUtils {


    public static Object maskObj(Object value){
        if (null == value) {
            return null;
        }
        try {
            Class<?> aClass = value.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field field:declaredFields) {
                if (field.getType() == String.class) {
                    Sensitive sensitive = field.getAnnotation(Sensitive.class);
                    SensitiveType sensitiveType = getSensitiveType(field, sensitive);
                    boolean accessible = field.isAccessible();
                    // 修改访问控制权限
                    field.setAccessible(true);
                    Object o = field.get(value);
                    String newValue = mask((String) o, sensitive, sensitiveType);
                    field.set(value,newValue);
                    // 恢复访问控制权限
                    field.setAccessible(accessible);
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
            type = SensitiveType.Default;
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
