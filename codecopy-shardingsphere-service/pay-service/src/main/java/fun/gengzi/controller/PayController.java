package fun.gengzi.controller;

import fun.gengzi.codecopy.vo.ReturnData;
import fun.gengzi.entity.GoodsEntity;
import fun.gengzi.vo.GoodsVo;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 * sharding jdbc 测试接口
 *
 * @author gengzi
 * @date 2020年6月23日13:49:42
 */
@Api(value = "sharding jdbc 测试接口-使用行表达式分片", tags = {"sharding jdbc 测试接口-使用行表达式分片-按照主键id分表和version分库"})
@Controller
public class PayController {

    private Logger logger = LoggerFactory.getLogger(PayController.class);


    @ApiOperation(value = "新增商品信息", notes = "新增商品信息")
    @PostMapping("/queryInfo")
    @ResponseBody
    public ReturnData queryInfo(@RequestBody GoodsVo good) {
        logger.info("queryInfo入参：{}", good);
        GoodsEntity goodsEntity = new GoodsEntity();
        BeanUtils.copyProperties(good, goodsEntity);

//        GoodsEntity save = goodsJPA.save(goodsEntity);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage("");
        return ret;
    }

    @ApiOperation(value = "测试key 的spel表达式 RCE问题", notes = "测试key 的spel表达式 RCE问题")
    @GetMapping("/queryInfotest")
    @ResponseBody
    // 将 key 替换为  new int[1000000][1000000]  执行就会内存溢出 oom
    @Cacheable(cacheManager = "localManager", value = "default",key = "#root.args[0]")
    public String queryInfotest(@RequestParam("test") String test) {
        logger.info("queryInfo入参：{}", test);
        return test.toString();
    }


}
