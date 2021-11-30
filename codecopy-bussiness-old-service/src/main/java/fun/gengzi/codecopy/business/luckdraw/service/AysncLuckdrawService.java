package fun.gengzi.codecopy.business.luckdraw.service;

import fun.gengzi.codecopy.business.luckdraw.entity.TbAwardee;

/**
 * <h1>抽奖服务的异步任务</h1>
 *
 * @author gengzi
 * @date 2020年9月11日10:37:15
 */
public interface AysncLuckdrawService {


    /**
     * 数据库扣除积分操作
     *
     * @param activityId   活动id
     * @param uid          用户id
     * @param intergralNum 扣除积分数目
     */
    void runDeductionIntergral(String activityId, String uid, Integer intergralNum);

    /**
     * 减库存
     *
     * @param activityId 活动id
     * @param prizeId    奖品id
     */
    void runDeductionPrizeNum(String activityId, Integer prizeId);


    /**
     * 记录获奖信息
     *
     * @param tbAwardee {@link TbAwardee} 获奖信息类
     */
    void runSaveAwardeeInfo(TbAwardee tbAwardee);


}
