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
    // 积分对象信息 redis 前缀
    public static final String INTEGRAL_PREFIX = "integral:";
    // 仅积分信息 redis 前缀
    public static final String ONLEY_INTEGRAL_PREFIX = "onley_integral:";
    // 仅奖品信息 redis 前缀
    public static final String ONLEY_PRIZE_PREFIX = "onley_prize:";
    // 页面随机验证码 redis 前缀
    public static final String VALIDCODE_PREFIX = "validcode:";
    // reids key 分隔符
    public static final String REDISKEYSEPARATOR = ":";

    // 用户信息redis失效时间 默认 半小时
    public static final Long INVALIDTIME = 3600L;
    // 用户活动积分key
    public static final String INTEGRALKEY = LuckdrawContants.INTEGRAL_PREFIX + "%s" + LuckdrawContants.REDISKEYSEPARATOR + "%s";
    // session 用户信息key
    public static final String USERINFOKEY = LuckdrawContants.USERPREFIX + "%s";
    // 利用redis 原子特性，仅存储 活动用户的积分
    public static final String ONLEYINTEGRALKEY = LuckdrawContants.ONLEY_INTEGRAL_PREFIX + "%s" + LuckdrawContants.REDISKEYSEPARATOR + "%s";
    // 利用redis 原子特性，仅存储 奖品的数目 活动id:奖品id
    public static final String ONLEYPRIZEKEY = LuckdrawContants.ONLEY_PRIZE_PREFIX + "%s" + LuckdrawContants.REDISKEYSEPARATOR + "%s";
    // 页面随机验证码key
    public static final String VALIDCODEKEY = LuckdrawContants.VALIDCODE_PREFIX + "%s";


}
