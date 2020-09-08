package fun.gengzi.codecopy.business.luckdraw.algorithm;

/**
 * 抽奖算法接口
 */
public interface LuckdrawAlgorithlm<T> {


    /**
     * 抽奖算法
     * @param t 影响因素算法实体
     * @return 奖项级别
     */
    Integer algorithlm(T t);

}
