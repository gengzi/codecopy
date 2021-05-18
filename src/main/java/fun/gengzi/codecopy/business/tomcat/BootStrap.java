package fun.gengzi.codecopy.business.tomcat;

import com.google.common.io.FileBackedOutputStream;
import lombok.SneakyThrows;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class BootStrap {


    public static final int PORT = 8080;


    /**
     * 创建class类，可以直接写包名  xx.Class 包名会被创建出来
     * <p>
     * new 对象后 点，会出现一些 idea 提供的快捷方式 如： new xx().var 会打印参数
     *
     * @param args
     */
    @SneakyThrows
    public static void main(String[] args) {

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
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            // 回传HTTP 响应
            printWriter.println("HTTP/1.1 200 OK");
            socket.close();
        }
    }
}
