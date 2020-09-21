package fun.gengzi.codecopy.business.luckdraw.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <h1>抽奖枚举类</h1>
 *
 * @author gengzi
 * @date 2020年9月9日15:21:52
 */
@Getter
@AllArgsConstructor
public enum LuckdrawEnum {

    ERROR_DEFAULT(1000, "LUCKDRAW_DEFAULT", "服务异常，请稍后再试哟！"),
    ERROR_SERVICE_LIMIT(1000, "SERVICE_LIMIT", "请稍等，您访问的太快了！"),
    ERROR_PAGE_VALIDCODE(1002, "ERROR_PAGE_VALIDCODE", "随机验证码错误，请重试！"),
    ERROR_VALIDCODE(1001, "ERROR_VALIDCODE", "短信验证码错误，请重试！"),
    ERROR_USERINFO_NOEXISTS(2001, "USERINFO_NOEXISTS", "请重新登录，再抽奖哦！"),
    ERROR_ACTIVITY_NOEXISTS(3001, "ACTIVITY_NOEXISTS", "该活动过期了哦！"),
    ERROR_INTEGRAL_LITTLE(5001, "INTEGRAL_LITTLE", "抱歉，您的积分不足，无法抽奖哦！");

    private Integer code;
    private String codeStr;
    private String msg;


}
