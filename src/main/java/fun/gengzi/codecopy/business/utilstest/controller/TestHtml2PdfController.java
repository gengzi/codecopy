package fun.gengzi.codecopy.business.utilstest.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import fun.gengzi.codecopy.business.utilstest.utils.HtmlToPdfUtils;
import fun.gengzi.codecopy.vo.ReturnData;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 在spring boot 中使用itext和itextrender生成pdf文件
 * https://www.cnblogs.com/majianming/p/9539376.html
 *
 * PDF技术（四）-Java实现Html转PDF文件
 * https://blog.csdn.net/qq_34190023/article/details/82999702
 *
 *
 *
 */
@Api(value = "测试html转pdf", tags = {"测试html转pdf"})
@Controller
@RequestMapping("/testHtml2Pdf")
public class TestHtml2PdfController {

    private Logger logger = LoggerFactory.getLogger(TestHtml2PdfController.class);


    @ApiOperation(value = "测试耗时在哪里", notes = "测试耗时在哪里")
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/test")
    @ResponseBody
    public ReturnData test() {
        TimeInterval timer = DateUtil.timer();
        HtmlToPdfUtils.textToPdf("<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"utf-8\"/>" +
                "    <title>FreeMarker</title>" +
                "</head>" +
                "<body style=\"font-family: Arial\">" +
                "<p><input type=\"checkbox\" checked=\"checked\" disabled=\"disabled\" name=\"vehicle\" value=\"Bike\" /> I have a bike</p>" +
                "<h1>hello world刘宁233333</h1>" +
                "<h1 style=\"color: red\">古人说“桂林山水甲天下。”</h1>" +
                "</body>" +
                "</html>", "D:\\jeecg\\1.pdf");

        logger.info("耗时：{}",timer.interval());

        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }

}
