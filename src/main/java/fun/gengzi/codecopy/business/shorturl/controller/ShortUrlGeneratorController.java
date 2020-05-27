package fun.gengzi.codecopy.business.shorturl.controller;

import fun.gengzi.codecopy.business.payment.controller.PaymentActionController;
import fun.gengzi.codecopy.business.shorturl.service.ShortUrlGeneratorService;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <h1>将长链接转换为短链接</h1>
 *
 * @author gengzzi
 * @date 2020年5月26日12:09:16
 */
@Api(value = "shorturl生成接口", tags = {"shorturl生成接口"})
@Controller
public class ShortUrlGeneratorController {


    private Logger logger = LoggerFactory.getLogger(PaymentActionController.class);

    // 使用一般发号器实现
    @Autowired
    @Qualifier("shortUrlGeneratorServiceImpl")
    private ShortUrlGeneratorService shortUrlGeneratorService;

    // 使用分段发号器实现
    @Autowired
    @Qualifier("shortUrlSubGeneratorServiceImpl")
    private ShortUrlGeneratorService shortUrlSubGeneratorServiceImpl;



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


}
