package fun.gengzi.codecopy.business.luckdraw.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>验证用户信息vo</h1>
 *
 * @author gengzi
 * @date 2020年9月9日15:54:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationVo {
    // 手机号
    private String phone;
    // 验证码
    private String phoneValidCode;
    // 页面验证码
    private String validCode;
    // 页面验证码的code
    private String code;

}
