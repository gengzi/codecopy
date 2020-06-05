package fun.gengzi.codecopy.business.authentication.controller;

import fun.gengzi.codecopy.business.authentication.entity.RequestParamEntity;
import fun.gengzi.codecopy.business.authentication.service.AuthenticationService;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>接口鉴权controller</h1>
 *
 * @author gengzi
 * @date 2020年6月5日10:42:08
 */
@Api(value = "接口鉴权", tags = {"接口鉴权"})
@Controller
@RequestMapping("/api/v1")
public class AuthenticationController {
    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @ApiOperation(value = "校验token", notes = "校验token")
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
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        // 校验token 是否有效
        Boolean validToken = authenticationService.isValidToken(token);
        if(validToken){
            // 校验签名
            Boolean validSign = authenticationService.isValidSign(requestParamEntity);
            if(validSign){
                ret.setSuccess();
                ret.setMessage("success");
                return ret;
            }
        }
        ret.setFailure("failure");
        return ret;
    }

}
