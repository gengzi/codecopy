// 弃用

//package fun.gengzi.codecopy.business.security.controller;
//
//import fun.gengzi.codecopy.vo.ReturnData;
//import io.swagger.annotations.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//
//
//@Api(value = "权限校验", tags = {"权限校验"})
//@Controller
//@RequestMapping("/securityBase")
//public class SecurityBaseController {
//
//    private Logger logger = LoggerFactory.getLogger(SecurityBaseController.class);
//
//
//    /**
//     * 必须是管理员，才能访问
//     * @param aid
//     * @return
//     */
//    @ApiOperation(value = "adminApi", notes = "adminApi")
//    @ApiImplicitParams({@ApiImplicitParam(name = "aid", value = "aid", required = true)})
//    @PostMapping("/adminApi")
//    @ResponseBody
//    public ReturnData adminApi(@RequestParam("aid") String aid) {
//        ReturnData ret = ReturnData.newInstance();
//        ret.setSuccess();
//        return ret;
//    }
//
//    /**
//     * 公共访问
//     * @param aid
//     * @return
//     */
//    @ApiOperation(value = "publicApi", notes = "publicApi")
//    @ApiImplicitParams({@ApiImplicitParam(name = "aid", value = "aid", required = true)})
//    @PostMapping("/publicApi")
//    @ResponseBody
//    public ReturnData publicApi(@RequestParam("aid") String aid) {
//        ReturnData ret = ReturnData.newInstance();
//        ret.setSuccess();
//        return ret;
//    }
//
//    /**
//     * 必须鉴权登陆后访问
//     * @param aid
//     * @return
//     */
//    @ApiOperation(value = "userApi", notes = "userApi")
//    @ApiImplicitParams({@ApiImplicitParam(name = "aid", value = "aid", required = true)})
//    @PostMapping("/userApi")
//    @ResponseBody
//    public ReturnData userApi(@RequestParam("aid") String aid) {
//        ReturnData ret = ReturnData.newInstance();
//        ret.setSuccess();
//        return ret;
//    }
//
//
//}
//
