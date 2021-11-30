package fun.gengzi.codecopy.business.tomcat;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * <h1>响应实体</h1>
 *
 * @author gengzi
 * @date 2021年5月20日23:15:53
 */
public class Response {

    // socket
    private Socket socket;


    public Response(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * 输出一般数据
     *
     * @param data 响应结果
     */
    @SneakyThrows
    public void outPut(String data) {
        OutputStream outputStream = this.socket.getOutputStream();
        String result = ResponseUtils.http200(data);
        outputStream.write(result.getBytes());
    }


    /**
     * 输出静态页面
     *
     * @param url 静态页面路径
     */
    @SneakyThrows
    public void jumpStaticResource(String url) {
        OutputStream outputStream = this.socket.getOutputStream();
        // 获取页面路径
        String path = Response.class.getResource("/").getPath() + "templates" + url;
        System.out.println(path);
        // 根据真实路径获取文件内容
        File file = new File(path);
        if (file.exists()) {
            // 文件存在
            FileInputStream fileInputStream = new FileInputStream(file);
            int count = fileInputStream.available();
            // 设置响应头
            String header = ResponseUtils.http200Header(count);
            outputStream.write(header.getBytes());
            // 读写静态文件
            byte[] bytes = new byte[1024];
            while (count > 0) {
                count = count - 1024;
                if (count < 0) {
                    byte[] bytes1 = new byte[count + 1024];
                    fileInputStream.read(bytes1);
                    outputStream.write(bytes1);
                }
                fileInputStream.read(bytes);
                outputStream.write(bytes);
                // 刷新输出流
                outputStream.flush();
            }
        }
        outputStream.close();
    }


}
