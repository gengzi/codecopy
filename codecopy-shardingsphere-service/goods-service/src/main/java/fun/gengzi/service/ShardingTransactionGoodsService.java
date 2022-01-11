package fun.gengzi.service;

import org.springframework.stereotype.Service;

/**
 * <h1> 商品服务服务层 </h1>
 *
 *
 * 主要是对事务的演示， xa 二阶段提交
 *
 * @author gengzi
 * @date 2022/1/7 14:12
 */
public interface ShardingTransactionGoodsService {

    /**
     * 默认执行
     * @param goodId
     */
    void inventoryReductions(Long goodId);

    /**
     * 默认执行
     *
     * @param goodId
     */
    void inventoryReductionsByBase(Long goodId);

}
