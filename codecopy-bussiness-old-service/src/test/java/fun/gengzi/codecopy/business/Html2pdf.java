package fun.gengzi.codecopy.business;

import cn.hutool.core.util.RandomUtil;
import com.itextpdf.text.pdf.BaseFont;
import org.junit.Test;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;


/**
 * 转换html为pdf
 * @author Uncle Liu
 *
 */
public class Html2pdf {

    /**
     * 将HTML转成PD格式的文件。html文件的格式比较严格
     * @param htmlFile
     * @param pdfFile
     * @throws Exception
     */
    // <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd ">
    public static void html2pdf(String htmlFile, String pdfFile) throws Exception {
        // step 1
        String url = htmlFile;
        // step 2


        OutputStream os = new FileOutputStream(pdfFile);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(url);

        // step 3 解决中文支持
        ITextFontResolver fontResolver = renderer.getFontResolver();
        if("linux".equals(getCurrentOperatingSystem())){
            fontResolver.addFont("/usr/share/fonts/chiness/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        }else{
            fontResolver.addFont("D:\\jeecg\\simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        }

        renderer.layout();
        renderer.createPDF(os);
        os.close();

        System.out.println("create pdf done!!");

    }

    public static String getCurrentOperatingSystem(){
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println("---------当前操作系统是-----------" + os);
        return os;
    }


    @Test
    public void fun() {

        String htmlFile = "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"utf-8\"/>" +
                "    <title>FreeMarker</title>" +
                "</head>" +
                "<body style=\"font-family: '黑体' \">" +
                "<p><input type=\"checkbox\" checked=\"checked\" disabled=\"disabled\" name=\"vehicle\" value=\"Bike\" /> I have a bike</p>" +
                "<h1>hello world刘宁233333</h1>" +
                "<h1 style=\"color: red\">${name}</h1>" +
                "</body>" +
                "</html>";
        String pdfFile = "D:\\jeecg\\ss.pdf";
        try {
            Html2pdf.html2pdf(htmlFile, pdfFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void fun03(){
        String sql = "INSERT INTO `tb_integral` VALUES ('%s', '%s', 'hd_001', 't_%s', '%s', '2020-09-11 15:28:40', '2020-09-11 15:28:40');";
        for (int i = 3; i < 100; i++) {
            String format = String.format(sql, i, i, i, RandomUtil.randomInt(30, 1000));
            System.out.println(format);
        }
    }



}