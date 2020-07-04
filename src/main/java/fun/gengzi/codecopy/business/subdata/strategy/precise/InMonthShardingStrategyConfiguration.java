package fun.gengzi.codecopy.business.subdata.strategy.precise;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Date;

/**
 * <h1>日期的分库策略</h1>
 *
 * @author gengzi
 * @date 2020年7月1日15:56:32
 */
public class InMonthShardingStrategyConfiguration implements PreciseShardingAlgorithm<Date> {
    private Logger logger = LoggerFactory.getLogger(InMonthShardingStrategyConfiguration.class);

    public InMonthShardingStrategyConfiguration() {
        logger.info("初始化 -> [{}]", "InMonthShardingStrategyConfiguration ----- 根据日期精确分片算法-启用");
    }

    /**
     * Sharding.
     * <p>
     * 返回一个拼接库的名称
     *
     * @param availableTargetNames available data sources or tables's names 可用的数据源或表的名称
     * @param shardingValue        sharding value  分片值
     * @return sharding result for data source or table's name 数据源或者表分片结果
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
        // shardingValue 分片的值 日期，2020-06-06 15:12:00  根据年份分表，获取年份最小的，扩展年份最大的
        // 假如只扩展两年的 2020  2021。
        //配置的分片的sharding-column对应的值
        Date timeValue = shardingValue.getValue();
        String time = DateUtil.formatDate(timeValue);
        logger.info("time: {}",time);
        //分库时配置的sharding-column
        String timeColumn = shardingValue.getColumnName();
        //需要分库的逻辑表
        String tableName = shardingValue.getLogicTableName();
        logger.info("分表策略： 分片值 : {} , 分片列: {} , 逻辑表: {} ", time, timeColumn, tableName);
        availableTargetNames.forEach(name->{
            logger.info("availableTargetName ：{} ", name);
        });
        //按年路由
        for (String each : availableTargetNames) {
            String value = StringUtils.substring(time, 5, 7);
            if (each.endsWith(Integer.parseInt(value)+"")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }
}
