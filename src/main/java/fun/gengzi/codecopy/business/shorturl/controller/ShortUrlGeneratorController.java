package fun.gengzi.codecopy.business.shorturl.controller;

import fun.gengzi.codecopy.aop.BusinessAuthentication;
import fun.gengzi.codecopy.business.payment.controller.PaymentActionController;
import fun.gengzi.codecopy.business.shorturl.entity.Shorturl;
import fun.gengzi.codecopy.business.shorturl.service.PrescriptionShortUrlGeneratorService;
import fun.gengzi.codecopy.business.shorturl.service.ShortUrlGeneratorService;
import fun.gengzi.codecopy.constant.RspCodeEnum;
import fun.gengzi.codecopy.exception.RrException;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * <h1>将长链接转换为短链接</h1>
 *
 * @author gengzzi
 * @date 2020年5月26日12:09:16
 */
@Api(value = "shorturl生成接口", tags = {"shorturl生成接口"})
@Controller
public class ShortUrlGeneratorController {


    private Logger logger = LoggerFactory.getLogger(ShortUrlGeneratorController.class);

    // 使用一般发号器实现
    @Autowired
    @Qualifier("shortUrlGeneratorServiceImpl")
    private ShortUrlGeneratorService shortUrlGeneratorService;

    // 使用分段发号器实现
    @Autowired
    @Qualifier("shortUrlSubGeneratorServiceImpl")
    private ShortUrlGeneratorService shortUrlSubGeneratorServiceImpl;

    @Autowired
    private PrescriptionShortUrlGeneratorService prescriptionShortUrlGeneratorService;



    @ApiOperation(value = "长链接转短链接", notes = "长链接转短链接")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "longurl", value = "长链接", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n"	+
            "\t    \"message\": \"短链接\",\n" +
            "\t}\n")})
    @PostMapping("/getShortUrl")
    @ResponseBody
    public ReturnData generatorShortUrl(@RequestParam("longurl") String longurl) {
        logger.info("getShortUrl start {} ", System.currentTimeMillis());
        String shortUrl = shortUrlGeneratorService.generatorShortUrl(longurl);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(shortUrl);
        return ret;
    }


    @ApiOperation(value = "短链接跳转服务", notes = "短链接跳转服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shorturl", value = "短链接", required = true)})
    @GetMapping("/u/{shorturl}")
    public String redirectUrl(@PathVariable("shorturl") String shorturl) {
        logger.info("redirectUrl start {} ", System.currentTimeMillis());
        String longUrl = shortUrlGeneratorService.getLongUrl(shorturl);
        return "redirect:" + longUrl;
    }


    // ------------------- 分段发号器 ----------------------------------


    @ApiOperation(value = "长链接转短链接", notes = "长链接转短链接")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "longurl", value = "长链接", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n"	+
            "\t    \"message\": \"短链接\",\n" +
            "\t}\n")})
    @PostMapping("/getShortUrlSub")
    @ResponseBody
    public ReturnData generatorShortUrlBySub(@RequestParam("longurl") String longurl) {
        logger.info("getShortUrl start {} ", System.currentTimeMillis());
        String shortUrl = shortUrlSubGeneratorServiceImpl.generatorShortUrl(longurl);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(shortUrl);
        return ret;
    }

    // ------------------- 时效短链接 ----------------------------------

    @ApiOperation(value = "长链接转短链接", notes = "长链接转短链接")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "longurl", value = "长链接", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n"	+
            "\t    \"message\": \"短链接\",\n" +
            "\t}\n")})
    @PostMapping("/getPrescriptionShortUrl")
    @ResponseBody
    public ReturnData getPrescriptionShortUrl(@RequestParam("longurl") String longurl) {
        logger.info("getShortUrl start {} ", System.currentTimeMillis());
        Optional<Shorturl> shortUrl = prescriptionShortUrlGeneratorService.getPrescriptionShortUrl(longurl);

        // 校验对象
        Shorturl shorturl = shortUrl.orElseThrow(() -> new RrException("访问接口失败", RspCodeEnum.FAILURE.getCode()));

        Shorturl shorturlRsp = new Shorturl();
        shorturlRsp.setShorturl(null);

//        Shorturl shorturl1 = Optional.ofNullable(shorturlRsp)
//                .orElseThrow(() -> new RrException("访问接口失败", RspCodeEnum.FAILURE.getCode()));
        // 校验对接及字段
        String shortUrlStr = Optional.ofNullable(shorturlRsp).map(s -> s.getShorturl()).filter( s-> StringUtils.isNoneBlank(s)).orElse("转换失败");
        System.out.println(shortUrlStr);

        String shortUrlStr2 = Optional.ofNullable(shorturlRsp).map(s -> s.getShorturl()).filter(s -> StringUtils.isNoneBlank(s)).orElseGet(() -> "生成一个值");


        if(shorturlRsp == null ){
            throw new RrException("访问接口失败", RspCodeEnum.FAILURE.getCode());
        }else{
            if(shorturlRsp.getShorturl() == null){
                throw new RrException("转换失败", RspCodeEnum.FAILURE.getCode());
            }else{
                // xx 执行业务逻辑，如果还需要判断的，还带继续写 判空
            }
        }


        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(shorturl);
        return ret;
    }


    /**
     * 测试接口鉴权
     * @param longurl
     * @return
     */
    @ApiOperation(value = "长链接转短链接-测试接口鉴权", notes = "长链接转短链接-测试接口鉴权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "longurl", value = "长链接", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n"	+
            "\t    \"message\": \"短链接\",\n" +
            "\t}\n")})
    @PostMapping("/getShortUrlByTest")
    @ResponseBody
    @BusinessAuthentication(callNumber = 5)
    public ReturnData testgeneratorShortUrl(@RequestParam("longurl") String longurl) {
        logger.info("getShortUrl start {} ", System.currentTimeMillis());
        String shortUrl = shortUrlGeneratorService.generatorShortUrl(longurl);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        ret.setMessage(shortUrl);
        return ret;
    }

}
