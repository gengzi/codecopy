package fun.gengzi.codecopy.business.netty.server;


import fun.gengzi.codecopy.business.netty.server.impl.MessageServiceImpl;

public class NettyServer {


    public static void main(String[] args) throws InterruptedException {
        MessageServiceImpl.startServer();
    }
}
