package fun.gengzi.codecopy.business.netty.server.impl;

import fun.gengzi.codecopy.business.netty.common.MessageService;
import fun.gengzi.codecopy.business.netty.server.MessageServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class MessageServiceImpl implements MessageService {
    public String sendMessage(String msg) {
        System.out.println("接收到请求" + msg);
        return "服务端返回>>" + msg;
    }


    public static void startServer() throws InterruptedException {
        System.out.println("服务端启动");

        // 1, 创建两个线程池，用于接收请求和执行业务
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        // 2，打开服务
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 3，配置服务
        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new MessageServerHandler());

                    }
                });
        // 4，绑定端口启动
        serverBootstrap.bind("127.0.0.1", 8888).sync();
    }


}
