package fun.gengzi.codecopy.business.luckdraw.constant;

/**
 * <h1>抽奖全局常量类</h1>
 *
 * @author gengzi
 * @date 2020年9月9日14:43:13
 */
public class LuckdrawContants {

    // userinfo redis 前缀
    public static final String USERPREFIX = "userinfo:";
    // 奖品信息 redis 前缀
    public static final String INTEGRAL_PREFIX = "integral:";
    // reids key 分隔符
    public static final String REDISKEYSEPARATOR = ":";

    // 用户信息redis失效时间 默认 半小时
    public static final Long INVALIDTIME = 3600L;


}
