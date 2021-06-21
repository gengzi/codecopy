package fun.gengzi.codecopy.business.problemsolve.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// 请求接受类
public class BootStrap {
    // 监听端口
    public static final int PORT = 8080;

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {
            Socket socket = serverSocket.accept();
            //TODO  不返回，直接卡死
            Thread.sleep(2000000);
        }
    }
}