package fun.gengzi.codecopy.business.luckdraw.algorithm;

import fun.gengzi.codecopy.business.luckdraw.entity.LuckdrawAlgorithlmEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <h1>默认抽奖算法</h1>
 * <p>
 * <p>
 * 该抽奖算法，依靠 Math.random(); 会返回一个 0-1 的随机数
 * 判断生成随机数在那个奖品的概率范围，从而实现抽奖
 * 比如
 * 奖品1 奖品2  奖品3 奖品4
 * 0.1    0.2  0.3    0.4
 * 那么获奖范围：
 * 奖品1  奖品2   奖品3    奖品4
 * 0-0.1 0.1-0.2 0.2-0.3 0.4-1
 * 假如生成随机数 0.22 ，在 0.2 - 0.3 的范围内，获得奖品3
 * <p>
 * 该方式，算是比较公平的方式。
 * 注意： probabilityList 奖品概率的list 需要排序后使用
 *
 * @author gengzi
 * @date 2020年9月9日09:29:20
 */
@Service("DefaultLuckdrawAlgorithlm")
public class DefaultLuckdrawAlgorithlm implements LuckdrawAlgorithlm<LuckdrawAlgorithlmEntity> {
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
