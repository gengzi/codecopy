package fun.gengzi.codecopy.business.utilstest.utils;


import cn.hutool.http.HttpRequest;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class HtmlToPdfUtilsTest {

    @Test
    public void fun01() {
//        File file = new File(ClassLoader.getSystemClassLoader().getResource("templates/index.ftl").getPath());
//        String pdfPath1 = System.getProperty("user.dir") + "/test1.pdf";
//        String pdfPath2 = System.getProperty("user.dir") + "/test2.pdf";

//        HtmlToPdfUtils.htmlToPdf(file, pdfPath1);

        HtmlToPdfUtils.htmlTextToPdf("<!DOCTYPE html>" +
                "<html lang=\"cn\">" +
                "<head>" +
                "    <meta charset=\"utf-8\"/>" +
                "    <title>FreeMarker</title>" +
                "</head>" +
                "<body style=\"font-family:SimSun; \">" +
                "<p><input type=\"checkbox\" checked=\"checked\" disabled=\"disabled\" name=\"vehicle\" value=\"Bike\" /> I have a bike</p>" +
                "<h1>hello world刘宁233333</h1>" +
                "<h1 style=\"color: red\">减法的南方科大fdsafaf是农发321321</h1>" +
                "</body>" +
                "</html>", "D:\\jeecg\\2.pdf", HtmlToPdfUtils.FontFamilyEnum.SIMSUN);
    }

    @Test
    public void fun02(){
        URL url = null;
        try {
            url = new URL("https://zhuanlan.zhihu.com/p/33791813");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String body = HttpRequest.get(url.toString()).execute().body();
        System.out.println(body);
    }

}