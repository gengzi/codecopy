package fun.gengzi.controller;

import fun.gengzi.codecopy.vo.ReturnData;
import fun.gengzi.dao.GoodsJPA;
import fun.gengzi.entity.GoodsEntity;
import fun.gengzi.vo.GoodsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Api(value = "商品管理", tags = {"商品管理"})
@Controller
public class GoodsController {

    private Logger logger = LoggerFactory.getLogger(GoodsController.class);


    @Autowired
    private GoodsJPA goodsJPA;


    @ApiOperation(value = "新增商品信息", notes = "新增商品信息")
    @PostMapping("/savegood")
    @ResponseBody
    public ReturnData savegood(@RequestBody GoodsVo good) {
        logger.info("savegood入参：{}",good);
        GoodsEntity goodsEntity = new GoodsEntity();
        BeanUtils.copyProperties(good, goodsEntity);

        GoodsEntity save = goodsJPA.save(goodsEntity);
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

    @ApiOperation(value = "查询单个商品信息", notes = "查询单个商品信息")
    @PostMapping("/queryInfo/{currentPage}/{pageSize}")
    @ResponseBody
    public ReturnData queryInfo(@RequestBody GoodsVo good,
                                @PathVariable("currentPage") Integer currentPage,
                                @PathVariable("pageSize") Integer pageSize) {
        logger.info("queryInfo入参：查询条件{},当前页码{},展示条数{}", good,currentPage,pageSize);
        // spring boot 2.0推荐写法
        Pageable pageable = PageRequest.of(currentPage,pageSize);
        Specification<GoodsEntity> specification = (Specification<GoodsEntity>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            Predicate p1 = criteriaBuilder.like(root.get("goodsName"),good.getGoodsName()+"%");
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        Page<GoodsEntity> all = goodsJPA.findAll(specification, pageable);
        logger.info("查询分页入参{}", good);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(all);
        return ret;
    }





}
