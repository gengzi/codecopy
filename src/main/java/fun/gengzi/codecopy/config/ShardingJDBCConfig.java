package fun.gengzi.codecopy.config;

import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * sharding jdbc 配置
 * <p>
 * 按日分表策略
 *
 * https://shardingsphere.apache.org/document/legacy/4.x/document/cn/manual/sharding-jdbc/configuration/config-java/
 *
 * @author gengzi
 * @date 2020年6月24日13:48:21
 */
//@Configuration
public class ShardingJDBCConfig {

    DataSource getShardingDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        // 设置表分片规则
        shardingRuleConfig.getTableRuleConfigs().add(getBussinessTableRuleConfiguration());
        // 设置绑定表
        shardingRuleConfig.getBindingTableGroups().add("t_bussiness");
        // 设置广播表
        shardingRuleConfig.getBroadcastTables().add("shorturl,area_info,dic_data,dic_list,sys_permission");
        // 设置默认的分库策略
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("data_version", "ds$->{data_version % 2}"));
        // 设置默认的分表策略
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("id", new PreciseModuloShardingTableAlgorithm()));

        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, getProperties());
    }


    /**
     * 配置 sharding jdbc 的一些配置
     * @return
     */
    private static Properties  getProperties(){
        Properties properties = new Properties();
        properties.setProperty("sql.show","true");
        return properties;
    }


    /**
     * 主键生成策略 - 雪花算法
     *
     * @return
     */
    private static KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
        KeyGeneratorConfiguration result = new KeyGeneratorConfiguration("SNOWFLAKE", "id");
        return result;
    }


    /**
     * t_bussiness 表规则
     *
     * @return
     */
    TableRuleConfiguration getBussinessTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("t_bussiness", "ds$->{0..1}.t_bussiness$->{0..1}");
        result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());
        return result;
    }


    /**
     * 创建所有的 数据源
     *
     * @return {@link  Map<String, DataSource> }
     */
    Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>();
        result.put("ds0", DataSourceUtilConfig.createDataSource("ds0"));
        result.put("ds1", DataSourceUtilConfig.createDataSource("ds1"));
        return result;
    }


}
