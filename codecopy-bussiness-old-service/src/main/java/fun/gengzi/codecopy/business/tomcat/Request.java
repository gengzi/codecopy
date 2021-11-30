package fun.gengzi.codecopy.business.tomcat;


import lombok.SneakyThrows;

import java.io.InputStream;
import java.net.Socket;

/**
 * <h1>请求对象</h1>
 * 包含请求信息
 * 封装最简单的 请求方式，请求路径，请求参数
 * <p>
 * <p>
 * GET https://www.fiddler2.com/UpdateCheck.aspx?isBeta=False HTTP/1.1
 * User-Agent: Fiddler/5.0.20192.25091 (.NET 4.7.1; WinNT 10.0.17763.0; zh-CN; 12xAMD64; Auto Update; Full Instance; Extensions: APITesting, AutoSaveExt, EventLog, FiddlerOrchestraAddon, HostsFile, RulesTab2, SAZClipboardFactory, SimpleFilter, Timeline)
 * Pragma: no-cache
 * Host: www.fiddler2.com
 * Accept-Language: zh-CN
 * Referer: http://fiddler2.com/client/5.0.20192.25091
 * Accept-Encoding: gzip, deflate
 * Connection: close
 *
 * @author gengzi
 * @date 2021年5月19日15:16:39
 */
public class Request {

    private String url;
    private String httpType;
    private Socket socket;

    public Request(Socket socket) {
        this.socket = socket;
        this.exec();
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpType() {
        return httpType;
    }

    public void setHttpType(String httpType) {
        this.httpType = httpType;
    }


    @SneakyThrows
    private void exec() {
        Socket socket = this.socket;
        InputStream inputStream = socket.getInputStream();
        int count = 0;
        while (count == 0) {
            count = inputStream.available();
        }
        byte[] bytes = new byte[count];
        inputStream.read(bytes);
        String httpReq = new String(bytes);
        String type = RequestUtils.getType(httpReq);
        this.httpType = type;
        String url = RequestUtils.getUrl(httpReq);
        this.url = url;

        System.out.println("请求信息url:" + this.url + " 请求方式:" + this.httpType);
    }


}
