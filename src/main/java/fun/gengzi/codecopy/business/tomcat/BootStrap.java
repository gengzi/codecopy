package fun.gengzi.codecopy.business.tomcat;

import lombok.SneakyThrows;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * <h1>mytomcat 启动类</h1>
 * <p>
 * 1.0 版本，支持接收HTTP请求，并成功响应 hello mytomcat
 * 2.0 版本，封装Request 和 Response 对象，并响应静态资源
 *
 * @author gengzi
 * @date 2021年5月19日15:09:38
 */
public class BootStrap {

    // 监听端口
    public static final int PORT = 8080;
    // 静态页面后缀
    public static final String STATIC_HTML_TYPE = ".html";

    // get请求方式
    public static final String GET_HTTP = "GET";


    /**
     * 创建class类，可以直接写包名  xx.Class 包名会被创建出来
     * <p>
     * new 对象后 点，会出现一些 idea 提供的快捷方式 如： new xx().var 会打印参数
     *
     * @param args
     */

    public static void main(String[] args) {
        // version 1
//         version1();
        // version 2
        version2();

    }

    /**
     * 版本1 支持接收HTTP请求，并成功响应 hello mytomcat
     */
    @SneakyThrows
    public static void version1() {
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            // 只有接收到请求后，该方法才会返回
            Socket socket = serverSocket.accept();
            // 获取数据
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            System.out.println("--->request");
            System.out.println(line);

            OutputStream outputStream = socket.getOutputStream();

            String hell_mytomcat = ResponseUtils.http200("hell mytomcat");
            System.out.println(hell_mytomcat);
            outputStream.write(hell_mytomcat.getBytes());
            outputStream.flush();
            outputStream.close();
            socket.close();
        }
    }


    /**
     * 版本2 封装Request 和 Response 对象，并响应静态资源
     */
    @SneakyThrows
    public static void version2() {
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            // 只有接收到请求后，该方法才会返回
            Socket socket = serverSocket.accept();
            // 获取数据
            Request request = new Request(socket);
            // 进行响应
            Response response = new Response(socket);
            if (request.getUrl().endsWith(STATIC_HTML_TYPE) &&  GET_HTTP.equalsIgnoreCase(request.getHttpType())) {
                response.jumpStaticResource(request.getUrl());
            }else {
                response.outPut("找不到此页面");
            }
            socket.close();
        }
    }
}
