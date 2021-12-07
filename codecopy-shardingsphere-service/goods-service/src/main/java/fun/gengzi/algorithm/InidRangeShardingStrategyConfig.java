package fun.gengzi.algorithm;

import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.algorithm.sharding.range.VolumeBasedRangeShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * 根据id范围分片
 */
@Slf4j
public final class InidRangeShardingStrategyConfig implements StandardShardingAlgorithm<Long> {

    private LinkedHashMap<Range<Long>, Long> hashMap = new LinkedHashMap<>();
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
    }

    @Override
    public String getType() {
        return "idRang";
    }

    /**
     * Sharding. 精确分片
     *
     * 0 - 2
     * 2 - 4
     *
     *
     * 0 - 500000       1
     * 500000 - 1000000  2
     *
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
        return "goods_"+String.valueOf(rangesUtils.ascOrderFixedLengthRange(hashMap, keyValue));

    }

    /**
     * Sharding. 范围分片
     *
     * 根据id分片，1 - 50 万一张表，
     *
     * @param availableTargetNames available data sources or table names
     * @param shardingValue        sharding value
     * @return sharding results for data sources or table names
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        log.info("根据id范围分片：可用目标名称[{}],分片键信息[{}]", availableTargetNames, shardingValue);
        Range<Long> valueRange = shardingValue.getValueRange();
        int low = 0 ;
        int upper =1;
        final LinkedHashSet<String> targetNames = new LinkedHashSet<>();
        Long aLong = valueRange.lowerEndpoint();
        Long aLong1 = valueRange.upperEndpoint();
        for (int i = low; i <upper ; i++) {
            for(String name :availableTargetNames){
                targetNames.add(name);
            }
        }
        return targetNames;
    }
}
