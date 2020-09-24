package fun.gengzi.codecopy.business.luckdraw.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import fun.gengzi.codecopy.business.luckdraw.entity.KafkaLuckdrawEntity;
import fun.gengzi.codecopy.business.luckdraw.service.KafkaService;
import fun.gengzi.codecopy.business.luckdraw.service.LuckdrawService;
import fun.gengzi.codecopy.dao.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <h1>kafka service impl</h1>
 *
 * @author gengzi
 * @date 2020年9月23日17:03:39
 */
@Service
public class KafkaServiceImpl implements KafkaService {


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LuckdrawService luckdrawService;

    /**
     * 发送抽奖信息到kafka
     *
     * @param kafkaLuckdrawEntity
     */
    @Override
    public void sendLuckdrawMsgInfo(KafkaLuckdrawEntity kafkaLuckdrawEntity) {
        // TODO 待实现
    }

    /**
     * 消费kafka中的抽奖信息
     * 为了保证幂等性，两种方式，一种是数据库设置唯一字段，如果可以插入成功，说明没有消费过该数据
     * 或者redis，如果找到相同的key，说明消费过，如果没有，插入key，消费数据
     *
     * @param kafkaLuckdrawEntity
     */
    @Override
    public void receiveLuckdrawMsgInfo(KafkaLuckdrawEntity kafkaLuckdrawEntity) {
        // 当时间延误一分钟以上，取消该抽奖操作
        String idempotencyFiled = kafkaLuckdrawEntity.getIdempotencyFiled();
        if (StringUtils.isNotBlank(idempotencyFiled)) {
            String[] idArr = idempotencyFiled.split(":");
            String currentTime = idArr[idArr.length - 1];
            Date oldDate = DateUtil.date(Long.parseLong(currentTime));
            Date currentDate = DateUtil.date(System.currentTimeMillis());
            long minute = DateUtil.between(oldDate, currentDate, DateUnit.MINUTE);
            if (minute >= 1) {
                return;
            }else{
                // 校验当前消息的幂等性，保证消费消息不重复
                boolean flag = redisUtil.hasKey(idempotencyFiled);
                if(!flag){
                    // 不存在，存入到redis
                    redisUtil.set(idempotencyFiled,"");
                    // 调用抽奖算法，依然走一遍校验流程
                    String activityId = kafkaLuckdrawEntity.getActivityId();
                    luckdrawService.luckdraw(activityId);
                }
            }
        }
    }
}
