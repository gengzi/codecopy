package fun.gengzi.service;

import fun.gengzi.dao.GoodsShardingJPA;
import org.apache.shardingsphere.transaction.annotation.ShardingSphereTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h1>事务演示</h1>
 *
 * @author gengzi
 * @date 2022/1/7 14:15
 */
@Service
public class ShardingTransactionGoodsServiceImpl implements ShardingTransactionGoodsService{

    @Autowired
    private GoodsShardingJPA goodsJPA;


    /**
     *  配置分布式事务 注意shariding jdbc xa 两阶段提交，我认为并不能控制远程请求的 事务
     *  只能控制，当前sharding jdbc 执行sql 的库和表的事务
     *  如果需要使用远程控制，需要使用 sharding jdbc 接入西塔的分布式事务
     * @param goodId
     */
    @ShardingSphereTransactionType(TransactionType.XA)
    @Transactional
    @Override
    public void inventoryReductions(Long goodId) {
        for (int i = 0; i < 2; i++) {
            // 正常测试
            goodsJPA.inventoryReduction(goodId, 1);
            // 模拟异常情况。看是否回滚
        }
        // 可以成功
        throw new RuntimeException("测试回滚是否成功");
    }

    /**
     *  配置分布式事务 注意shariding jdbc xa 两阶段提交，我认为并不能控制远程请求的 事务
     *  只能控制，当前sharding jdbc 执行sql 的库和表的事务
     *  如果需要使用远程控制，需要使用 sharding jdbc 接入西塔的分布式事务
     * @param goodId
     */
    @ShardingSphereTransactionType(TransactionType.BASE)
    @Transactional
    @Override
    public void inventoryReductionsByBase(Long goodId) {
        for (int i = 0; i < 2; i++) {
            // 正常测试
            goodsJPA.inventoryReduction(goodId, 1);
            // 模拟异常情况。看是否回滚
        }
        // 可以成功
        throw new RuntimeException("测试回滚是否成功");
    }
}
