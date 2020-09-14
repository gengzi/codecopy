package fun.gengzi.codecopy.business.luckdraw.service.impl;

import cn.hutool.core.util.IdUtil;
import fun.gengzi.codecopy.business.luckdraw.algorithm.LuckdrawAlgorithlm;
import fun.gengzi.codecopy.business.luckdraw.constant.LuckdrawContants;
import fun.gengzi.codecopy.business.luckdraw.dao.AwardeeDao;
import fun.gengzi.codecopy.business.luckdraw.dao.IntergralDao;
import fun.gengzi.codecopy.business.luckdraw.dao.PrizeDao;
import fun.gengzi.codecopy.business.luckdraw.dao.SysUserDao;
import fun.gengzi.codecopy.business.luckdraw.entity.*;
import fun.gengzi.codecopy.business.luckdraw.service.AysncLuckdrawService;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
    private AwardeeDao awardeeDao;

    @Autowired
    @Qualifier("DefaultLuckdrawAlgorithlm")
    private LuckdrawAlgorithlm luckdrawAlgorithlm;

    @Autowired
    private AysncLuckdrawService aysncLuckdrawService;

    /**
     * 根据活动id，抽奖
     * <p>
     * 根据活动id ，获取奖品信息
     * 根据token ，获取用户信息
     * 冻结用户积分
     * 调用抽奖算法，开始抽奖
     * 冻结奖品信息
     * 扣除用户积分
     * 减少奖品数目
     *
     * @param activityid 活动id
     * @param token      用户的token
     * @return {@link TbPrize}  获得的奖品信息
     */
    @Transactional
    @Override
    public TbPrize luckdraw(String activityid, String token) {

        logger.info("抽奖开始");
        List<TbPrize> tbPrizes = prizeDao.findByActivityidOrderByProbability(activityid);
        if (CollectionUtils.isNotEmpty(tbPrizes)) {
            logger.info("校验奖品概率");
            boolean flag = this.checkPrizeProbability(tbPrizes);
            logger.info("校验奖品概率不超过1：{}", flag);
            if (flag) {
                // 从缓存中获得用户积分
                final String userinfokey = String.format(LuckdrawContants.USERINFOKEY, token);
                SysUser sysUser = (SysUser) redisUtil.get(userinfokey);
                final String onleyintegralkey = String.format(LuckdrawContants.ONLEYINTEGRALKEY, activityid, sysUser.getUid());
                long integral = redisUtil.incr(onleyintegralkey, 0);
                // 冻结积分信息
                logger.info("校验用户积分是否足够，现在积分：{}", integral);
                if ((integral - 30) >= 0) {
                    logger.info("积分足够，可以抽奖");
                    // 允许抽奖
                    // 扣积分
                    redisUtil.decr(onleyintegralkey, 30);
                    // db扣积分
                    intergralDao.deductionIntergral(activityid, sysUser.getUid(), 30);
                    // 抽奖返回奖项
                    Integer algorithlm = startLuckdraw(activityid, tbPrizes);
                    // 判断几等奖，扣奖品表的库存
                    TbPrize tbPrize = tbPrizes.get(algorithlm);
                    logger.info("jaingpin 信息：{}", tbPrize);

                    // 预扣缓存库存
                    String onleyprizekey = String.format(LuckdrawContants.ONLEYPRIZEKEY, activityid, tbPrize.getId());
                    long decr = redisUtil.decr(onleyprizekey, 1);
                    if (decr < 0) {
                        logger.info("未抽到！");
                    }

                    // 减真实库存
                    Integer integer = prizeDao.deductionPrizeNum(activityid, tbPrize.getId());
                    if (integer <= 0) {
                        return null;
                    }
                    // 减库存失败，就不需要记录发奖信息
                    // 记录发奖信息
                    logger.info("记录发奖信息开始");
                    TbAwardee tbAwardee = new TbAwardee();
                    tbAwardee.setActivityId(activityid);
                    tbAwardee.setAwardeeName(sysUser.getUname());
                    tbAwardee.setCreatetime(new Date());
                    tbAwardee.setPrizeId(tbPrize.getId());
                    tbAwardee.setPrizeName(tbPrize.getPrizeName());
                    // 获奖数目 1
                    tbAwardee.setPrizeNum(1);
                    // 是否发放，奖品缺一个奖品类型字段
                    short isGrant = 1;
                    tbAwardee.setIsGrant(isGrant);
                    awardeeDao.save(tbAwardee);
                    logger.info("记录发奖信息结束");

                    // TODO 测试事物回滚，期望事物回滚后，返回给用户 抽奖异常，请稍后再试
                    try {
                        logger.warn("测试事物回滚，期望事物回滚后，返回给用户 抽奖异常，请稍后再试");
                        int i = 1 / 0;
                    } catch (Exception e) {
                        throw new RuntimeException("异常了");
                    }

                    return tbPrize;
                } else {
                    logger.info("积分不足哦！");
                }

            } else {
                logger.info("奖品信息概率有误");
            }
        }
        return null;
    }

    /**
     * 开始抽奖
     *
     * @param activityid 活动id
     * @param tbPrizes   奖品信息
     * @return 奖项
     */
    private Integer startLuckdraw(String activityid, List<TbPrize> tbPrizes) {
        final LuckdrawAlgorithlmEntity luckdrawAlgorithlmEntity = new LuckdrawAlgorithlmEntity();
        luckdrawAlgorithlmEntity.setActivityId(activityid);
        // 组装概率
        List<Double> collect = tbPrizes.stream().map(tbPrize -> tbPrize.getProbability())
                .sorted(Comparator.comparing(probability -> probability))
                .collect(Collectors.toList());

        luckdrawAlgorithlmEntity.setProbabilityList(collect);
        return luckdrawAlgorithlm.algorithlm(luckdrawAlgorithlmEntity);
    }

    /**
     * 检测当前活动的奖品概率不能超过1
     *
     * @param tbPrizes 奖品信息
     * @return true 未超过 false 超过
     */
    private boolean checkPrizeProbability(List<TbPrize> tbPrizes) {
        AtomicReference<Double> count = new AtomicReference<>(0.00);
        tbPrizes.forEach(tbPrize -> {
                    Double probability = tbPrize.getProbability();
                    count.updateAndGet(v -> v + probability);
                }
        );
        boolean flag = false;
        // 计算奖品总概率不能超过1
        if (count.get().compareTo(1.00) < 0) {
            flag = true;
        }
        return flag;
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
        if (redisUtil.hasKey(integralKey) || redisUtil.hasKey(onleyintegralkey)) {
            // 移除缓存
            redisUtil.del(integralKey, onleyintegralkey);
        }
        // 将该用户活动积分对象存入redis
        boolean flag = redisUtil.set(integralKey, tbIntegral);
        if (!flag) {
            throw new RrException("保存用户活动积分信息失败");
        }
        // 将该活动用户对应积分，存入redis
        redisUtil.incr(onleyintegralkey, tbIntegral.getIntegral());
        return tbIntegral;
    }


}
