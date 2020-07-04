package fun.gengzi.codecopy.business.subdata.controller;

import cn.hutool.core.date.DateUtil;
import fun.gengzi.codecopy.business.subdata.dao.BussinessDateAndAddressTableDaoExtendsJPA;
import fun.gengzi.codecopy.business.subdata.entity.BussinessDateAndAddressTable;
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
 * <h1>sharding jdbc 测试接口-自定义分片算法-复合分片</h1>
 *
 * @author gengzi
 * @date 2020年7月2日16:17:22
 */
@Api(value = "sharding jdbc 测试接口-自定义分片算法-复合分片", tags = {"sharding jdbc 测试接口-自定义分片算法-复合分片-按照创建时间和地区编码分片"})
@Controller
@RequestMapping("/complexKeys")
public class BussinessDateTableByComplexKeysController {

    private Logger logger = LoggerFactory.getLogger(BussinessDateTableByComplexKeysController.class);

    @Autowired
    private BussinessDateAndAddressTableDaoExtendsJPA bussinessTableDao;


    /**
     * 测试数据：
     * {"name":"ff","createdate":"2020-6-23 10:40:50","dataVersion":1}
     * <p>
     * <p>
     * {
     * "addresscode": "xx",
     * "code": "xx",
     * "createdate": "2020-01-23 08:10:26",
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
    public ReturnData insertInfo(@RequestBody BussinessDateAndAddressTable bussinessTable) {
        BussinessDateAndAddressTable save = bussinessTableDao.save(bussinessTable);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(save);
        return ret;
    }


    /**
     * 测试数据
     * {
     * "addresscode": "west",
     * "createdate": "2020-01-23 08:10:26"
     * }
     *
     * @param bussinessTable
     * @return
     */
    @ApiOperation(value = "业务表-查询(针对 in = sql语法)", notes = "业务表-查询(针对  in = sql语法)" +
            "看是否执行精确分片的算法，答案是肯定的")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "BussinessDateAndAddressTable", value = "BussinessDateAndAddressTable", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/qryBussinessInfo")
    @ResponseBody
    public ReturnData qryBussinessInfoByDateAndAddress(@RequestBody BussinessDateAndAddressTable bussinessTable) {
        List<BussinessDateAndAddressTable> byCreatedateAndAddresscode = bussinessTableDao.findByCreatedateAndAddresscode(bussinessTable.getCreatedate(), bussinessTable.getAddresscode());
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(byCreatedateAndAddresscode);
        return ret;
    }



}
