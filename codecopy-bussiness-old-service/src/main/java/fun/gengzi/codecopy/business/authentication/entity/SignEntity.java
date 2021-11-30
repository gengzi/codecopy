package fun.gengzi.codecopy.business.authentication.entity;

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
public class SignEntity {
    // encryptkey 使用服务端公钥加密的 aes key
    private String encryptkey;
    // data 真实数据 + 签名
    private String data;
}
