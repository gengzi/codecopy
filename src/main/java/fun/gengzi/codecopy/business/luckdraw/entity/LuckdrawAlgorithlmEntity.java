package fun.gengzi.codecopy.business.luckdraw.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * <h1>抽奖算法实体类</h1>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LuckdrawAlgorithlmEntity {
    // 活动id
    private String activityId;
    // 奖品概率list
    private List<Double> probabilityList;

}
