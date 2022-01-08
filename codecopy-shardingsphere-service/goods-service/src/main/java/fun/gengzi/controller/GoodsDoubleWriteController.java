package fun.gengzi.controller;

import fun.gengzi.codecopy.vo.ReturnData;
import fun.gengzi.dao.GoodsShardingJPA;
import fun.gengzi.entity.GoodsEntity;
import fun.gengzi.service.ShardingTransactionGoodsService;
import fun.gengzi.vo.GoodsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shardingsphere.transaction.annotation.ShardingSphereTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Api(value = "商品管理-新旧数据库", tags = {"商品管理-新旧数据库"})
@Controller
@RequestMapping("twoWriter")
public class GoodsDoubleWriteController {

    private Logger logger = LoggerFactory.getLogger(GoodsDoubleWriteController.class);

    @Autowired
    private GoodsShardingJPA goodsJPA;

    @Autowired
    private ShardingTransactionGoodsService shardingTransactionGoodsService;


    @ApiOperation(value = "新增商品信息", notes = "新增商品信息")
    @PostMapping("/savegood")
    @ResponseBody
    public ReturnData savegood(@RequestBody GoodsVo good) {
        logger.info("savegood入参：{}", good);
        GoodsEntity goodsEntity = new GoodsEntity();
        BeanUtils.copyProperties(good, goodsEntity);
        GoodsEntity save = goodsJPA.saveAndFlush(goodsEntity);
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
    public ReturnData inventoryReduction(@RequestParam("goodid") Long goodid, @RequestParam("num") Integer num) {
        logger.info("inventoryReduction入参：商品id{}，减少数目{}", goodid, num);
        goodsJPA.inventoryReduction(goodid, num);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage("");
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

    @ApiOperation(value = "根据商品id减库存--测试shardingjdbc分布式事务", notes = "根据商品id减库存--测试shardingjdbc分布式事务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodid", value = "商品id", required = true),
            @ApiImplicitParam(name = "num", value = "减少数目", required = true)})

    @PostMapping("/inventoryReductionTest")
    @ResponseBody
    public ReturnData inventoryReductionTest(@RequestParam("goodid") Long goodid) {
        logger.info("inventoryReduction入参：商品id{}", goodid);
        shardingTransactionGoodsService.inventoryReductions(goodid);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage("");
        return ret;
    }


}
