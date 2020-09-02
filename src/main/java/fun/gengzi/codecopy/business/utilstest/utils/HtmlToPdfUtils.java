package fun.gengzi.codecopy.business.utilstest.utils;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;


import cn.hutool.http.HttpRequest;
import com.itextpdf.text.pdf.BaseFont;
import com.lowagie.text.DocumentException;
import com.vip.vjtools.vjkit.io.FileUtil;
import fun.gengzi.codecopy.exception.RrException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

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
            fontResolver.addFont(ClassLoader.getSystemClassLoader().getResource("font/simsun.ttf").getPath(),
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        logger.info("HtmlToPdfUtils完成");
    }


    /**
     * html文本转换成PDF
     *
     * 由于 ITextpdf 对html 检测非常严格，html 头部必须声明
     * <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
     * <html lang="en" xmlns="http://www.w3.org/1999/xhtml">
     *
     * 以及其他的都要加</>结束，所以一般的页面都将不支持 转换 pdf
     *
     *
     * @param url           网址
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
            fontResolver.addFont(ClassLoader.getSystemClassLoader().getResource("font/" + fontFamilyEnum.fontFileName).getPath(),
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
    }


}