package fun.gengzi.codecopy.business.authentication.entity;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

/**
 * 请求参数封装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParamFieldEntity {
    // 签名
    private String sign;
    // 随机码，防止签名不会变化
    private String reqNum;
    // 电话号
    private String phoneNum;
    // 图片路径
    private String imageUrl;
    // 编码
    private String code;


    /**
     * 字段转换
     *
     * @param paramFieldEntity
     * @return
     */
    public static String toHttpGetParam(ParamFieldEntity paramFieldEntity) {
        StringBuilder fieldBuilder = new StringBuilder();
        Class<?> aClass = paramFieldEntity.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(
                (field) -> {
                    try {
                        Method method = aClass.getMethod("get" + StringUtils.capitalize(field.getName()));
                        Object invoke = method.invoke(paramFieldEntity);
                        if (invoke != null) {
                            fieldBuilder.append(field.getName() + "=" + invoke + "&");
                        }
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
        );
        return fieldBuilder.toString();
    }

//    public static void main(String[] args) {
//        final ParamFieldEntity paramFieldEntity = new ParamFieldEntity();
//        paramFieldEntity.setCode("1");
//        paramFieldEntity.setImageUrl("/img/1.png");
//        paramFieldEntity.setPhoneNum("12135");
//        paramFieldEntity.setReqNum(String.valueOf(new Random().nextInt()));
//        String s = new ParamFieldEntity().toHttpGetParam(paramFieldEntity);
//        System.out.println(s);
//    }

}
