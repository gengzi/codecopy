package fun.gengzi.codecopy.business.subdata.strategy.complex;


import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Range;
import fun.gengzi.codecopy.constant.RspCodeEnum;
import fun.gengzi.codecopy.exception.RrException;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * <h1>复合分片算法</h1>
 * <p>
 * 针对多个分片键的策略
 *
 * @author gengzi
 * @date 2020年7月3日14:42:45
 */
public class InFiledstodbComplexKeysShardingConfiguration implements ComplexKeysShardingAlgorithm {

    private Logger logger = LoggerFactory.getLogger(InFiledstodbComplexKeysShardingConfiguration.class);

    @Getter
    enum ShardingColumnsEnum {
        createdate("createdate"), // 时间
        addresscode("addresscode"); // 地址code

        ShardingColumnsEnum(String key) {
            this.key = key;
        }

        private String key;
    }

    public InFiledstodbComplexKeysShardingConfiguration() {
        logger.info("初始化 -> [{}]", "InFiledsComplexKeysShardingConfiguration ----- 复合分片算法-启用");
    }

    /**
     * Sharding.
     * <p>
     * 分片键
     * * 地区 西部，东部
     * * 时间 年，月
     * <p>
     * 分片规则 假设
     * 按照 时间的年来分库，比如 ds2020 ds2021
     * 按照 地区 和 月份 来分表， bussiness_west01  bussiness_west02  bussiness_est01 bussiness_est02
     *
     * @param availableTargetNames available data sources or tables's names
     * @param shardingValue        sharding value
     * @return sharding results for data sources or tables's names
     */
    @Override
    public Collection<String> doSharding(Collection availableTargetNames, ComplexKeysShardingValue shardingValue) {
        final Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
        // 列名称和范围分片的值
        Map columnNameAndRangeValuesMap = shardingValue.getColumnNameAndRangeValuesMap();
        // 列名称和精确分片的值
        Map columnNameAndShardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();
        // 逻辑表名称,比如 逻辑数据库 ds 物理数据库 ds2020 ds2021
        String logicTableName = shardingValue.getLogicTableName();

        // 精确分片算法实现
        if (!columnNameAndShardingValuesMap.isEmpty()) {
            logger.info("库- 复合分片-精确分片算法执行");
            logger.info("key : {}", columnNameAndShardingValuesMap.keySet().toArray());
            logger.info("value : {}", columnNameAndShardingValuesMap.values().toArray());
            Collection<String> dbNamesBySharding = getShardingTargetNamesByShardingValues(availableTargetNames, columnNameAndShardingValuesMap);
            result.addAll(dbNamesBySharding);
        }
        // 范围分片算法实现
        if (!columnNameAndRangeValuesMap.isEmpty()) {
            logger.info("库- 复合分片-范围分片算法执行");
            logger.info("key : {}", columnNameAndRangeValuesMap.keySet().toArray());
            logger.info("value : {}", columnNameAndRangeValuesMap.values().toArray());
            Collection<String> dbNamesByRange = getShardingTargetNamesByRangeValues(availableTargetNames, columnNameAndRangeValuesMap);
            result.addAll(dbNamesByRange);
        }
        logger.info("result 分库结果: {}", result.toArray());

        return result;
    }

    /**
     * 范围分片算法，支持 BETWEEN AND, >, <, >=, <= 的sql
     *
     * @param availableTargetNames        可用目标名称
     * @param columnNameAndRangeValuesMap 列名和范围值的map
     * @return
     */
    private Collection<String> getShardingTargetNamesByRangeValues(Collection<String> availableTargetNames, Map columnNameAndRangeValuesMap) {
        // 预设范围，防止参数传递过来，并没有范围 ，表示从 1900年-5000年
        Integer low = 1900;
        Integer upper = 5000;

        final Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
        Range<Date> range = (Range<Date>) columnNameAndRangeValuesMap.get(ShardingColumnsEnum.createdate.getKey());
        // 判断是否存在下界
        if (range.hasLowerBound()) {
            Date lowerDate = range.lowerEndpoint();
            logger.info("lowerDate : {} ", DateUtil.formatDateTime(lowerDate));
            low = Integer.valueOf(DateUtil.format(lowerDate, "yyyy"));
        }
        // 判断是否存在上界
        if (range.hasUpperBound()) {
            Date upperDate = range.upperEndpoint();
            logger.info("upperDate : {} ", DateUtil.formatDateTime(upperDate));
            upper = Integer.valueOf(DateUtil.format(upperDate, "yyyy"));
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
     * 精确分片的算法，支持 = in 的sql
     *
     * @param availableTargetNames           可用的目标名称
     * @param columnNameAndShardingValuesMap 列名和分片值的Map
     * @return
     */
    private Collection<String> getShardingTargetNamesByShardingValues(Collection availableTargetNames, Map columnNameAndShardingValuesMap) {
        final Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
        Collection<Date> createdate = (Collection<Date>) columnNameAndShardingValuesMap.get(ShardingColumnsEnum.createdate.getKey());
        Optional<Date> optionalDate = createdate.stream().findFirst();

        Date date = optionalDate.orElseThrow(() -> new RrException("error createdate", RspCodeEnum.SHARDING_DB_FAILURE.getCode()));
        logger.info("createdate : {}", date);

        String time = DateUtil.formatDate(date);
        //按年路由
        for (Object each : availableTargetNames) {
            String value = StringUtils.substring(time, 0, 4); //获取到年份
            if (((String) each).endsWith(value)) {
                result.add((String) each);
            }
        }
        return result;
    }
}
