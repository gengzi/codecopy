package fun.gengzi.codecopy.business.subdata.strategy.hint;

import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <h1>过Hint指定分片值而非从SQL中提取分片值的方式进行分片的策略 分库分表</h1>
 * <p>
 * 对于分片字段非SQL决定，而由其他外置条件决定的场景，可使用SQL Hint灵活的注入分片字段。
 * 例：内部系统，按照员工登录主键分库，而数据库中并无此字段。SQL Hint支持通过Java API和SQL注释(待实现)两种方式使用。
 * <p>
 * <p>
 * 分片策略，指定分片值
 * 假设业务数据，都是由不同的代理人填写，并非真实用户填写，在业务数据库表中，并没有此字段。（当然感觉就不合理）
 * 依靠于 代理人的编码来设置分片规则。
 * <p>
 * <p>
 * 三种方式： DATABASE_TABLES  DATABASE_ONLY MASTER_ONLY
 * DATABASE_TABLES  数据库 和 表 都根据某个表来区分
 * hintManager.addDatabaseShardingValue("t_order", 1L);
 * hintManager.addTableShardingValue("t_order", 1L);
 * DATABASE_ONLY  仅数据库
 * hintManager.setDatabaseShardingValue(1L);
 * MASTER_ONLY   仅主数据库
 * hintManager.setMasterRouteOnly();
 *
 *
 *  dsh0_hint.sql  使用这个库
 *
 *
 * 官方说明：
 * 实现动机
 * 通过解析SQL语句提取分片键列与值并进行分片是ShardingSphere对SQL零侵入的实现方式。若SQL语句中没有分片条件，则无法进行分片，需要全路由。
 *
 * 在一些应用场景中，分片条件并不存在于SQL，而存在于外部业务逻辑。因此需要提供一种通过外部指定分片结果的方式，在ShardingSphere中叫做Hint。
 *
 * 实现机制
 * ShardingSphere使用ThreadLocal管理分片键值。可以通过编程的方式向HintManager中添加分片条件，该分片条件仅在当前线程内生效。
 *
 * 除了通过编程的方式使用强制分片路由，ShardingSphere还计划通过SQL中的特殊注释的方式引用Hint，使开发者可以采用更加透明的方式使用该功能。
 *
 * 指定了强制分片路由的SQL将会无视原有的分片逻辑，直接路由至指定的真实数据节点。
 *
 * 特殊注释，这个功能还在开发
 *
 *
 *
 *
 *
 *
 *
 *
 * @author gengi
 * @date 2020年7月10日13:45:09
 */
public class InShardingValuesHintToDataBaseShardingConfiguration implements HintShardingAlgorithm {
    private Logger logger = LoggerFactory.getLogger(InShardingValuesHintToDataBaseShardingConfiguration.class);

    public InShardingValuesHintToDataBaseShardingConfiguration() {
        logger.info("初始化 -> [{}]", "InShardingValuesHintShardingConfiguration ----- 根据Hint额外值分片算法-启用");
    }

    /**
     * Sharding.
     *
     * <p>sharding value injected by hint, not in SQL.</p>
     * 分片值通过提示注入，而不是在SQL中
     *
     * @param availableTargetNames available data sources or tables's names 可用的数据库和表名称
     * @param shardingValue        sharding value  分片值
     * @return sharding result for data sources or tables's names 数据源或者表名称的分片结果
     */
    @Override
    public Collection<String> doSharding(Collection availableTargetNames, HintShardingValue shardingValue) {
        final ArrayList<String> result = new ArrayList<>(availableTargetNames.size());
        // 列名称
        final String columnName = shardingValue.getColumnName();
        // 逻辑表名称
        final String logicTableName = shardingValue.getLogicTableName();
        // 获取值集合
        final Collection values = shardingValue.getValues();
        // 算法： 根据userid % 2 来筛选，需要操作的数据库名称 和数据表名称
        Collection<String> targetNames = availableTargetNames;
        targetNames.forEach(name -> {
                    values.forEach(obj -> {
                        if (obj instanceof Integer) {
                            final Integer userid = (Integer) obj;
                            if (name.endsWith((userid % 2) + "")) {
                                result.add(name);
                            }
                        }
                    });
                }
        );
        return result;
    }
}
