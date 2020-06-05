package fun.gengzi.codecopy.business.authentication.controller;

import cn.hutool.http.Header;
import fun.gengzi.codecopy.business.authentication.constant.AuthenticationConstans;
import fun.gengzi.codecopy.business.authentication.entity.RequestParamEntity;
import fun.gengzi.codecopy.constant.RspCodeEnum;
import fun.gengzi.codecopy.exception.RrException;
import fun.gengzi.codecopy.utils.AESUtils;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

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


    @Value("${token.aeskey}")
    private String aeskey;


    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

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
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        //TODO 校验token 是否有效

        //TODO 校验完成，校验签名



        logger.info("validToken start {} ", System.currentTimeMillis());
        final String reqNum = requestParamEntity.getReqNum();
        final String sign = requestParamEntity.getSign();

        final String reqParams = AESUtils.decrypt(sign, aeskey)
                .orElseThrow(() -> new RrException("认证失败", RspCodeEnum.FAILURE.getCode()));

        String[] split = reqParams.split("&");
        final HashMap<String, String> paramKeyValue = new HashMap<>();
        Arrays.asList(split).forEach(s -> {
            String[] keyvalue = s.split("=");
            paramKeyValue.put(keyvalue[0],keyvalue[1]);
        });

        paramKeyValue.get(AuthenticationConstans.CALLNUMBER);
        String reqNumByMap = paramKeyValue.get(AuthenticationConstans.REQNUM);
        if(StringUtils.isNoneBlank(reqNum) && reqNum.equals(reqNumByMap)){
            // 校验通过 ，鉴权成功
        }
        // 响应一个用户id ，进行 rsa 加密处理


        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage("success");
        logger.info("validToken end {}", System.currentTimeMillis());
        return ret;
    }

}
