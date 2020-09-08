package fun.gengzi.codecopy.business.luckdraw.algorithm;

import fun.gengzi.codecopy.business.luckdraw.entity.LuckdrawAlgorithlmEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <h1>默认抽奖算法</h1>
 */
@Service("DefaultLuckdrawAlgorithlm")
public class DefaultLuckdrawAlgorithlm implements LuckdrawAlgorithlm<LuckdrawAlgorithlmEntity> {
    @Override
    public Integer algorithlm(LuckdrawAlgorithlmEntity luckdrawAlgorithlmEntity) {
        return draw(luckdrawAlgorithlmEntity.getProbabilityList());
    }


    public static int draw(List<Double> giftProbList) {
        List<Double> sortRateList = new ArrayList<Double>();
        // 计算概率总和
        Double sumRate = 0D;
        for (Double prob : giftProbList) {
            sumRate += prob;
        }
        if (sumRate != 0) {
            double rate = 0D;    //概率所占比例
            for (Double prob : giftProbList) {
                rate += prob;
                // 构建一个比例区段组成的集合(避免概率和不为1)
                sortRateList.add(rate / sumRate);
            }
            // 随机生成一个随机数，并排序
            double random = Math.random();
            sortRateList.add(random);
            Collections.sort(sortRateList);
            // 返回该随机数在比例集合中的索引
            return sortRateList.indexOf(random);
        }
        return -1;
    }
}
