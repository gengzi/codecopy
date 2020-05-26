package fun.gengzi.codecopy.business.shorturl.controller;

import fun.gengzi.codecopy.business.payment.controller.PaymentActionController;
import fun.gengzi.codecopy.business.shorturl.service.ShortUrlGeneratorService;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ShortUrlGeneratorService shortUrlGeneratorService;

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

    @GetMapping("/{shorturl}")
    public String redirectUrl(@PathVariable("shorturl") String shorturl) {
        logger.info("redirectUrl start {} ", System.currentTimeMillis());
        String longUrl = shortUrlGeneratorService.getLongUrl(shorturl);
        return "redirect:" + longUrl;
    }


}
