package fun.gengzi.codecopy.utils;

import com.alibaba.fastjson.JSON;
import fun.gengzi.codecopy.business.authentication.controller.AuthenticationController;
import fun.gengzi.codecopy.vo.ReturnData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HttpResponseUtils {

    public static Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    public static void responseResult(HttpServletResponse response, ReturnData result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/json");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(JSON.toJSONString(result));
            writer.flush();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
