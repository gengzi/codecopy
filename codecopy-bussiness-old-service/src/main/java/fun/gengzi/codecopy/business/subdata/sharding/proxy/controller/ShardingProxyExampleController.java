package fun.gengzi.codecopy.business.subdata.sharding.proxy.controller;

import fun.gengzi.codecopy.business.subdata.entity.BussinessTable;
import fun.gengzi.codecopy.business.subdata.sharding.proxy.service.ShardingProxyService;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * <h1>sharding proxy 测试接口</h1>
 *
 * @author gengzi
 * @date 2020年6月30日15:18:44
 */
@Api(value = "sharding proxy 测试接口", tags = {"sharding proxy 测试接口"})
@Controller("/proxy")
public class ShardingProxyExampleController {

    private Logger logger = LoggerFactory.getLogger(ShardingProxyExampleController.class);

    @Autowired
    private ShardingProxyService shardingProxyService;


    /**
     * 测试数据： { "name": "测试proxy" }
     * @param bussinessTable
     * @return
     */
    @ApiOperation(value = "插入一条数据", notes = "验证操作proxy，能否跟sharding-jdbc一样，是一样的")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "BussinessTable", value = "BussinessTable", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/insertBussinessInfo")
    @ResponseBody
    public ReturnData insertBussinessInfo(@RequestBody BussinessTable bussinessTable) {
        BussinessTable bussinessTable1 = shardingProxyService.insertBussinessData(bussinessTable);
        Long enterpriseInfoId = bussinessTable1.getId();
        // 并不会返回 id ，但是数据是插入成功
        logger.info("获取的业务数据id：{}", enterpriseInfoId);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(bussinessTable1);
        return ret;
    }
}
