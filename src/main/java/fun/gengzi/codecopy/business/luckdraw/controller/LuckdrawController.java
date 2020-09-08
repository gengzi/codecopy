package fun.gengzi.codecopy.business.luckdraw.controller;

import fun.gengzi.codecopy.business.luckdraw.entity.TbActivity;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * <h1>luckdraw</h1>
 *
 * @author gengzi
 * @date 2020年9月7日14:56:38
 */
@Api(value = "luckdraw", tags = {"luckdraw"})
@Controller
@RequestMapping("/luckdraw")
public class LuckdrawController {


    /**
     * 发起抽奖接口，返回抽奖结果
     * 查询当前用户的抽奖记录和发放记录
     * 查看当前用户获得的奖品
     */


    private Logger logger = LoggerFactory.getLogger(LuckdrawController.class);


    @ApiOperation(value = "xx", notes = "xx")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "TbActivity", value = "TbActivity", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/luckdraw")
    @ResponseBody
    public ReturnData luckdraw(@RequestParam("aid") String aid, @RequestBody TbActivity tbActivity) {
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }

//    @PostMapping("/error")
//    @ResponseBody
//    public ReturnData error() {
//        ReturnData ret = ReturnData.newInstance();
//        ret.setFailure("活动已经过期！");
//        return ret;
//    }


}

