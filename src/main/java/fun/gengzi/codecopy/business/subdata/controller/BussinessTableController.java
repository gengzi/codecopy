package fun.gengzi.codecopy.business.subdata.controller;

import fun.gengzi.codecopy.business.subdata.dao.BussinessTableDao;
import fun.gengzi.codecopy.business.subdata.dao.BussinessTableDaoExtendsJPA;
import fun.gengzi.codecopy.business.subdata.entity.BussinessTable;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;


/**
 * sharding jdbc 测试接口
 *
 * @author gengzi
 * @date 2020年6月23日13:49:42
 */
@Api(value = "sharding jdbc 测试接口", tags = {"sharding jdbc 测试接口"})
@Controller
public class BussinessTableController {

    private Logger logger = LoggerFactory.getLogger(BussinessTableController.class);

    @Autowired
    private BussinessTableDao bussinessTableDao;

    @Autowired
    private BussinessTableDaoExtendsJPA bussinessTableDaoExtendsJPA;

    @Autowired
    private SubDataService subDataService;


    @ApiOperation(value = "根据主键查询业务信息", notes = "根据主键查询业务信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/queryInfo")
    @ResponseBody
    public ReturnData queryInfo(@RequestParam("id") Long id) {
//        BussinessTable one = BussinessTableDaoExtendsJPA.getOne(filed);
//        Object bussinessTableById = bussinessTableDao.getBussinessTableById(id);
        BussinessTable bussinessTbaleInfo = subDataService.getBussinessTbaleInfo(id);

        Long enterpriseInfoId = bussinessTbaleInfo.getId();
        logger.info("获取的业务数据id：{}", enterpriseInfoId);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(bussinessTbaleInfo);
        return ret;
    }

    /**
     * 测试数据：
     * {"name":"ff","createdate":"2020-6-23 10:40:50","dataVersion":1}
     *
     * @param bussinessTable
     * @return
     */
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

    @ApiOperation(value = "根据名称查询业务信息", notes = "根据名称查询业务信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/queryInfoByName")
    @ResponseBody
    public ReturnData queryInfoByName(@RequestParam("name") String name) {
        List<BussinessTable> byEnterpriseName = bussinessTableDaoExtendsJPA.findByName(name);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(byEnterpriseName);
        return ret;
    }

    @ApiOperation(value = "更新一条数据", notes = "更新一条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "BussinessTable", value = "请求实体", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/updateBussinessInfo")
    @ResponseBody
    public ReturnData updateBussinessInfo(@RequestBody BussinessTable bussinessTable) {
        return insertInfo(bussinessTable);
    }

    @ApiOperation(value = "根据id删除一条数据", notes = "根据id删除一条数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id主键", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/delBussinessInfo/{id}")
    @ResponseBody
    public ReturnData delBussinessInfo(@PathVariable("id") Long id) {
        bussinessTableDaoExtendsJPA.deleteById(id);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "BussinessTableVo", value = "业务数据查询vo", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/qryBussinessInfo")
    @ResponseBody
    public ReturnData qryBussinessInfo(@RequestBody BussinessTableVo bussinessTableVo) {
        // 构建排序字段
        Sort orders = Sort.by(Sort.Direction.DESC, "createdate");
        // 构建分页
        PageRequest pageRequest = PageRequest.of(bussinessTableVo.getCurrentPage(), bussinessTableVo.getPageSize(), orders);
        // 构建查询条件
        Specification<BussinessTable> bussinessTableSpecification = (Specification<BussinessTable>) (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("name"), bussinessTableVo.getName());
        };
        Page<BussinessTable> all = bussinessTableDaoExtendsJPA.findAll(bussinessTableSpecification, pageRequest);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(all);
        return ret;
    }


}
