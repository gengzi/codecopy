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
        final Collection<String> resultByAddress = new LinkedHashSet<>(availableTargetNames.size());
        final Collection<String> resultByDate = new LinkedHashSet<>(availableTargetNames.size());
        // 列名称和范围分片的值
        Map columnNameAndRangeValuesMap = shardingValue.getColumnNameAndRangeValuesMap();
        // 列名称和精确分片的值
        Map columnNameAndShardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();
        // 逻辑表名称
        String logicTableName = shardingValue.getLogicTableName();

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
        if(intersection.size() == 0){
            throw new  RrException("error", RspCodeEnum.SHARDING_ROUTE_FAILURE.getCode());
        }
        return intersection;
    }
}
