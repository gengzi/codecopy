package fun.gengzi.codecopy.business.authentication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求参数封装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestParamEntity {
    // 签名
    private String sign;
    // 随机码，防止签名不会变化
    private String reqNum;
}
