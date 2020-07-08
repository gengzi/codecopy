package fun.gengzi.codecopy.business.subdata.strategy.complex;


import cn.hutool.core.date.DateUtil;
import fun.gengzi.codecopy.constant.RspCodeEnum;
import fun.gengzi.codecopy.exception.RrException;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Range;

import java.util.*;

/**
 * <h1>复合分片算法</h1>
 * <p>
 * 针对多个分片键的策略
 *
 * @author gengzi
 * @date 2020年7月3日14:42:45
 */
public class InFiledstoTableComplexKeysShardingConfiguration implements ComplexKeysShardingAlgorithm {

    private Logger logger = LoggerFactory.getLogger(InFiledstoTableComplexKeysShardingConfiguration.class);

    final List<String> addressAll = Arrays.asList("west", "est");

    @Getter
    enum ShardingColumnsEnum {
        createdate("createdate"), // 时间
        addresscode("addresscode"); // 地址code

        ShardingColumnsEnum(String key) {
            this.key = key;
        }

        private String key;
    }

    public InFiledstoTableComplexKeysShardingConfiguration() {
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
        final Collection<String> dbNamesBySharding = new LinkedHashSet<>(availableTargetNames.size());
        final Collection<String> dbNamesByRange = new LinkedHashSet<>(availableTargetNames.size());
        // 列名称和范围分片的值
        Map columnNameAndRangeValuesMap = shardingValue.getColumnNameAndRangeValuesMap();
        // 列名称和精确分片的值
        Map columnNameAndShardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();
        // 逻辑表名称
        String logicTableName = shardingValue.getLogicTableName();


        // 精确分片算法实现
        if (!columnNameAndShardingValuesMap.isEmpty()) {
            logger.info("复合分片-精确分片算法执行");
            logger.info("key : {}", columnNameAndShardingValuesMap.keySet().toArray());
            logger.info("value : {}", columnNameAndShardingValuesMap.values().toArray());
            Collection<String> shardingTargetNamesByShardingValues = getShardingTargetNamesByShardingValues(availableTargetNames, columnNameAndShardingValuesMap);
            dbNamesBySharding.addAll(shardingTargetNamesByShardingValues);

        }
        // 范围分片算法实现
        if (!columnNameAndRangeValuesMap.isEmpty()) {
            logger.info("复合分片-范围分片算法执行");
            logger.info("key : {}", columnNameAndRangeValuesMap.keySet().toArray());
            logger.info("value : {}", columnNameAndRangeValuesMap.values().toArray());
            Collection shardingTargetNamesByRangeValues = getShardingTargetNamesByRangeValues(availableTargetNames, columnNameAndRangeValuesMap);
            dbNamesByRange.addAll(shardingTargetNamesByRangeValues);

        }


        // TODO 如果精确分片和范围分片 都支持，这个策略是什么
        // 比如查询 est 东部地区，月份 1到 2 的数据
        // 就需要将范围分片和精确分片的结果集进行处理,交集
        if (!dbNamesBySharding.isEmpty() && !dbNamesByRange.isEmpty()) {
            Collection<String> intersection = CollectionUtils.intersection(dbNamesBySharding, dbNamesByRange);
            if (intersection.isEmpty()) {
                throw new RrException("error", RspCodeEnum.SHARDING_ROUTE_FAILURE.getCode());
            }
            result.addAll(intersection);
        } else {
            result.addAll(dbNamesBySharding);
            result.addAll(dbNamesByRange);
        }


        logger.info("result 分库结果: {}", result.toArray());
        return result;
    }


