package fun.gengzi.codecopy.business.luckdraw.service;

import fun.gengzi.codecopy.business.luckdraw.entity.KafkaLuckdrawEntity;

/**
 * <h1>kafka service</h1>
 *
 * @author gengzi
 * @date 2020年9月23日16:58:22
 */
public interface KafkaService {

    /**
     * 发送抽奖信息到kafka
     * @param kafkaLuckdrawEntity
     */
    void sendLuckdrawMsgInfo(KafkaLuckdrawEntity kafkaLuckdrawEntity);

    /**
     * 消费kafka中的抽奖信息
     * @param kafkaLuckdrawEntity
     */
    void receiveLuckdrawMsgInfo(KafkaLuckdrawEntity kafkaLuckdrawEntity);


}
