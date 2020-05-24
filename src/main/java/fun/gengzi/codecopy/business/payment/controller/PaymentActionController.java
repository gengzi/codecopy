package fun.gengzi.codecopy.business.payment.controller;

import javax.annotation.Resource;

import fun.gengzi.codecopy.business.payment.entity.QueryPayStatusInput;
import fun.gengzi.codecopy.vo.ReturnData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <h1>支付模块</h1>
 *
 * @author gengzzi
 * @date 2020年5月23日19:58:58
 */
@Api(value = "支付相关",tags = {"支付相关接口"})
@RestController
@RequestMapping("pay/paymentAction")
public class PaymentActionController {
    private Logger logger = LoggerFactory.getLogger(PaymentActionController.class);

    @ApiOperation(value = "查询支付状态", notes = "查询支付状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "\t{\n" +
                    "\t		\"userID\":\"用户ID\",\n" +
                    "\t		\"payFlowID\":\"支付单号\"\n" +
                    "\t}\n", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
			"\t    \"status\": 200,\n" +
			"\t    \"info\": {\n" +
            "\t			\"nextStepType\":\"下一步操作\",\n" +
            "\t			\"nextStepData\":\"下一步操作结构体\"\n" +
			"\t		}\n"	+
            "\t    \"message\": \"操作成功\",\n" +
			"\t    \"bzcode\": \"\"\n" +
            "\t}\n")})
    @RequestMapping(value = "/queryPayStatus", method = RequestMethod.POST)
    public ReturnData queryPayStatus(@RequestBody QueryPayStatusInput data) {
        logger.info("qryParams {} ",data.toString());
        // 示例
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }


}