    /**
     * 范围分片，支持 BETWEEN AND, >, <, >=, <= 的sql
     * <p>
     * 表名由  地区和月份组拼构成
     * 例子：
     * t_bussiness_region_est1  t_bussiness_region_est2  t_bussiness_region_west1  t_bussiness_region_west2
     * <p>
     * <p>
     * 算法思路：
     * <p>
     * 根据月份的上界和下界，得到月份的范围，匹配多个表
     * 对于address 不存在范围，或者 >, <, >=, <=  的操作，所以不对齐进行判断
     *
     * @param availableTargetNames        可用的目标表
     * @param columnNameAndRangeValuesMap 列名称和范围分片值Map
     * @return
     */
    private Collection<String> getShardingTargetNamesByRangeValues(Collection<String> availableTargetNames, Map columnNameAndRangeValuesMap) {
        final Range<Date> range = (Range<Date>) columnNameAndRangeValuesMap.get(ShardingColumnsEnum.createdate.getKey());
        // 限定月份的范围 1到12月
        Integer low = 1;
        Integer upper = 12;
        final Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
        // 判断是否存在下界
        if (range.hasLowerBound()) {
            Date lowerDate = range.lowerEndpoint();
            logger.info("lowerDate : {} ", DateUtil.formatDateTime(lowerDate));
            low = Integer.valueOf(DateUtil.format(lowerDate, "MM"));
        }
        // 判断是否存在上界
        if (range.hasUpperBound()) {
            Date upperDate = range.upperEndpoint();
            logger.info("upperDate : {} ", DateUtil.formatDateTime(upperDate));
            upper = Integer.valueOf(DateUtil.format(upperDate, "MM"));
        }
        // 注意：传入的日期，可能会导致  上界 和 下界，下界比上界大，比如 2020-03-03 到 2021-01-03
        // 会导致，上界比下界还小，需要做处理，当判断上界比下界小，就交换
        if (low > upper) {
            low = low ^ upper;
            upper = low ^ upper;
            low = low ^ upper;
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
     * 支持 in = 的sql 执行
     * <p>
     * 表名由  地区和月份组拼构成
     * 例子：
     * t_bussiness_region_est1  t_bussiness_region_est2  t_bussiness_region_west1  t_bussiness_region_west2
     * <p>
     * 算法思路：
     * <p>
     * 先根据address 来找出包含 addres值的 表名
     * 再根据月份，找出包含 月份的表名
     * 将两个表名的集合，取交集，得到最终的表名
     * <p>
     * 比如： address ： est  月份：1
     * 找出 est 的表名有  t_bussiness_region_est1  t_bussiness_region_est2
     * 找出 1月 的表名有 t_bussiness_region_est1  t_bussiness_region_west1
     * 取交集，只留下了 t_bussiness_region_est1 ，这个表就是真正要操作的表名
     *
     * @param availableTargetNames           可用的目标表
     * @param columnNameAndShardingValuesMap 列名称和分片值映射
     * @return {@link Collection<String>} in = sql 需要执行的目标表
     */
    private Collection<String> getShardingTargetNamesByShardingValues(Collection availableTargetNames, Map columnNameAndShardingValuesMap) {
        final Collection<String> resultByAddress = new LinkedHashSet<>(availableTargetNames.size());
        final Collection<String> resultByDate = new LinkedHashSet<>(availableTargetNames.size());
        Collection<Date> createdateList = (Collection<Date>) columnNameAndShardingValuesMap.get(ShardingColumnsEnum.createdate.getKey());
        Collection<String> addresscodeList = (Collection<String>) columnNameAndShardingValuesMap.get(ShardingColumnsEnum.addresscode.getKey());
        Optional<Date> optionalDate = createdateList.stream().findFirst();
        Optional<String> optionalAddress = addresscodeList.stream().findFirst();

        Date date = optionalDate.orElseThrow(() -> new RrException("error createdate ", RspCodeEnum.SHARDING_TABLE_FAILURE.getCode()));
        String addresscode = optionalAddress.orElseThrow(() -> new RrException("error addresscode ", RspCodeEnum.SHARDING_TABLE_FAILURE.getCode()));
        logger.info("createdate : {}", date);
        logger.info("addresscode : {}", addresscode);
        Collection<String> stravailableTargetNames = availableTargetNames;

        stravailableTargetNames.forEach(each -> {
            String[] split = each.split("_");
            String tableName = split[split.length - 1];
            if (tableName.startsWith(addresscode)) {
                resultByAddress.add(each);
            }
        });

        String time = DateUtil.formatDate(date);
        //按月份路由
        for (String each : stravailableTargetNames) {
            String value = StringUtils.substring(time, 5, 7);
            if (each.endsWith(Integer.parseInt(value) + "")) {
                resultByDate.add(each);
            }
        }
        // 取两个集合的交集，确定要执行sql 对应表
        Collection<String> intersection = CollectionUtils.intersection(resultByAddress, resultByDate);
        if (intersection.isEmpty()) {
            throw new RrException("error", RspCodeEnum.SHARDING_ROUTE_FAILURE.getCode());
        }
        return intersection;
    }

}
