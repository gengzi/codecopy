package fun.gengzi.codecopy.business.luckdraw.service.impl;

import com.alibaba.fastjson.JSONObject;
import fun.gengzi.codecopy.business.luckdraw.entity.KafkaLuckdrawEntity;
import org.junit.Test;

public class KafkaServiceImplTest {


    @Test
    public void fun01() {
        String value = "{\"sysUser\":{\"uid\":\"3\",\"uname\":\"大丸子\",\"utype\":1,\"phone\":\"17839166574\",\"createtime\":\"2020-09-24 04:08:12\",\"updatetime\":\"2020-09-24 04:08:14\",\"token\":\"a981d817-421f-4be7-a9b4-dff947186882\",\"integral\":null},\"activityId\":\"hd_001\",\"idempotencyFiled\":\"hd_001:1601359263808\"}";
        KafkaLuckdrawEntity kafkaLuckdrawEntity = JSONObject.parseObject(value, KafkaLuckdrawEntity.class);
        System.out.println(kafkaLuckdrawEntity.toString());
    }

}