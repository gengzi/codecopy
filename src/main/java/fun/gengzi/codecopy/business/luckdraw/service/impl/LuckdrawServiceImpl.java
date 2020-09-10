package fun.gengzi.codecopy.business.luckdraw.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import fun.gengzi.codecopy.business.luckdraw.algorithm.LuckdrawAlgorithlm;
import fun.gengzi.codecopy.business.luckdraw.constant.LuckdrawContants;
import fun.gengzi.codecopy.business.luckdraw.dao.IntergralDao;
import fun.gengzi.codecopy.business.luckdraw.dao.PrizeDao;
import fun.gengzi.codecopy.business.luckdraw.dao.SysUserDao;
import fun.gengzi.codecopy.business.luckdraw.entity.*;
import fun.gengzi.codecopy.business.luckdraw.service.LuckdrawService;
import fun.gengzi.codecopy.dao.RedisUtil;
import fun.gengzi.codecopy.exception.RrException;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>抽奖服务</h1>
 */
@Service
public class LuckdrawServiceImpl implements LuckdrawService {
    private Logger logger = LoggerFactory.getLogger(LuckdrawServiceImpl.class);

    @Autowired
    private PrizeDao prizeDao;

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IntergralDao intergralDao;

    @Autowired
    @Qualifier("DefaultLuckdrawAlgorithlm")
    private LuckdrawAlgorithlm luckdrawAlgorithlm;

    /**
     * 根据活动id，抽奖
     *
     * @param activityid 活动id
     * @param token      用户的token
     * @return {@link TbPrize}  获得的奖品信息
     */
    @Override
    public TbPrize luckdraw(String activityid, String token) {
        // 根据活动id ，获取奖品信息
        // 根据token ，获取用户信息
        // 冻结用户积分
        // 调用抽奖算法，开始抽奖
        // 冻结奖品信息
        // 扣除用户积分
        // 减少奖品数目

        List<TbPrize> tbPrizes = prizeDao.findByActivityid(activityid);
        if (CollectionUtils.isNotEmpty(tbPrizes)) {
            BigDecimal count = new BigDecimal(0);
            tbPrizes.forEach(tbPrize -> {
                        BigDecimal probability = tbPrize.getProbability();
                        count.add(probability);
                    }
            );
            boolean flag = false;
            // 计算奖品总概率不能超过1
            if (count.compareTo(new BigDecimal(1)) < 0) {
                flag = true;
            }
            // 从缓存中获得用户积分
            final String userinfokey = String.format(LuckdrawContants.USERINFOKEY, token);
            SysUser sysUser = (SysUser) redisUtil.get(userinfokey);
            final String integralkey = String.format(LuckdrawContants.INTEGRALKEY, activityid, sysUser.getUid());
            TbIntegral tbIntegral = (TbIntegral) redisUtil.get(integralkey);
            // 冻结积分信息
            // 获取redis分布式锁，锁定redis 中存储的该用户积分数据
            Integer integral = tbIntegral.getIntegral();
            if ((integral - 30) >= 0) {
                // 允许抽奖
                // 扣积分
                String onleyintegralkey = String.format(LuckdrawContants.ONLEYINTEGRALKEY, activityid, sysUser.getUid());
                redisUtil.decr(onleyintegralkey,30);
                final LuckdrawAlgorithlmEntity luckdrawAlgorithlmEntity = new LuckdrawAlgorithlmEntity();
                luckdrawAlgorithlmEntity.setActivityId(activityid);
                ArrayList<Double> probabilityList = new ArrayList<>(10);
                probabilityList.add(0.0001);
                probabilityList.add(0.01);
                probabilityList.add(0.1);
                probabilityList.add(0.2);
                probabilityList.add(0.3);
                probabilityList.add(0.3);
                luckdrawAlgorithlmEntity.setProbabilityList(probabilityList);
                Integer algorithlm = luckdrawAlgorithlm.algorithlm(luckdrawAlgorithlmEntity);
                // 判断几等奖，异步扣奖品表的库存


            }

        }


        return null;
    }

    /**
     * 根据手机号，获取用户信息
     *
     * @param phone 手机号
     * @return {@link SysUser} 用户信息
     */
    @Override
    public SysUserDTO getUserInfoByPhoneNum(String phone) {
        SysUser sysUser = sysUserDao.findByPhone(phone);
        if (sysUser == null) {
            return null;
        }
        String token = IdUtil.randomUUID();
        String userInfokey = LuckdrawContants.USERPREFIX + token;
        SysUserDTO sysUserDTO = new SysUserDTO();
        BeanUtils.copyProperties(sysUser, sysUserDTO); //使用更新对象的非空值去覆盖待更新对象
        sysUserDTO.setToken(token);
        boolean flag = redisUtil.set(userInfokey, sysUserDTO, LuckdrawContants.INVALIDTIME);
        if (!flag) {
            throw new RrException("保存用户信息失败");
        }
        return sysUserDTO;
    }

    /**
     * 根据活动id
     *
     * @param activityid 活动id
     * @param uid        用户id
     * @return {@link TbIntegral} 用户活动积分信息
     */
    @Override
    public TbIntegral getIntegralInfo(String activityid, String uid) {
        TbIntegral tbIntegral = intergralDao.findFirstByActivityidAndUid(activityid, uid);
        if (tbIntegral == null) {
            return null;
        }
        String integralKey = LuckdrawContants.INTEGRAL_PREFIX + activityid + LuckdrawContants.REDISKEYSEPARATOR + uid;
        String onleyintegralkey = String.format(LuckdrawContants.ONLEYINTEGRALKEY, activityid, uid);
        if(redisUtil.hasKey(integralKey) || redisUtil.hasKey(onleyintegralkey)){
            // 移除缓存
            redisUtil.del(integralKey,onleyintegralkey);
        }
        // 将该用户活动积分对象存入redis
        boolean flag = redisUtil.set(integralKey, tbIntegral, LuckdrawContants.INVALIDTIME);
        if (!flag) {
            throw new RrException("保存用户活动积分信息失败");
        }
        // 将该活动用户对应积分，存入redis
        redisUtil.incr(onleyintegralkey, tbIntegral.getIntegral());
        return tbIntegral;
    }


}
