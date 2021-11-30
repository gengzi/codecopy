package fun.gengzi.codecopy.business.luckdraw.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import fun.gengzi.codecopy.business.luckdraw.entity.KafkaLuckdrawEntity;
import fun.gengzi.codecopy.business.luckdraw.service.KafkaService;
import fun.gengzi.codecopy.business.luckdraw.service.LuckdrawService;
import fun.gengzi.codecopy.dao.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Date;

/**
 * <h1>kafka service impl</h1>
 * <p>
 * <p>
 * 引入kafka，虽然优化了系统业务，但是增加了系统的不稳定性，mq一旦有什么问题，都会影响业务
 * 基本策略：
 * 如何保证mq的高可用
 * 如何保证消息不会丢失
 * 如何保证消息不会被重复消费
 * 抽奖业务不用保证 消息的有序性
 * 建议阅读
 * https://doocs.github.io/advanced-java/#/?id=%e4%ba%92%e8%81%94%e7%bd%91-java-%e5%b7%a5%e7%a8%8b%e5%b8%88%e8%bf%9b%e9%98%b6%e7%9f%a5%e8%af%86%e5%ae%8c%e5%85%a8%e6%89%ab%e7%9b%b2%c2%a9
 *
 * @author gengzi
 * @date 2020年9月23日17:03:39
 */
@Service
public class KafkaServiceImpl implements KafkaService {
    private Logger logger = LoggerFactory.getLogger(KafkaServiceImpl.class);

    @Value("${kafka.topic.my-topic}")
    private String myTopic;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private LuckdrawService luckdrawService;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 发送抽奖信息到kafka
     *
     * @param kafkaLuckdrawEntity
     */
    @Transactional("kafkaTransactionManager")
    @Override
    public void sendLuckdrawMsgInfo(KafkaLuckdrawEntity kafkaLuckdrawEntity) {
        logger.info("kafkaLuckdrawEntity:{}", kafkaLuckdrawEntity);
        ListenableFuture future = kafkaTemplate.send(myTopic, kafkaLuckdrawEntity);
        // 异步发送，并回调
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                // 在出现异常中，包含两种异常，一种是 不可重试的异常，一种是 可重试的异常
                logger.error("生产者发送消息：{} 失败，原因：{}", kafkaLuckdrawEntity.toString(), ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                logger.info("生产者成功发送消息到" + myTopic + "-> " + result.getProducerRecord().value().toString());
            }
        });
    }


    /**
     * 消费数据
     * @param data
     */
    @KafkaListener(topics = {"${kafka.topic.my-topic}"}, groupId = "group1")
    public void consumeMessage(String data) {
        logger.info("消费者消费的消息 -> {}", data);
        KafkaLuckdrawEntity kafkaLuckdrawEntity = JSONObject.parseObject(data, KafkaLuckdrawEntity.class);
        receiveLuckdrawMsgInfo(kafkaLuckdrawEntity);
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
            } else {
                // 校验当前消息的幂等性，保证消费消息不重复
                boolean flag = redisUtil.hasKey(idempotencyFiled);
                if (!flag) {
                    // 不存在，存入到redis
                    redisUtil.set(idempotencyFiled, "0", 300);
                    // 调用抽奖算法，依然走一遍校验流程
                    String activityId = kafkaLuckdrawEntity.getActivityId();
                    luckdrawService.mqLuckdraw(kafkaLuckdrawEntity);
                    redisUtil.set(idempotencyFiled, "1", 300);
                }
            }
        }
    }
}
