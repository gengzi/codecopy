package fun.gengzi.controller;

import fun.gengzi.aspect.JpaHintSharding;
import fun.gengzi.codecopy.vo.ReturnData;
import fun.gengzi.dao.GoodsJPA;
import fun.gengzi.dao.GoodsShardingJPA;
import fun.gengzi.entity.GoodsEntity;
//import fun.gengzi.olddao.GoodsOldJPA;
import fun.gengzi.enums.ShardingDataSourceType;
import fun.gengzi.service.ShardingNewDataSourceSqlExecuteService;
import fun.gengzi.vo.GoodsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Api(value = "商品管理-新旧数据库", tags = {"商品管理-新旧数据库"})
@Controller
@RequestMapping("twoWriter")
public class GoodsDoubleWriteController {

    private Logger logger = LoggerFactory.getLogger(GoodsDoubleWriteController.class);

    @Autowired
    private GoodsJPA goodsJPA;

    @Autowired
    @Qualifier("goodsShardingJPA")
    private GoodsShardingJPA goodsShardingJPA;

//    @Autowired
//    private ShardingNewDataSourceSqlExecuteService service;


    @ApiOperation(value = "新增商品信息", notes = "新增商品信息")
    @PostMapping("/savegood")
    @ResponseBody
    public ReturnData savegood(@RequestBody GoodsVo good) {
        logger.info("savegood入参：{}",good);
        GoodsEntity goodsEntity = new GoodsEntity();
        BeanUtils.copyProperties(good, goodsEntity);
        // 新旧数据库选择
        HintManager hintManager = HintManager.getInstance();
        hintManager.setDatabaseShardingValue(ShardingDataSourceType.TYPE_OLD.getType());
        GoodsEntity save = goodsJPA.save(goodsEntity);
        hintManager.clearShardingValues();

        goodsShardingJPA.save(goodsEntity);
//        service.asyncSqlExecute("test");
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(save);
        return ret;
    }

    @ApiOperation(value = "根据商品id减库存", notes = "根据商品id减库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodid", value = "商品id", required = true),
            @ApiImplicitParam(name = "num", value = "减少数目", required = true)})

    @PostMapping("/inventoryReduction")
    @ResponseBody
    public ReturnData inventoryReduction(@RequestParam("goodid") Long goodid ,@RequestParam("num") Integer num) {
        logger.info("inventoryReduction入参：商品id{}，减少数目{}", goodid, num);
        GoodsEntity save = goodsJPA.inventoryReduction(goodid, num);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(save);
        return ret;
    }


    @ApiOperation(value = "查询单个商品信息", notes = "查询单个商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodid", value = "商品id", required = true)})
    @PostMapping("/queryInfo")
    @ResponseBody
    public ReturnData queryInfo(@RequestParam("goodid") Long goodid) {
        logger.info("queryInfo入参：商品id{}", goodid);
        Optional<GoodsEntity> goodsEntity = goodsJPA.findById(goodid);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(goodsEntity);
        return ret;
    }




    // TODO 配置双数据源

    //TODO 历史数据导入






}
