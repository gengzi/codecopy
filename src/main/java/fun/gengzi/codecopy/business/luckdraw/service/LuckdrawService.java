package fun.gengzi.codecopy.business.luckdraw.service;


import fun.gengzi.codecopy.business.luckdraw.entity.*;

import java.util.List;


/**
 * <h1>抽奖service层</h1>
 */
public interface LuckdrawService {

    /**
     * 根据活动id，抽奖
     *
     * @param activityid 活动id
     * @param token      用户的token 信息
     * @return {@link TbPrize}  获得的奖品信息
     */
    TbPrize luckdraw(String activityid, String token);

    /**
     * 根据活动id，抽奖 测试方法
     *
     * @param activityid 活动id
     * @param uid        用户id
     * @return {@link TbPrize}  获得的奖品信息
     */
    TbPrize luckdrawTest(String activityid, String uid);


    /**
     * 根据手机号，获取用户信息
     *
     * @param phone 手机号
     * @return {@link SysUser} 用户信息
     */
    SysUserDTO getUserInfoByPhoneNum(String phone);


    /**
     * 根据活动id
     *
     * @param activityid 活动id
     * @param uid        用户id
     * @return {@link TbIntegral} 用户活动积分信息
     */
    TbIntegral getIntegralInfo(String activityid, String uid);

    /**
     * 根据活动id,查询奖品信息
     *
     * @param activityid 活动id
     * @return {@link List<TbPrize>} 用户活动积分信息
     */
    List<TbPrize> getPrizeInfo(String activityid);

    /**
     * 根据活动id,查询当前最新的获奖人信息
     *
     * @param activityid 活动id
     * @return {@link  List<AwardeeVo>} 用户活动积分信息
     */
    List<AwardeeVo> getAwardeeInfo(String activityid);

    /**
     * 根据活动id和用户id，查询我的奖品信息
     *
     * @param activityid 活动id
     * @param uid        用户id
     * @return {@link  List<AwardeeVo>} 用户活动积分信息
     */
    List<MyAwardeeVo> getMyPrizeInfo(String activityid, String uid);


}
