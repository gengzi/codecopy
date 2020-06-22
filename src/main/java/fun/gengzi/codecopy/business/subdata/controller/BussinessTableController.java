package fun.gengzi.codecopy.business.subdata.controller;

import fun.gengzi.codecopy.business.subdata.dao.BussinessTableDao;
import fun.gengzi.codecopy.business.subdata.dao.BussinessTableDaoImpl;
import fun.gengzi.codecopy.business.subdata.entity.BussinessTable;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.util.*;
import java.util.function.Function;


@Api(value = "sharding jdbc 测试接口", tags = {"sharding jdbc 测试接口"})
@Controller
public class BussinessTableController {

    private Logger logger = LoggerFactory.getLogger(BussinessTableController.class);

    @Autowired
    private BussinessTableDao bussinessTableDao;


    @ApiOperation(value = "查询业务信息", notes = "查询业务信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filed", value = "字段", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/queryInfo")
    @ResponseBody
    public ReturnData queryInfo(@RequestParam("filed") String filed) {
//        Optional<BussinessTable> byId = bussinessTableDao.findById(filed);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
//        ret.setMessage(byId.orElse(null));
        return ret;
    }

    @ApiOperation(value = "插入一条数据", notes = "插入一条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bussinessTable", value = "请求实体", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/insertInfo")
    @ResponseBody
    public ReturnData insertInfo(@RequestBody BussinessTable bussinessTable) {
        bussinessTableDao.insert(bussinessTable);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }


}
