package fun.gengzi.codecopy.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 精确分片算法 - 针对表
 *
 * https://github.com/geomonlin/incubator-shardingsphere-example/blob/4.0.0-RC2/example-core/config-utility/src/main/java/org/apache/shardingsphere/example/algorithm/PreciseModuloShardingTableAlgorithm.java
 *
 * @author gengzi
 * @date 2020年6月24日14:13:25
 *
 */
public final class PreciseModuloShardingTableAlgorithm implements PreciseShardingAlgorithm<Long> {
    
    @Override
    public String doSharding(final Collection<String> tableNames, final PreciseShardingValue<Long> shardingValue) {
        for (String each : tableNames) {
            if (each.endsWith(shardingValue.getValue() % 2 + "")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }
}