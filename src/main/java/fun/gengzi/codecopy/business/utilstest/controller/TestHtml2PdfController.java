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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 在spring boot 中使用itext和itextrender生成pdf文件
 * https://www.cnblogs.com/majianming/p/9539376.html
 * <p>
 * PDF技术（四）-Java实现Html转PDF文件
 * https://blog.csdn.net/qq_34190023/article/details/82999702
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
        HtmlToPdfUtils.htmlTextToPdf("<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"utf-8\"/>" +
                "    <title>FreeMarker</title>" +
                "</head>" +
                "<body style=\"font-family: SimSun\">" +
                "<p><input type=\"checkbox\" checked=\"checked\" disabled=\"disabled\" name=\"vehicle\" value=\"Bike\" /> I have a bike</p>" +
                "<h1>hello world刘宁233333</h1>" +
                "<h1 style=\"color: red\">古人说“桂林山水甲天下。”</h1>" +
                "</body>" +
                "</html>", "D:\\jeecg\\1.pdf", HtmlToPdfUtils.FontFamilyEnum.SIMSUN);

        logger.info("耗时：{}", timer.interval());

        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }


    /**
     * 测试数据
     * <!DOCTYPE html><html lang="en"><head>    <meta charset="utf-8"/>    <title>FreeMarker</title></head><body style="font-family: SimSun"><p><input type="checkbox" checked="checked" disabled="disabled" name="vehicle" value="Bike" /> I have a bike</p><h1>hello world刘宁233333</h1><h1 style="color: red">古人说“桂林山水甲天下。”</h1></body></html>
     *
     * @param htmlStr 页面文本
     * @param pdfPath 保存本地磁盘文件路径和名称
     * @return {@link ReturnData}
     */
    @ApiOperation(value = "使用ITextpdf将html转pdf", notes = "使用ITextpdf将html转pdf")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "htmlStr", value = "页面文本"),
            @ApiImplicitParam(name = "pdfPath", value = "保存本地磁盘文件路径和名称")})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/htmlTextToPdf")
    @ResponseBody
    public ReturnData htmlTextToPdf(@RequestParam("htmlStr") String htmlStr, @RequestParam("pdfPath") String pdfPath) {
        HtmlToPdfUtils.htmlTextToPdf(htmlStr, pdfPath, HtmlToPdfUtils.FontFamilyEnum.SIMSUN);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }


    @ApiOperation(value = "使用wkhtmltopdf转pdf", notes = "使用wkhtmltopdf转pdf")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "网址"),
            @ApiImplicitParam(name = "pdfPath", value = "保存本地磁盘文件路径和名称")})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/wkhtmlTextToPdfBy")
    @ResponseBody
    public ReturnData wkhtmlTextToPdfBy(@RequestParam("url") String url, @RequestParam("pdfPath") String pdfPath) {
        HtmlToPdfUtils.WKhtmlTextToPdfBy(url, pdfPath);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }

    @ApiOperation(value = "使用Chrome  Headless无头浏览器，完成对html转换pdf", notes = "使用Chrome  Headless无头浏览器，完成对html转换pdf")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "网址"),
            @ApiImplicitParam(name = "pdfPath", value = "保存本地磁盘文件路径和名称")})
    @ApiResponses({@ApiResponse(code = 200, message = "\t{\n" +
            "\t    \"status\": 200,\n" +
            "\t    \"info\": {\n" +
            "\t		}\n" +
            "\t    \"message\": \"信息\",\n" +
            "\t}\n")})
    @PostMapping("/chromhtmlTextToPdfBy")
    @ResponseBody
    public ReturnData chromhtmlTextToPdfBy(@RequestParam("url") String url, @RequestParam("pdfPath") String pdfPath) {
        HtmlToPdfUtils.ChromhtmlTextToPdfBy(url, pdfPath);
        ReturnData ret = ReturnData.newInstance();
        ret.setSuccess();
        return ret;
    }


    public static void main(String[] args) {
        String aa = "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"utf-8\"/>" +
                "    <title>FreeMarker</title>" +
                "</head>" +
                "<body style=\"font-family: SimSun\">" +
                "<p><input type=\"checkbox\" checked=\"checked\" disabled=\"disabled\" name=\"vehicle\" value=\"Bike\" /> I have a bike</p>" +
                "<h1>hello world刘宁233333</h1>" +
                "<h1 style=\"color: red\">古人说“桂林山水甲天下。”</h1>" +
                "</body>" +
                "</html>";

        System.out.println(aa);
    }

}

