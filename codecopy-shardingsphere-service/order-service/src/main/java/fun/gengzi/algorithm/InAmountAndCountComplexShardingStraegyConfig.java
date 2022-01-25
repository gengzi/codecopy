package fun.gengzi.algorithm;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <h1>分表策略：金额和商品数目</h1>
 * <p>
 * 商品数目 <=3  || 金额 <= 5000  表1
 * 商品数目 >3 || 金额 > 5000   表2
 *
 * @author gengzi
 * @date 2022/1/20 16:19
 */
@Slf4j
public class InAmountAndCountComplexShardingStraegyConfig implements ComplexKeysShardingAlgorithm<String> {

    // 金额
    public static final String KEYS_AMOUNT = "amount";
    // 数目
    public static final String KEYS_COUNT = "count";
    // 金额比较值
    private BigDecimal amountDefault = new BigDecimal("5000");

    // 目标表：表名称前缀
    private static final String TARGETNAME_PREFIX = "pay_order_";

    /**
     * Sharding.
     *
     * @param availableTargetNames available data sources or table names 可用的目标名称
     * @param shardingValue        sharding value 分片键
     * @return sharding results for data sources or table names
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<String> shardingValue) {
        // 判断当前那个有值，以不同的逻辑处理
        log.info("商品表分表,可用目标名称[{}],分片键信息[{}]", availableTargetNames, shardingValue);
        // 范围分片
        Map<String, Range<String>> rangeValuesMap = shardingValue.getColumnNameAndRangeValuesMap();
        if (CollectionUtil.isNotEmpty(rangeValuesMap)) {
            // 执行范围分片逻辑
            return rangeSharding(availableTargetNames, rangeValuesMap);
        }
        // 精确分片
        Map<String, Collection<String>> nameAndShardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();
        if (CollectionUtil.isNotEmpty(nameAndShardingValuesMap)) {
            return sharding(availableTargetNames, nameAndShardingValuesMap);
        }
        throw new UnsupportedOperationException();
    }


    /**
     * 单键值
     *
     * @param availableTargetNames
     * @param nameAndShardingValuesMap
     * @return
     */
    private Collection<String> sharding(Collection<String> availableTargetNames, Map<String, Collection<String>> nameAndShardingValuesMap) {
        String amountStr = nameAndShardingValuesMap.get(KEYS_AMOUNT).stream().findFirst().orElse(null);
        String countStr = nameAndShardingValuesMap.get(KEYS_COUNT).stream().findFirst().orElse(null);
        if (StringUtils.isAnyBlank(amountStr, countStr)) {
            throw new NullPointerException("分片键值为空，请检查!");
        }
        BigDecimal amount = new BigDecimal(amountStr);
        Integer count = Integer.valueOf(countStr);
        log.info("商品分片键:金额:{},数目:{}", amount, count);
        String targetTableName;
        if (count <= 3 || amount.compareTo(amountDefault) < 1) {
            targetTableName = TARGETNAME_PREFIX + "1";
        } else {
            targetTableName = TARGETNAME_PREFIX + "2";
        }
        List<String> result = availableTargetNames.stream().filter(tableName -> tableName.equals(targetTableName)).collect(Collectors.toList());
        log.info("商品表分表，分片结果:{}", result);
        return result;
    }

    /**
     * 单键值
     *
     * @param availableTargetNames 目标表集合
     * @param rangeMap             范围分片键和数据
     * @return
     */
    private Collection<String> rangeSharding(Collection<String> availableTargetNames, Map<String, Range<String>> rangeMap) {
        // 获取不同分片键，取分片逻辑处理
        Range<String> countRange = rangeMap.get(KEYS_COUNT);
        Range<String> amountRange = rangeMap.get(KEYS_AMOUNT);
        String countLow = "";
        String countHeigh = "";
        if (countRange.hasUpperBound()) {
            countHeigh = countRange.upperEndpoint();
        }
        if (countRange.hasLowerBound()) {
            countLow = countRange.lowerEndpoint();
        }

        String amountLow = "";
        String amountHeigh = "";
        if (amountRange.hasUpperBound()) {
            amountLow = amountRange.lowerEndpoint();
        }
        if (amountRange.hasLowerBound()) {
            amountHeigh = amountRange.upperEndpoint();
        }

        HashMap<String, Collection<String>> objectObjectHashMap = new HashMap<>();

        Collection<String> sharding = sharding(availableTargetNames, objectObjectHashMap);



        return null;
    }

    /**
     * Initialize algorithm.
     * <p>
     * 初始化
     */
    @Override
    public void init() {
        log.info("按商品数目和商品总价多分片键分片<init>");
    }

    /**
     * Get type.
     * 与配置文件中的一致
     *
     * @return type
     */
    @Override
    public String getType() {
        return "complexamountandcount";
    }


}
