package fun.gengzi.codecopy.business.subdata.controller;

import fun.gengzi.codecopy.business.subdata.dao.BussinessTableDao;
import fun.gengzi.codecopy.business.subdata.dao.BussinessTableDaoExtendsJPA;
import fun.gengzi.codecopy.business.subdata.dao.DicListDao;
import fun.gengzi.codecopy.business.subdata.entity.BussinessTable;
import fun.gengzi.codecopy.business.subdata.entity.DicList;
import fun.gengzi.codecopy.business.subdata.service.DicListService;
import fun.gengzi.codecopy.business.subdata.service.SubDataService;
import fun.gengzi.codecopy.business.subdata.vo.BussinessTableToDicVo;
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

    @Autowired
    private DicListDao dicListDao;

    @Autowired
    private DicListService dicListService;


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


    /**
     * 数据迁移策略：
     * 在分库分表之前，数据库表已经包含了大量数据，在服务不停机的前提下，需要实现 双写方案，
     * 业务前期，双写，还是使用旧的服务和数据
     * 业务中期，双写，使用新的服务和数据
     * 业务后期，单写，使用新的服务和数据
     * <p>
     * 数据迁移程序，读旧库，走新服务，插入新库
     *
     * @param bussinessTable
     * @return
     */
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

    /**
     * 分页查询 往往伴随着，根据某些字段的查询或者模糊查询
     * 考虑到分库分表之后，不是根据分片字段的查询，都会走全量查询，也就是会查询所有分库分表的表
     * 为了避免这种全量查询，考虑将分页和模糊查询，都是用es 来实现
     * 对于，需要关联表查询的sql（left join），尽量把 left join 消除，需要业务来确定。 在某些场景下，不展示某些数据信息
     * 对于，无法避免关联查询的sql ，需要重新规划sql，将sql 查询结果，在内存中拼接处理
     * <p>
     * 为了使得分库分表的顺利进行，需要将 触发器，存储过程 什么的消除。
     *
     * @param bussinessTableVo
     * @return
     */
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


    @ApiOperation(value = "全局表-插入一条数据", notes = "全局表-插入一条数据-验证配置的全局表，" +
            "插入一条数据，能否在所有库中都插入，答案是可以的")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dic_vo", value = "dic请求实体", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/saveDiclist")
    @ResponseBody
    public ReturnData saveDiclist(@RequestBody DicList dicList) {
//        DicList save = dicListDao.save(dicList);
        DicList dicList1 = dicListService.insertDicList(dicList);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(dicList1);
        return ret;
    }


    @ApiOperation(value = "全局表-删除一条数据", notes = "全局表-删除一条数据-验证配置的全局表，" +
            "删除一条数据，能否在所有库中都删除，答案是可以的")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/delDicListInfo/{id}")
    @ResponseBody
    public ReturnData saveDiclist(@PathVariable("id") Integer id) {
        dicListDao.deleteById(id);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }


    @ApiOperation(value = "全局表-业务表-查询", notes = "全局表-业务表-查询，" +
            "将业务表中的字典code，都转换为字典的对应中文")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "业务表id", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/qryBussinessInfo/{id}")
    @ResponseBody
    public ReturnData qryBussinessInfo(@PathVariable("id") Long id) {
        List<Map<String,Object>> bussinessInfoAndDicInfo = subDataService.getBussinessInfoAndDicInfo(id);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(bussinessInfoAndDicInfo);
        return ret;
    }



}
