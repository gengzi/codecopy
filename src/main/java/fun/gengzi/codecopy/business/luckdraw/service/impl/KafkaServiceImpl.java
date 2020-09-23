package fun.gengzi.codecopy.business.luckdraw.service.impl;

import fun.gengzi.codecopy.business.luckdraw.entity.KafkaLuckdrawEntity;
import fun.gengzi.codecopy.business.luckdraw.service.KafkaService;
import org.springframework.stereotype.Service;

/**
 * <h1>kafka service impl</h1>
 *
 * @author gengzi
 * @date 2020年9月23日17:03:39
 */
@Service
public class KafkaServiceImpl implements KafkaService {
    /**
     * 发送抽奖信息到kafka
     *
     * @param kafkaLuckdrawEntity
     */
    @Override
    public void sendLuckdrawMsgInfo(KafkaLuckdrawEntity kafkaLuckdrawEntity) {
        // TODO 待实现
    }
}
