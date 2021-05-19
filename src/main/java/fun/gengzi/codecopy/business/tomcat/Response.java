package fun.gengzi.codecopy.business.tomcat;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Response {

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

    @SneakyThrows
    public void outPut(String data){
        OutputStream outputStream = this.socket.getOutputStream();
        outputStream.write(data.getBytes());
    }



    @SneakyThrows
    public void jumpStaticResource(String url){
        OutputStream outputStream = this.socket.getOutputStream();
        // 获取页面路径
        String path = Response.class.getResource("/").getPath() + url;
        System.out.println(path);
        // 根据真实路径获取文件内容
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        int count  = fileInputStream.available();

        String header = ResponseUtils.http200Header(count);
        outputStream.write(header.getBytes());

        byte[] bytes = new byte[1024];
        int len  = 0;


    }




}
