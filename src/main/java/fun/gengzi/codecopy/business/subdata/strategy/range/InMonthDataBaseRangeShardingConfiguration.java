package fun.gengzi.codecopy.business.subdata.strategy.range;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Range;
import fun.gengzi.codecopy.business.subdata.strategy.precise.InDateShardingStrategyConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

/**
 * <h1>范围值控制分库分表</h1>
 *
 * 目的在于，根据范围来选择操作那个库，那个表，来执行sql
 *
 *
 * @author gengzi
 * @date 2020年7月2日11:07:15
 */
public class InMonthDataBaseRangeShardingConfiguration implements PreciseShardingAlgorithm<Date>, RangeShardingAlgorithm<Date> {

    private Logger logger = LoggerFactory.getLogger(InDateShardingStrategyConfiguration.class);

    public InMonthDataBaseRangeShardingConfiguration() {
        logger.info("初始化 -> [{}]", "InMonthDataBaseRangeShardingConfiguration ----- 根据月份范围分片算法-启用");
    }

    /**
     * Sharding.
     *
     * @param availableTargetNames available data sources or tables's names
     * @param shardingValue        sharding value
     * @return sharding results for data sources or tables's names
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> shardingValue) {
        // 限定月份的范围 1到12月
        Integer low = 1;
        Integer upper = 12;
        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
        Range<Date> range = shardingValue.getValueRange();
        // 判断是否存在下界
        if (range.hasLowerBound()) {
            Date lowerDate = range.lowerEndpoint();
            logger.info("lowerDate : {} ", DateUtil.formatDateTime(lowerDate));
            low = Integer.valueOf(DateUtil.format(lowerDate, "MM"));
        }
        // 判断是否存在上节
        if (range.hasUpperBound()) {
            Date upperDate = range.upperEndpoint();
            logger.info("upperDate : {} ", DateUtil.formatDateTime(upperDate));
            upper = Integer.valueOf(DateUtil.format(upperDate, "MM"));
        }
        for (int i = low; i <= upper; i++) {
            for (String each : availableTargetNames) {
                if (each.endsWith(i + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }

    /**
     * Sharding.
     *
     * @param availableTargetNames available data sources or tables's names
     * @param shardingValue        sharding value
     * @return sharding result for data source or table's name
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
        //按月份路由
        for (String each : availableTargetNames) {
            String value = StringUtils.substring(time, 5, 7);
            if (each.endsWith(Integer.parseInt(value)+"")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }
}
