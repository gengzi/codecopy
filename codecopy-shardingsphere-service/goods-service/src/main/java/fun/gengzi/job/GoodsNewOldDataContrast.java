package fun.gengzi.job;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import fun.gengzi.aspect.JpaThreadLocalManager;
import fun.gengzi.codecopy.vo.ReturnData;
import fun.gengzi.dao.GoodsJPA;
import fun.gengzi.dao.GoodsShardingJPA;
import fun.gengzi.entity.GoodsEntity;
import fun.gengzi.enums.ShardingDataSourceType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <h1>每日job</h1>
 * <p>
 * <p>
 * 》》 不引入定时任务了，手动执行
 * <p>
 * 用于每日排查出现问题的数据
 *
 * @author gengzi
 * @date 2021年12月30日10:34:37
 */
@Api(value = "新旧库数据对比", tags = {"新旧库数据对比"})
@Controller
@Slf4j
public class GoodsNewOldDataContrast {

    @Autowired
    private GoodsJPA goodsJPA;

    @Autowired
    private GoodsShardingJPA goodsShardingJPA;

    @ApiOperation(value = "数据对比", notes = "数据对比")
    @GetMapping("/checkData")
    @ResponseBody
    public ReturnData checkData() {
        Date date = new Date();
        List<Long> longs = goodsShardingJPA.queryIdByUpdate(DateUtil.beginOfDay(date).toString(), DateUtil.endOfDay(date).toString());
        JpaThreadLocalManager instance = JpaThreadLocalManager.getInstance();
        for (Long id : longs) {
            instance.setDatabaseShardingValue(ShardingDataSourceType.TYPE_OLD.getType());
            Optional<GoodsEntity> oldGoodsEntity = goodsJPA.findById(id);
            GoodsEntity oldEntity = oldGoodsEntity.orElseThrow(() -> new DataException("数据不存在", new SQLException()));
            instance.clearShardingValues();
            instance.setDatabaseShardingValue(ShardingDataSourceType.TYPE_NEW.getType());
            Optional<GoodsEntity> newGoodEntiy = goodsJPA.findById(id);
            GoodsEntity newEntity = newGoodEntiy.orElseThrow(() -> new DataException("数据不存在", new SQLException()));
            instance.clearShardingValues();
            boolean equal = ObjectUtil.equal(oldEntity, newEntity);
            log.info("对比结果：{}", equal);
            // 对比失败，旧库覆盖新库数据
            if(!equal){
                log.error("数据对比失败，请检查");
                instance.setDatabaseShardingValue(ShardingDataSourceType.TYPE_NEW.getType());
                goodsJPA.save(oldEntity);
                instance.clearShardingValues();
            }
        }
        instance.close();
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }


}
