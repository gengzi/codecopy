package fun.gengzi.codecopy.business.tomcat;


import lombok.SneakyThrows;

/**
 * <h1>默认响应模板</h1>
 * <p>
 * <p>
 * 响应：
 * ---- 响应头  ----
 * HTTP/1.1 200 OK
 * Cache-Control: private
 * Content-Type: text/plain; charset=utf-8
 * Vary: Accept-Encoding
 * Server: Microsoft-IIS/8.5
 * X-AspNet-Version: 4.0.30319
 * X-Powered-By: ASP.NET
 * Date: Wed, 19 May 2021 06:26:15 GMT
 * Connection: close
 * Content-Length: 1144
 * -- 响应结果 -- 空出一行下面跟响应结果
 * data
 */
public class ResponseUtils {


    @SneakyThrows
    public static String http200Header(int data) {

        StringBuilder result = new StringBuilder();
        result.append("HTTP/1.1 200 OK \n");
        result.append("Content-Type: text/html; charset=utf-8 \n");
        result.append("Content-Length: " + data + " \n");
        result.append("\r\n");
        return result.toString();
    }


    /**
     * 获取http 200 的响应结果
     *
     * @param data 响应数据
     * @return
     */
    @SneakyThrows
    public static String http200(String data) {
        byte[] bytes = data.getBytes("UTF-8");

        StringBuilder result = new StringBuilder();
        result.append("HTTP/1.1 200 OK \n");
        result.append("Content-Type: text/html; charset=utf-8 \n");
        result.append("Content-Length: " + bytes.length + " \n");
        result.append("\r\n");
        result.append(data);
        return result.toString();
    }


    /**
     * 获取http 404 的响应结果
     *
     * @return
     */
    @SneakyThrows
    public static String http404() {
        final String info = "404....";
        StringBuilder result = new StringBuilder();
        result.append("HTTP/1.1 200 OK \n");
        result.append("Content-Type: text/html; charset=utf-8 \n");
        result.append("Content-Length: " + info.getBytes().length + " \n");
        result.append("\r\n");
        result.append(info);
        return result.toString();
    }


}
