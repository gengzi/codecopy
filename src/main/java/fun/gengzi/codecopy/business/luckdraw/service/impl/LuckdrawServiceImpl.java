package fun.gengzi.codecopy.business.luckdraw.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import fun.gengzi.codecopy.business.luckdraw.constant.LuckdrawContants;
import fun.gengzi.codecopy.business.luckdraw.dao.IntergralDao;
import fun.gengzi.codecopy.business.luckdraw.dao.PrizeDao;
import fun.gengzi.codecopy.business.luckdraw.dao.SysUserDao;
import fun.gengzi.codecopy.business.luckdraw.entity.SysUser;
import fun.gengzi.codecopy.business.luckdraw.entity.SysUserDTO;
import fun.gengzi.codecopy.business.luckdraw.entity.TbIntegral;
import fun.gengzi.codecopy.business.luckdraw.entity.TbPrize;
import fun.gengzi.codecopy.business.luckdraw.service.LuckdrawService;
import fun.gengzi.codecopy.dao.RedisUtil;
import fun.gengzi.codecopy.exception.RrException;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    /**
     * 根据活动id，抽奖
     *
     * @param activityid 活动id
     * @return {@link TbPrize}  获得的奖品信息
     */
    @Override
    public TbPrize luckdraw(String activityid) {
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

            // 获得用户积分


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
        String integralKey = LuckdrawContants.INTEGRAL_PREFIX + activityid + LuckdrawContants.REDISKEYSEPARATOR + uid;
        boolean flag = redisUtil.set(integralKey, tbIntegral, LuckdrawContants.INVALIDTIME);
        if (!flag) {
            throw new RrException("保存用户活动积分信息失败");
        }
        return tbIntegral;
    }


}
