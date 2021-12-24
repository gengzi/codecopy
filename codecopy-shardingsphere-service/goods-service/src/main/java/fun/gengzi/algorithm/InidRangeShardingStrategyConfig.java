package fun.gengzi.algorithm;

import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Optional;

/**
 * 根据id范围分片 ，标准分片支持 =、IN、BETWEEN AND、>、<、>=、<=
 * <p>
 * <p>
 * 可以简单理解，这俩个doSharding 都是来控制返回那个表或者库的真实取值
 * 精确分片，返回一个物理表名或者库名
 * 范围分片，返回一组物理表名或者库名
 *
 * @author gengzi
 * @date 2021年12月9日14:46:44
 */
@Slf4j
public final class InidRangeShardingStrategyConfig implements StandardShardingAlgorithm<Long> {

    // 区间集合Map
    private LinkedHashMap<Range<Long>, Long> hashMap = new LinkedHashMap<>();
    // 区间
    private RangesUtils.Entity entity;
    // 目标表：表名称前缀
    private static final String TARGETNAME_PREFIX = "goods_";
    // 旧表
    private static final String OLD_TABLES_NAME = "goods";

    private RangesUtils<Long, Long> rangesUtils;

    /**
     * Initialize algorithm.
     * 初始化算法
     */
    @Override
    public void init() {
        // 可以做一下初始化动作
        log.info("按id范围分片<init>");
        // 方案1
//        Range<Long> open1 = Range.closed(1L, 10L);
//        Range<Long> open2 = Range.closed(11L, 31L);
//        Range<Long> open3 = Range.closed(41L, 43L);
//        Range<Long> open4 = Range.closed(50L, 100L);
//        hashMap.put(open1, 0L);
//        hashMap.put(open2, 1L);
//        hashMap.put(open3, 0L);
//        hashMap.put(open4, 1L);

        // 方案2 测试环境
        Range<Long> open1 = Range.closed(1L, 1100000L);
        Range<Long> open2 = Range.closed(1100001L, 1500000L);
        Range<Long> open3 = Range.closed(1500001L, 1800000L);
        Range<Long> open4 = Range.closed(1800001L, 2000001L);
        hashMap.put(open1, 0L);
        hashMap.put(open2, 1L);
        hashMap.put(open3, 2L);
        hashMap.put(open4, 3L);

        rangesUtils = new RangesUtils();
        this.entity = this.rangesUtils.mapToArr(hashMap);
    }

    @Override
    public String getType() {
        return "idRang";
    }

    /**
     * Sharding. 精确分片 。用于支持 =、>=、<=、>、<、IN
     * <p>
     * availableTargetNames ：
     * 如果配置如下
     * actual-data-nodes: goods.goods_$->{0..1}
     * <p>
     * goods_0 和 goods_1
     *
     * @param availableTargetNames available data sources or table names 分片表名称信息
     * @param shardingValue        sharding value 分片值
     * @return sharding result for data source or table name  分片结果对应数据源名称或者表名
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        log.info("根据id范围分片：可用目标名称[{}],分片键信息[{}]", availableTargetNames, shardingValue);
        // 对于旧库旧表，不分表
        if (availableTargetNames.size() == 1 &&
                availableTargetNames.stream().filter(name -> name.equals(OLD_TABLES_NAME)).toArray().length == 1) {
            return OLD_TABLES_NAME;
        }
        // 其他情况
        Long keyValue = shardingValue.getValue();
        Object indexVal = rangesUtils.ascOrderFixedLengthRange(this.entity, keyValue);
        // 匹配目标表名称的名称
        Optional<String> targetName = availableTargetNames.stream().filter(name -> name.equals(TARGETNAME_PREFIX + indexVal)).findFirst();
        log.info("根据id范围分片：分片结果[{}]", targetName.get());
        if (targetName.isPresent()) {
            return targetName.get();
        }
        throw new UnsupportedOperationException();
    }

    /**
     * Sharding. 范围分片
     * <p>
     * 根据id分片，1 - 50 万一张表，
     *
     * @param availableTargetNames available data sources or table names
     * @param shardingValue        sharding value
     * @return sharding results for data sources or table names
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        log.info("根据id范围分片：可用目标名称[{}],分片键信息[{}]", availableTargetNames, shardingValue);
        // 循环判断
        // return loopCheckTargentName(availableTargetNames, shardingValue);
        // 首尾判断
        return headAndTailTargetName(availableTargetNames, shardingValue);
    }

    /**
     * 头尾判断
     * <p>
     * // 俩种方法范围确定要查那些表
     * // 1, 循环  2，只判断首 尾 范围
     * <p>
     * // 循环判断，适用于范围区间对应的数据表不是递增的情况 ，比如 [1L,10L] goods_1  [11L,20L] goods_3  [21L,31L] goods_1
     * <p>
     * // 只判断首尾，使用与范围区间对应的数据表是递增的情况
     *
     * @param availableTargetNames
     * @param shardingValue
     * @return
     */
    private Collection<String> headAndTailTargetName(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        // 对于旧库旧表，不分表
        if (availableTargetNames.size() == 1 &&
                availableTargetNames.stream().filter(name -> name.equals(OLD_TABLES_NAME)).toArray().length == 1) {
            return availableTargetNames;
        }
        // 新库
        Long low = 0L;
        Long upper = 0L;
        final LinkedHashSet<String> targetNames = new LinkedHashSet<>();
        Range<Long> rangeVlaue = shardingValue.getValueRange();
        // 存在下界
        if (rangeVlaue.hasLowerBound()) {
            low = rangeVlaue.lowerEndpoint();
        }
        if (rangeVlaue.hasUpperBound()) {
            upper = rangeVlaue.upperEndpoint();
        }
        // 获取下界坐标
        Long lowVal = (Long) rangesUtils.ascOrderFixedLengthRange(this.entity, low);
        // 获取上界坐标
        Long upperVal = (Long) rangesUtils.ascOrderFixedLengthRange(this.entity, upper);
        for (Long i = lowVal; i < upperVal; i++) {
            targetNames.add(availableTargetNames.toArray(new String[]{})[i.intValue() - 1]);
        }
        log.info("根据id范围分片：分片结果[{}]", targetNames);
        return targetNames;
    }

    /**
     * 循环判断，适用于范围区间对应的数据表不是递增的情况 ，比如 [1L,10L] goods_1  [11L,20L] goods_3  [21L,31L] goods_1
     *
     * @param availableTargetNames
     * @param shardingValue
     * @return
     */
    private Collection<String> loopCheckTargentName(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        Long low = 0L;
        Long upper = 0L;
        final LinkedHashSet<String> targetNames = new LinkedHashSet<>();
        Range<Long> rangeVlaue = shardingValue.getValueRange();
        // 存在下界
        if (rangeVlaue.hasLowerBound()) {
            low = rangeVlaue.lowerEndpoint();
        }
        // 存在下界
        if (rangeVlaue.hasUpperBound()) {
            upper = rangeVlaue.upperEndpoint();
        }
        // 循环判断
        for (Long i = low; i < upper; i++) {
            Object indexVal = rangesUtils.ascOrderFixedLengthRange(this.entity, i);
            // 匹配目标表名称的名称
            Optional<String> targetName = availableTargetNames.stream().filter(name -> name.equals(TARGETNAME_PREFIX + indexVal)).findFirst();
            if (targetName.isPresent()) {
                targetNames.add(targetName.get());
            }
        }
        return targetNames;
    }
}
