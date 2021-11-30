package fun.gengzi.codecopy.business.connection.controller;

import fun.gengzi.codecopy.business.connection.entity.MysqlDTO;
import fun.gengzi.codecopy.business.connection.service.CheckMysqlConnectionService;
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
 * <h1>mysql链接检测</h1>
 *
 * @author gengzi
 * @date 2020年7月14日15:58:44
 */
@Api(value = "mysql链接检测", tags = {"mysql链接检测"})
@Controller
@RequestMapping("/mysqlCheckController")
public class MsqlCheckController {

    private Logger logger = LoggerFactory.getLogger(MsqlCheckController.class);

    @Autowired
    private CheckMysqlConnectionService checkMysqlConnectionService;


    /**
     *
     * 测试数据
     * endIndex 2077103104
     * startIndex 2077101544
     *
     *
     * @param startIndex
     * @param endIndex
     * @return
     */
    @ApiOperation(value = "插入数据测试", notes = "插入数据测试")
    @ApiImplicitParams({@ApiImplicitParam(name = "startIndex", value = "ipv4对应的long初始值", required = true),
            @ApiImplicitParam(name = "endIndex", value = "ipv4对应的long结束值", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/testCheckInfo/{startIndex}/{endIndex}")
    @ResponseBody
    public ReturnData testCheckInfo(@PathVariable("startIndex") Long startIndex, @PathVariable("endIndex") Long endIndex) {
        checkMysqlConnectionService.checkMysqlConnectionIsEnable(startIndex, endIndex);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }

    /**
     * 测试数据
     * 123.206.14.49
     * @param ip
     * @return
     */
    @ApiOperation(value = "查询数据", notes = "查询数据-加密列自动解密后返回")
    @ApiImplicitParams({@ApiImplicitParam(name = "ip", value = "ipv4", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/qryMysqlConnectionInfo/{ip}")
    @ResponseBody
    public ReturnData qryMysqlConnectionInfo(@PathVariable("ip") String ip) {
        List<MysqlDTO> mysqlDTOInfo = checkMysqlConnectionService.getMysqlDTOInfo(ip);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(mysqlDTOInfo);
        return ret;
    }


}
