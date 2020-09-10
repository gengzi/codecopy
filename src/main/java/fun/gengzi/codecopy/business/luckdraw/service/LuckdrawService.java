package fun.gengzi.codecopy.business.luckdraw.service;


import fun.gengzi.codecopy.business.luckdraw.entity.SysUser;
import fun.gengzi.codecopy.business.luckdraw.entity.SysUserDTO;
import fun.gengzi.codecopy.business.luckdraw.entity.TbIntegral;
import fun.gengzi.codecopy.business.luckdraw.entity.TbPrize;

import java.util.List;


/**
 * <h1>抽奖service层</h1>
 */
public interface LuckdrawService {

    /**
     * 根据活动id，抽奖
     *
     * @param activityid 活动id
     * @return {@link TbPrize}  获得的奖品信息
     */
    TbPrize luckdraw(String activityid);


    /**
     * 根据手机号，获取用户信息
     *
     * @param phone 手机号
     * @return {@link SysUser} 用户信息
     */
    SysUserDTO getUserInfoByPhoneNum(String phone);


    /**
     * 根据活动id
     * @param activityid  活动id
     * @param uid  用户id
     * @return {@link TbIntegral} 用户活动积分信息
     */
    TbIntegral getIntegralInfo(String activityid,String uid);

}
