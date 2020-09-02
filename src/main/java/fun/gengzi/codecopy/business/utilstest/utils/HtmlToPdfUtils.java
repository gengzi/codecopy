package fun.gengzi.codecopy.business.utilstest.utils;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;


import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.http.HttpRequest;
import com.itextpdf.text.pdf.BaseFont;
import com.lowagie.text.DocumentException;
import com.vip.vjtools.vjkit.io.FileUtil;
import fun.gengzi.codecopy.exception.RrException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;

/**
 * <h1>Html转pdf工具</h1>
 * 使用 itextpdf 类库实现
 *
 *
 * <dependency>
 * <groupId>org.xhtmlrenderer</groupId>
 * <artifactId>flying-saucer-pdf</artifactId>
 * <version>9.0.7</version>
 * </dependency>
 *
 * <dependency>
 * <groupId>com.itextpdf</groupId>
 * <artifactId>itext-asian</artifactId>
 * <version>5.2.0</version>
 * </dependency>
 * <!-- https://mvnrepository.com/artifact/com.itextpdf/itextpdf -->
 * <dependency>
 * <groupId>com.itextpdf</groupId>
 * <artifactId>itextpdf</artifactId>
 * <version>5.5.13</version>
 * </dependency>
 */
@Component
public class HtmlToPdfUtils {

    private static final Logger logger = LoggerFactory.getLogger(HtmlToPdfUtils.class);

    private HtmlToPdfUtils() {
    }

