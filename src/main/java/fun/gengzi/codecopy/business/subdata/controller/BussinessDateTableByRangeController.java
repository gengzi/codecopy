package fun.gengzi.codecopy.business.subdata.controller;

import cn.hutool.core.date.DateUtil;
import fun.gengzi.codecopy.business.subdata.dao.BussinessDateTableDaoExtendsJPA;
import fun.gengzi.codecopy.business.subdata.entity.BussinessDateTable;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * <h1>sharding jdbc 测试接口-自定义分片算法-范围分片</h1>
 *
 * @author gengzi
 * @date 2020年7月2日16:17:22
 */
@Api(value = "sharding jdbc 测试接口-自定义分片算法-范围分片", tags = {"sharding jdbc 测试接口-自定义分片算法-范围分片-按照创建时间分片"})
@Controller
@RequestMapping("/range")
public class BussinessDateTableByRangeController {

    private Logger logger = LoggerFactory.getLogger(BussinessDateTableByRangeController.class);

    @Autowired
    private BussinessDateTableDaoExtendsJPA bussinessTableDao;


    /**
     * 测试数据：
     * {"name":"ff","createdate":"2020-6-23 10:40:50","dataVersion":1}
     * <p>
     * <p>
     * {
     * "addresscode": "xx",
     * "code": "xx",
     * "createdate": "2020-06-23 08:10:26",
     * "dataVersion": 1,
     * "diccode": "xx",
     * "guid": "xx",
     * "isDel": 0,
     * "name": "xx",
     * "updatedate": "2020-06-23 08:10:26"
     * }
     *
     * @param bussinessTable
     * @return
     */
    @ApiOperation(value = "插入一条数据", notes = "插入一条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "BussinessDateTable", value = "请求实体", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/insertBussinessDateInfo")
    @ResponseBody
    public ReturnData insertInfo(@RequestBody BussinessDateTable bussinessTable) {
        BussinessDateTable save = bussinessTableDao.save(bussinessTable);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(save);
        return ret;
    }


    @ApiOperation(value = "业务表-查询(针对 between and sql语法)", notes = "业务表-查询(针对 between and sql语法)" +
            "测试是否执行范围分片的算法，答案是肯定的")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startdateStr", value = "开始时间", required = true),
            @ApiImplicitParam(name = "enddateStr", value = "结束时间", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/qryBussinessInfoByDate/{startdateStr}/{enddateStr}")
    @ResponseBody
    public ReturnData qryBussinessInfoByDate(@PathVariable("startdateStr") String startdateStr, @PathVariable("enddateStr") String enddateStr) {
        Date startdate = DateUtil.parse(startdateStr);
        Date enddate = DateUtil.parse(enddateStr);
        List<BussinessDateTable> infobyDate = bussinessTableDao.getInfobyDate(startdate,enddate);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(infobyDate);
        return ret;
    }


}
