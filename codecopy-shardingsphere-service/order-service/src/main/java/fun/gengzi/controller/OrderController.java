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
 * 订单管理
 *
 * @author gengzi
 * @date 2022年1月12日13:55:44
 */
@Api(value = "订单管理", tags = {"订单管理"})
@Controller
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(OrderController.class);


    @Autowired
    private PayOrderRecordJPA payOrderRecordJPA;


    @ApiOperation(value = "新增订单", notes = "新增订单")
    @PostMapping("/save")
    @ResponseBody
    public ReturnData save(@RequestBody GoodsVo good) {
        logger.info("queryInfo入参：{}",good);
        GoodsEntity goodsEntity = new GoodsEntity();
        BeanUtils.copyProperties(good, goodsEntity);

        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage("");
        return ret;
    }


}
