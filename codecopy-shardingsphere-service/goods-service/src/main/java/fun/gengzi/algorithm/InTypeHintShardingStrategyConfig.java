package fun.gengzi.algorithm;

import fun.gengzi.enums.ShardingDataSourceType;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingValue;

import java.util.Collection;
import java.util.stream.Collectors;


/**
 * <h1>数据库分片策略</h1>
 * <p>
 * 根据新老数据库设计的分片策略，通过 HintManager 机制
 * 设定 旧库 TYPE_OLD 新库 TYPE_NEW ,通过设置不同值实现
 * <p>
 * <p>
 * Apache ShardingSphere 使用 ThreadLocal 管理分片键值。 可以通过编程的方式向 HintManager 中添加分片条件，该分片条件仅在当前线程内生效。
 * 除了通过编程的方式使用强制分片路由，Apache ShardingSphere 还可以通过 SQL 中的特殊注释的方式引用 Hint，使开发者可以采用更加透明的方式使用该功能。
 * 指定了强制分片路由的 SQL 将会无视原有的分片逻辑，直接路由至指定的真实数据节点。
 *
 * @author gengzi
 * @date 2021年12月13日22:13:16
 */
@Slf4j
public final class InTypeHintShardingStrategyConfig implements HintShardingAlgorithm<String> {

    /**
     * Sharding.
     * <p>
     * 已经在配置新旧数据库名称，区分了，  new开头为分库分表后的数据库，old开头为旧数据库
     * <p>
     * 判断 shardingValue 存在  旧库 TYPE_OLD 就返回旧库，新库 TYPE_NEW 就返回新库
     *
     * <p>sharding value injected by hint, not in SQL.</p>
     *
     * @param availableTargetNames available data sources or table names  数据源名称或者表名称
     * @param shardingValue        sharding value 分片值
     * @return sharding result for data sources or table names 分片结果
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, HintShardingValue<String> shardingValue) {
        log.info("新旧库策略,可用目标名称[{}],分片键信息[{}]", availableTargetNames, shardingValue);
        Collection<String> values = shardingValue.getValues();
        if (values.contains(ShardingDataSourceType.TYPE_OLD.getType())) {
            return availableTargetNames.stream().filter(name -> name.startsWith(ShardingDataSourceType.TYPE_OLD.getPrefix())).collect(Collectors.toList());
        }
        if (values.contains(ShardingDataSourceType.TYPE_NEW.getType())) {
            return availableTargetNames.stream().filter(name -> name.startsWith(ShardingDataSourceType.TYPE_NEW.getPrefix())).collect(Collectors.toList());
        }
        throw new UnsupportedOperationException();
    }

    /**
     * Initialize algorithm.
     */
    @Override
    public void init() {
        log.info("强制分片路由：新旧库策略<init>");

    }

    @Override
    public String getType() {
        return "intype";
    }
}
