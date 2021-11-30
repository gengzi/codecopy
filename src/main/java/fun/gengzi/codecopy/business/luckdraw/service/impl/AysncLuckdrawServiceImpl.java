package fun.gengzi.codecopy.business.luckdraw.service.impl;

import fun.gengzi.codecopy.business.luckdraw.dao.IntergralDao;
import fun.gengzi.codecopy.business.luckdraw.dao.PrizeDao;
import fun.gengzi.codecopy.business.luckdraw.entity.TbAwardee;
import fun.gengzi.codecopy.business.luckdraw.service.AysncLuckdrawService;
import fun.gengzi.codecopy.exception.RrException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * <h1>抽奖服务的异步任务</h1>
 */
@Service
public class AysncLuckdrawServiceImpl implements AysncLuckdrawService {

    private Logger logger = LoggerFactory.getLogger(AysncLuckdrawServiceImpl.class);

    @Autowired
    private IntergralDao intergralDao;
    @Autowired
    private PrizeDao prizeDao;

    /**
     * 数据库扣除积分操作
     *
     * @param activityId   活动id
     * @param uid          用户id
     * @param intergralNum 扣除积分数目
     */
    @Async("luckdrawIntergralDaoThreadPool")
    @Override
    public void runDeductionIntergral(String activityId, String uid, Integer intergralNum) {
        logger.info("扣除积分开始");
        if (StringUtils.isAnyBlank(activityId, uid, String.valueOf(intergralNum))) {
            throw new RrException("失败，参数缺失");
        }
        intergralDao.deductionIntergral(activityId, uid, intergralNum);
        logger.info("扣除积分开始");
    }

    /**
     * 减库存
     *
     * @param activityId 活动id
     * @param prizeId    奖品id
     */
    @Async("luckdrawPrizeDaoThreadPool")
    @Override
    public void runDeductionPrizeNum(String activityId, Integer prizeId) {
        logger.info("减库存开始");
        if (StringUtils.isAnyBlank(activityId, String.valueOf(prizeId))) {
            throw new RrException("失败，参数缺失");
        }
        prizeDao.deductionPrizeNum(activityId, prizeId);
        logger.info("减库存结束");
    }

    /**
     * 记录获奖信息
     *
     * @param tbAwardee {@link TbAwardee} 获奖信息类
     */
    @Override
    public void runSaveAwardeeInfo(TbAwardee tbAwardee) {
        logger.info("暂时不使用此方法");
    }
}
