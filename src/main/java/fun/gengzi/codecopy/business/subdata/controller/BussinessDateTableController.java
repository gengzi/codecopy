package fun.gengzi.codecopy.business.subdata.controller;

import fun.gengzi.codecopy.business.subdata.dao.BussinessDateTableDaoExtendsJPA;
import fun.gengzi.codecopy.business.subdata.dao.BussinessTableDao;
import fun.gengzi.codecopy.business.subdata.dao.BussinessTableDaoExtendsJPA;
import fun.gengzi.codecopy.business.subdata.dao.DicListDao;
import fun.gengzi.codecopy.business.subdata.entity.BussinessDateTable;
import fun.gengzi.codecopy.business.subdata.entity.BussinessTable;
import fun.gengzi.codecopy.business.subdata.entity.DicList;
import fun.gengzi.codecopy.business.subdata.service.DicListService;
import fun.gengzi.codecopy.business.subdata.service.SubDataService;
import fun.gengzi.codecopy.business.subdata.vo.BussinessTableVo;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(value = "sharding jdbc 测试接口2", tags = {"sharding jdbc 测试接口2"})
@Controller
public class BussinessDateTableController {

    private Logger logger = LoggerFactory.getLogger(BussinessDateTableController.class);

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



}