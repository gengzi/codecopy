package fun.gengzi.codecopy.business.luckdraw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <h1>kafka 抽奖实体类</h1>
 *
 * @author gengzi
 * @date 2020年9月23日17:00:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaLuckdrawEntity implements Serializable {

    private static final long serialVersionUID = 1907603312737460676L;
    // 用户信息
    private SysUser sysUser;
    // 活动id
    private String activityId;
    // 保证幂等性的字段
    private String idempotencyFiled;


}
