package fun.gengzi.codecopy.business.luckdraw.algorithm;

import fun.gengzi.codecopy.business.luckdraw.entity.LuckdrawAlgorithlmEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <h1>权重抽奖算法</h1>
 *
 * 根据权重来判断获得奖项
 *
 * 假如 区分 新客户，老客户，新会员客户，老会员客户
 * 定义新客户获取 1 等级 概率为 0.5 2等奖 概率为 0.4 3等奖 0.1
 * 老客户获取 1等级 概率为 0.1 2等奖 概率为 0.4 3等奖 0.5
 * 新会员客户 获取 1等奖 概率为 0.7 2等奖 概率为 0.2 3等奖 0.1
 * 老会员客户  获取 1 等奖 概率为 0.3 2等奖 概率为 0.3 3等奖 0.4
 *
 *
 *
 *
 *
 * @author gengzi
 * @date 2020年9月15日10:55:33
 */
@Service("weightLuckdrawAlgorithlm")
public class WeightLuckdrawAlgorithlm implements LuckdrawAlgorithlm<LuckdrawAlgorithlmEntity> {
    @Override
    public Integer algorithlm(LuckdrawAlgorithlmEntity luckdrawAlgorithlmEntity) {
        return draw(luckdrawAlgorithlmEntity.getProbabilityList());
    }


    /**
     * 抽奖算法
     *
     * @param giftProbList 排序后的抽奖概率list
     * @return
     */
    public static int draw(List<Double> giftProbList) {
       // TODO
        return -1;
    }
}
