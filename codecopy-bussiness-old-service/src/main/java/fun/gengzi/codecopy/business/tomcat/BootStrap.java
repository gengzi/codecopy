package fun.gengzi.codecopy.business.tomcat;

import cn.hutool.core.io.FileUtil;
import lombok.SneakyThrows;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;


/**
 * <h1>mytomcat 启动类</h1>
 * <p>
 * 1.0 版本，支持接收HTTP请求，并成功响应 hello mytomcat
 * 2.0 版本，封装Request 和 Response 对象，并响应静态资源
 * 3.0 版本，支持动态请求，使用线程池，优化多个请求
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

    // 记录当前配置文件中的servlet 信息
    public static ConcurrentHashMap<String, Object> allServlet = new ConcurrentHashMap();

    // 线程池
    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
            6,
            60,
            TimeUnit.SECONDS, new LinkedBlockingDeque(50));

    /**
     * 创建class类，可以直接写包名  xx.Class 包名会被创建出来
     * <p>
     * new 对象后 点，会出现一些 idea 提供的快捷方式 如： new xx().var 会打印参数
     *
     * @param args
     */

    public static void main(String[] args) {
        // version 1
        // version1();
        // version 2
        // version2();
        // version3
        version4();
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
            if (request.getUrl().endsWith(STATIC_HTML_TYPE) && GET_HTTP.equalsIgnoreCase(request.getHttpType())) {
                response.jumpStaticResource(request.getUrl());
            } else {
                response.outPut("找不到此页面");
            }
            socket.close();
        }
    }


    /**
     * 初始化
     */
    @SneakyThrows
    public static void init() {
        // 初始化方法
        String path = Response.class.getResource("/").getPath() + "templates/web.xml";
        BufferedInputStream inputStream = FileUtil.getInputStream(path);
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputStream);
        Element rootElement = document.getRootElement();
        List<Node> nodes = rootElement.selectNodes("//servlet");
        nodes.forEach(
                node -> {
                    String servletName = node.selectSingleNode("servlet-name").getStringValue();
                    String servletClass = node.selectSingleNode("servlet-class").getStringValue();
                    Node servletMapping = rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                    String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();
                    System.out.println("初始化Servlet:路径[" + urlPattern + "],类:[" + servletClass + "]");
                    try {
                        allServlet.put(urlPattern, Class.forName(servletClass).newInstance());
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    /**
     * 启动方法
     */
    public static void start(){

    }


    @SneakyThrows
    public static void version3() {
        // 初始化
        init();
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            // 只有接收到请求后，该方法才会返回
            Socket socket = serverSocket.accept();
            executor.execute(new ServletThread(socket,allServlet));

        }
    }

    @SneakyThrows
    public static void version4() {

        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            Socket socket = serverSocket.accept();
            // 不返回，直接卡死
            Thread.sleep(2000000);
        }
    }

}
