package fun.gengzi.codecopy.business.authentication.controller;

import fun.gengzi.codecopy.business.authentication.entity.RequestParamEntity;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;

/**
 * <h1>接口安全实践</h1>
 *
 * 业务场景： 短信验证码
 *
 *
 * @author gengzi
 * @date 2020年7月30日16:37:41
 */
@Api(value = "接口安全", tags = {"接口安全"})
@Controller
@RequestMapping("/api/v1")
public class SecurityInterfaceController {
    private Logger logger = LoggerFactory.getLogger(SecurityInterfaceController.class);


    /**
     * 反爬虫，不透露接口入参真实值，防止中间人截取 真实数据
     * @param requestParamEntity
     * @param request
     * @return
     */
    @ApiOperation(value = "接口参数加密与解密", notes = "接口参数加密与解密")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "RequestParamEntity", value = "请求参数实体", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"success\",\n" +
            "\t}\n")})
    @PostMapping("/validToken")
    @ResponseBody
    public ReturnData validToken(@RequestBody RequestParamEntity requestParamEntity, HttpServletRequest request) {



        ReturnData ret = ReturnData.newInstance();
        ret.setFailure("failure");
        return ret;
    }

}
