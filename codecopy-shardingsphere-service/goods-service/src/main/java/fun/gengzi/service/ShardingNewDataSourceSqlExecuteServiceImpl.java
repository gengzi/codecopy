package fun.gengzi.service;

import fun.gengzi.dao.GoodsJPA;
import fun.gengzi.entity.GoodsEntity;
import fun.gengzi.enums.ShardingDataSourceType;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShardingNewDataSourceSqlExecuteServiceImpl implements ShardingNewDataSourceSqlExecuteService{



    @Autowired
    private GoodsJPA goodsJPA;

    @Override
    @Async("asyncOneThreadPool")
    public Object asyncSqlExecute(String name) {
        HintManager hintManager = HintManager.getInstance();
        hintManager.setDatabaseShardingValue(ShardingDataSourceType.TYPE_NEW.getType());
        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setId(99L);
        goodsEntity.setGoodsName("test");
        goodsJPA.save(goodsEntity);
        return null;
    }
}
