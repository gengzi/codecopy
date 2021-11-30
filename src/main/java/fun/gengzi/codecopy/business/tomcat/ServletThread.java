package fun.gengzi.codecopy.business.tomcat;

import lombok.SneakyThrows;

import java.net.Socket;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ServletThread implements Runnable {

    // 静态页面后缀
    public static final String STATIC_HTML_TYPE = ".html";

    // get请求方式
    public static final String GET_HTTP = "GET";

    private Socket socket;
    private ConcurrentHashMap<String, Object> hashMap;

    public ServletThread(Socket socket, ConcurrentHashMap hashMap) {
        this.socket = socket;
        this.hashMap = hashMap;
    }

    public ConcurrentHashMap getHashMap() {
        return hashMap;
    }

    public void setHashMap(ConcurrentHashMap hashMap) {
        this.hashMap = hashMap;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @SneakyThrows
    @Override
    public void run() {
        // 获取数据
        Request request = new Request(socket);
        Response response = new Response(socket);
        if (request.getUrl().endsWith(STATIC_HTML_TYPE) && GET_HTTP.equalsIgnoreCase(request.getHttpType())) {
            response.jumpStaticResource(request.getUrl());
        } else {
            Optional<Map.Entry<String, Object>> first = hashMap.entrySet().
                    stream().filter(map -> map.getKey().equals(request.getUrl())).
                    findFirst();

            Map.Entry<String, Object> servlet = first.orElse(null);
            if (servlet == null) {
                // 未知请求
                response.outPut("请求失败");
            } else {
                Object obj = servlet.getValue();
                if (obj instanceof Servlert) {
                    ((Servlert) obj).service(request, response);
                }
            }
        }
        socket.close();
    }
}
