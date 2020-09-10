package fun.gengzi.codecopy.business.luckdraw.controller;

import fun.gengzi.codecopy.business.luckdraw.algorithm.LuckdrawAlgorithlm;
import fun.gengzi.codecopy.business.luckdraw.constant.LuckdrawEnum;
import fun.gengzi.codecopy.business.luckdraw.entity.*;
import fun.gengzi.codecopy.business.luckdraw.service.LuckdrawService;
import fun.gengzi.codecopy.business.luckdraw.vo.VerificationVo;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;


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

    @Autowired
    @Qualifier("DefaultLuckdrawAlgorithlm")
    private LuckdrawAlgorithlm luckdrawAlgorithlm;

    @Autowired
    private LuckdrawService luckdrawService;

    @ApiOperation(value = "luckdraw", notes = "luckdraw")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "TbActivity", value = "TbActivity", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/start")
    @ResponseBody
    public ReturnData start(@RequestParam("aid") String aid) {
        logger.info("luckdraw quest param ,aid:{} ", aid);

        LuckdrawAlgorithlmEntity luckdrawAlgorithlmEntity = new LuckdrawAlgorithlmEntity();
        luckdrawAlgorithlmEntity.setActivityId("11");
        ArrayList<Double> probabilityList = new ArrayList<>(10);
        probabilityList.add(0.0001);
        probabilityList.add(0.01);
        probabilityList.add(0.1);
        probabilityList.add(0.2);
        probabilityList.add(0.3);
        probabilityList.add(0.3);
        luckdrawAlgorithlmEntity.setProbabilityList(probabilityList);
        for (int i = 0; i < 1000; i++) {
            Integer algorithlm = luckdrawAlgorithlm.algorithlm(luckdrawAlgorithlmEntity);
            logger.info("{}:{}", i, algorithlm);
        }

        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }


    @ApiOperation(value = "验证用户信息", notes = "验证用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "VerificationVo", value = "VerificationVo", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/verificationUserInfo")
    @ResponseBody
    public ReturnData verificationUserInfo(@RequestParam("aid") String aid, @RequestBody VerificationVo verificationVo, HttpServletResponse response) {
        logger.info("VerificationVo :{} ", verificationVo);
        final ReturnData ret = ReturnData.newInstance();
        if (verificationVo != null && StringUtils.isAnyBlank(verificationVo.getPhone(), verificationVo.getValidCode())) {
            ret.setFailure(LuckdrawEnum.ERROR_DEFAULT.getMsg());
            return ret;
        }
        // TODO 默认校验成功
        boolean flag = true;
        // 根据手机号获取用户信息
        if (flag) {
            SysUserDTO userInfoByPhoneNum = luckdrawService.getUserInfoByPhoneNum(verificationVo.getPhone());
            if (userInfoByPhoneNum != null) {
                // 获取该用户对应该活动的积分信息
                TbIntegral integralInfo = luckdrawService.getIntegralInfo(aid, userInfoByPhoneNum.getUid());
                if (integralInfo != null) {
                    // 返回token 其他数据
                    final TokenInfoEntity tokenInfoEntity = new TokenInfoEntity();
                    tokenInfoEntity.setUname(userInfoByPhoneNum.getUname());
                    tokenInfoEntity.setIntegral(String.valueOf(integralInfo.getIntegral()));
                    // 设置响应头 token
                    response.setHeader(HttpHeaders.AUTHORIZATION, userInfoByPhoneNum.getToken());

                    ret.setSuccess();
                    ret.setInfo(tokenInfoEntity);
                    return ret;
                }
            }
            ret.setFailure(LuckdrawEnum.ERROR_DEFAULT.getMsg());
            return ret;
        } else {
            ret.setFailure(LuckdrawEnum.ERROR_VALIDCODE.getMsg());
            return ret;
        }

    }


}

