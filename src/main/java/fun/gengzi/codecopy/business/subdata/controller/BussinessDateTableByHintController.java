package fun.gengzi.codecopy.business.subdata.controller;

import fun.gengzi.codecopy.business.subdata.dao.BussinessDateTableAndUseridDaoExtendsJPA;
import fun.gengzi.codecopy.business.subdata.entity.BussinessTable;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.apache.shardingsphere.api.hint.HintManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * sharding jdbc 测试接口
 *
 * @author gengzi
 * @date 2020年7月10日14:04:37
 */
@Api(value = "sharding jdbc 测试接口-自定义分片算法-hint分片", tags = {"sharding jdbc 测试接口-自定义分片算法-hint分片-按照额外的数据分片"})
@Controller
@RequestMapping("/hint")
public class BussinessDateTableByHintController {

    private Logger logger = LoggerFactory.getLogger(BussinessDateTableByHintController.class);

    @Autowired
    private BussinessDateTableAndUseridDaoExtendsJPA bussinessTableDao;


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
    @ApiOperation(value = "插入一条数据", notes = "插入一条数据，通过传入的userid 来控制分库分表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "BussinessDateTable", value = "请求实体", required = true),
            @ApiImplicitParam(name = "userid", value = "用户id", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/insertBussinessDateInfoByHint/{userid}")
    @ResponseBody
    public ReturnData insertInfo(@RequestBody BussinessTable bussinessTable, @PathVariable("userid") Integer userid) {
        // 必须使用HintManager，才能设置 hint 分片
        /**
         * 通过传入的 userid 来控制，分库分表 的选择
         * 需要使用 HintManager 来控制分库分表的流程，可以是多个参数
         *
         *
         */
        try {
            // 分片键值保存在ThreadLocal中，所以需要在操作结束时调用hintManager.close()来清除ThreadLocal中的内容
            // hintManager实现了AutoCloseable接口，可推荐使用try with resource自动关闭。
            final HintManager hintManager = HintManager.getInstance();
            hintManager.addDatabaseShardingValue("t_bussiness", userid);
            hintManager.addTableShardingValue("t_bussiness", userid);
            BussinessTable save = bussinessTableDao.save(bussinessTable);
            ReturnData ret = ReturnData.newInstance();
            ret.setSuccess();
            ret.setMessage(save);
            return ret;
        } catch (Exception e) {
            // 也可以不写 catch
            throw new RuntimeException(e.getMessage(), e);
        }

    }


}
