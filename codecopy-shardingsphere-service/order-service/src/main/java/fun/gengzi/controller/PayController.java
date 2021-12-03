package fun.gengzi.controller;

import fun.gengzi.codecopy.vo.ReturnData;
import fun.gengzi.dao.PayOrderRecordJPA;
import fun.gengzi.entity.GoodsEntity;
import fun.gengzi.vo.GoodsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


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


    @Autowired
    private PayOrderRecordJPA payOrderRecordJPA;


    @ApiOperation(value = "新增商品信息", notes = "新增商品信息")
    @PostMapping("/queryInfo")
    @ResponseBody
    public ReturnData queryInfo(@RequestBody GoodsVo good) {
        logger.info("queryInfo入参：{}",good);
        GoodsEntity goodsEntity = new GoodsEntity();
        BeanUtils.copyProperties(good, goodsEntity);

        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage("");
        return ret;
    }


}
