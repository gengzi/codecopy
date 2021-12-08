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
 * 根据id范围分片
 */
@Slf4j
public final class InidRangeShardingStrategyConfig implements StandardShardingAlgorithm<Long> {

    // 区间集合Map
    private LinkedHashMap<Range<Long>, Long> hashMap = new LinkedHashMap<>();
    // 区间
    private RangesUtils.Entity entity;
    // 目标表：表名称前缀
    private static final String TARGETNAME_PREFIX = "goods_";

    /**
     * Initialize algorithm.
     * 初始化算法
     */
    @Override
    public void init() {
        // 可以做一下初始化动作
        log.info("按id范围分片<init>");
        Range<Long> open1 = Range.closed(1L, 10L);
        Range<Long> open2 = Range.closed(11L, 31L);
        Range<Long> open3 = Range.closed(41L, 43L);
        Range<Long> open4 = Range.closed(50L, 100L);
        hashMap.put(open1, 0L);
        hashMap.put(open2, 1L);
        hashMap.put(open3, 0L);
        hashMap.put(open4, 1L);
        RangesUtils rangesUtils = new RangesUtils<>();
        this.entity = rangesUtils.mapToArr(hashMap);
    }

    @Override
    public String getType() {
        return "idRang";
    }

    /**
     * Sharding. 精确分片
     * <p>
     * 0 - 2
     * 2 - 4
     * <p>
     * <p>
     * 0 - 500000       1
     * 500000 - 1000000  2
     *
     * @param availableTargetNames available data sources or table names
     * @param shardingValue        sharding value
     * @return sharding result for data source or table name
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        log.info("根据id范围分片：可用目标名称[{}],分片键信息[{}]", availableTargetNames, shardingValue);
        Long keyValue = shardingValue.getValue();
        RangesUtils rangesUtils = new RangesUtils<>();
        Object indexVal = rangesUtils.ascOrderFixedLengthRange(this.entity, keyValue);
        // 匹配目标表名称的名称
        Optional<String> targetName = availableTargetNames.stream().filter(name -> name.equals(TARGETNAME_PREFIX + indexVal)).findFirst();
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
        //
        //        for (Long i = low; i < upper; i++) {
        //            for (String name : availableTargetNames) {
        //                targetNames.add(name);
        //            }
        //        }
        //        return targetNames;

        //return availableTargetNames;
    }

    private Collection<String> headAndTailTargetName(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
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

        // 俩种方法范围确定要查那些表
        // 1, 循环  2，只判断首 尾 范围

        // 循环判断，适用于范围区间对应的数据表不是递增的情况 ，比如 [1L,10L] goods_1  [11L,20L] goods_3  [21L,31L] goods_1

        // 只判断首尾，使用与范围区间对应的数据表是递增的情况
        RangesUtils rangesUtils = new RangesUtils<>();

        Long lowVal = (Long) rangesUtils.ascOrderFixedLengthRange(this.entity, low);

        Long upperVal = (Long) rangesUtils.ascOrderFixedLengthRange(this.entity, upper);
        for (Long i = lowVal; i < upperVal; i++) {
            targetNames.add(availableTargetNames.toArray(new String[]{})[i.intValue()-1]);
        }
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
        if (rangeVlaue.hasUpperBound()) {
            upper = rangeVlaue.upperEndpoint();
        }

        // 俩种方法范围确定要查那些表
        // 1, 循环  2，只判断首 尾 范围

        // 循环判断，适用于范围区间对应的数据表不是递增的情况 ，比如 [1L,10L] goods_1  [11L,20L] goods_3  [21L,31L] goods_1

        // 只判断首尾，使用与范围区间对应的数据表是递增的情况
        RangesUtils rangesUtils = new RangesUtils<>();
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