    static {
        logger.info("HtmlToPdfUtils初始化");
        ITextRenderer iTextRenderer = new ITextRenderer();
        ITextFontResolver fontResolver = iTextRenderer.getFontResolver();
        try {
            fontResolver.addFont(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("font/simsun.ttf")).getPath(),
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        logger.info("HtmlToPdfUtils完成");
    }

    /**
     * 使用wkhtmltopdf 将html转pdf
     * <p>
     * 注意，需要安装  wkhtmltopdf 该软件
     * 需要将 wkhtmltopdf 配置到环境变量中，以便使用
     * 在测试时，注意当前执行main方法的环境变量中，是否包含了 wkhtmltopdf 的配置
     * idea会读取系统环境变量的配置，但是刚修改的好像不能及时更新，可以重新idea 来帮助其更新配置
     * 具体，可点击edit configuration 配置运行界面，的 Enviaorment variables 中查看
     * <p>
     * 页面信息越复杂，内容越多，转换时间越慢
     *
     * @param url     网址
     * @param pdfPath 路径
     */
    public static void WKhtmlTextToPdfBy(String url, String pdfPath) {
        TimeInterval timer = DateUtil.timer();
        if (StringUtils.isAnyBlank(url)) {
            throw new RrException("url参数或pdfpath参数缺少！");
        }
        String execStr = "cmd.exe /c wkhtmltopdf  %s  %s ";
        execStr = String.format(execStr, url, pdfPath);
        logger.info("execStr:{}", execStr);
        List<String> infos = RuntimeUtil.execForLines(execStr);
        infos.forEach(info -> logger.info("命令执行结果：{}", info));
        logger.info("转换耗时(毫秒)：{}", timer.interval());
    }

    /**
     * 使用Chrome  Headless 无头浏览器，完成对html转换pdf
     *
     * 需要安装 goole chrome 浏览器，版本在 60 以上，linux 在 59以上
     * 调用命令实现转换
     *
     *
     *
     * @param url     网址
     * @param pdfPath 路径
     */
    public static void ChromhtmlTextToPdfBy(String url, String pdfPath) {
        TimeInterval timer = DateUtil.timer();
        if (StringUtils.isAnyBlank(url)) {
            throw new RrException("url参数或pdfpath参数缺少！");
        }
        //--window-size=800x1000
        // --virtual-time-budget=10000
        String execStr = "cmd.exe /c chrome --headless --run-all-compositor-stages-before-draw  --disable-gpu --print-to-pdf=\"%s\"  %s";
        execStr = String.format(execStr, pdfPath, url);
        logger.info("execStr:{}", execStr);
        List<String> infos = RuntimeUtil.execForLines(execStr);
        infos.forEach(info -> logger.info("命令执行结果：{}", info));
        logger.info("转换耗时(毫秒)：{}", timer.interval());
    }


    /**
     * html文本转换成PDF
     * <p>
     * 由于 ITextpdf 对html 检测非常严格，html 头部必须声明
     * <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
     * <html lang="en" xmlns="http://www.w3.org/1999/xhtml">
     * <p>
     * 以及其他的都要加</>结束，所以一般的页面都将不支持 转换 pdf
     *
     * @param url            网址
     * @param pdfPath        pdf生成路径
     * @param fontFamilyEnum {@link FontFamilyEnum}  中文字体信息
     */
    public static void htmlTextToPdf(URL url, String pdfPath, FontFamilyEnum fontFamilyEnum) {
        String body = HttpRequest.get(url.toString()).execute().body();
        htmlTextToPdf(body, pdfPath, fontFamilyEnum);
    }


    /**
     * html文本转换成PDF
     *
     * @param file           html 文件
     * @param pdfPath        pdf生成路径
     * @param fontFamilyEnum {@link FontFamilyEnum}  中文字体信息
     */
    public static void htmlTextToPdf(File file, String pdfPath, FontFamilyEnum fontFamilyEnum) {
        try {
            htmlTextToPdf(FileUtil.toString(file), pdfPath, fontFamilyEnum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * html文本转换成PDF
     *
     * @param htmlText       html字符串
     * @param pdfPath        pdf生成路径
     * @param fontFamilyEnum {@link FontFamilyEnum}  中文字体信息
     */
    public static void htmlTextToPdf(String htmlText, String pdfPath, FontFamilyEnum fontFamilyEnum) {
        logger.info("html转pdf入参,htmltext: {} , pdfpath:{}", htmlText, pdfPath);
        // 为了支持中文，检查html内容中是否包含 font-family:SimSun
        boolean fontFamilyFlag = htmlText.contains("font-family");
        boolean fontNameFlag = htmlText.contains(fontFamilyEnum.getFontName());
        if (!fontFamilyFlag || !fontNameFlag) {
            logger.warn("--请注意，当前转换的html未包含字体文件，中文可能转换失败--");
        }
        try {
            TimeInterval timer = DateUtil.timer();
            OutputStream outputStream = new FileOutputStream(pdfPath);
            ITextRenderer iTextRenderer = new ITextRenderer();
            //解决中文字体，需要单独下载字体
            //同时在前端样式中加入font-family:SimSun;
            ITextFontResolver fontResolver = iTextRenderer.getFontResolver();
            fontResolver.addFont(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("font/" + fontFamilyEnum.fontFileName)).getPath(),
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            iTextRenderer.setDocumentFromString(htmlText);
            iTextRenderer.layout();
            iTextRenderer.createPDF(outputStream);
            logger.info("转换耗时(毫秒)：{}", timer.interval());
            outputStream.close();
        } catch (IOException | DocumentException e) {
            throw new RrException("PDF转换异常", e);
        }
    }

    /**
     * 字体枚举类
     */
    public enum FontFamilyEnum {

        SIMSUN("SimSun", "simsun.ttf");

        // 字体名称
        private String fontName;
        // 字体文件
        private String fontFileName;

        FontFamilyEnum(String fontName, String fontFileName) {
            this.fontName = fontName;
            this.fontFileName = fontFileName;
        }

        public String getFontName() {
            return fontName;
        }

        public String getFontFileName() {
            return fontFileName;
        }
    }

    public static void main(String[] args) {

//        HtmlToPdfUtils.htmlTextToPdf("<!DOCTYPE html>" +
//                "<html lang=\"cn\">" +
//                "<head>" +
//                "    <meta charset=\"utf-8\"/>" +
//                "    <title>FreeMarker</title>" +
//                "</head>" +
//                "<body style=\"font-family:SimSun; \">" +
//                "<p><input type=\"checkbox\" checked=\"checked\" disabled=\"disabled\" name=\"vehicle\" value=\"Bike\" /> I have a bike</p>" +
//                "<h1>hello world刘宁233333</h1>" +
//                "<h1 style=\"color: red\">减法的南方科大fdsafaf是农发321321</h1>" +
//                "</body>" +
//                "</html>", "D:\\jeecg\\2.pdf", HtmlToPdfUtils.FontFamilyEnum.SIMSUN);

//        URL url = null;
//        try {
//            url = new URL("http://localhost:8089/swagger-ui.html#/");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        HtmlToPdfUtils.htmlTextToPdf(url, "D:\\jeecg\\3.pdf", HtmlToPdfUtils.FontFamilyEnum.SIMSUN);

//        WKhtmlTextToPdfBy("https://blog.csdn.net/qq_14873105/article/details/51394026", "D://jeecg/5.pdf");

//          ChromhtmlTextToPdfBy("https://www.csdn.net/", "D://jeecg/xx1.pdf");
    }


}