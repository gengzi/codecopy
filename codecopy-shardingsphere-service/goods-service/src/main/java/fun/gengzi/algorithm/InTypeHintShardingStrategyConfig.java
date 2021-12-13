package fun.gengzi.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingValue;

import java.util.Collection;
import java.util.stream.Collectors;


@Slf4j
public final class InTypeHintShardingStrategyConfig implements HintShardingAlgorithm<String> {
    /**
     * Sharding.
     *
     * <p>sharding value injected by hint, not in SQL.</p>
     *
     * @param availableTargetNames available data sources or table names
     * @param shardingValue        sharding value
     * @return sharding result for data sources or table names
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, HintShardingValue<String> shardingValue) {
        log.info("{}===={}",availableTargetNames,shardingValue);
        Collection<String> values = shardingValue.getValues();
        if(values.contains("old")){
            return availableTargetNames.stream().filter( name -> { return name.contains("old");}).collect(Collectors.toList());
        }
        return  availableTargetNames.stream().filter( name -> { return !name.contains("old");}).collect(Collectors.toList());
    }

    /**
     * Initialize algorithm.
     */
    @Override
    public void init() {
        log.info("强制分片路由：新旧库策略");

    }

    @Override
    public String getType() {
        return "intype";
    }
}
