package fun.gengzi.codecopy.business.netty.client.rpc;

import fun.gengzi.codecopy.business.netty.client.handler.MessageHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcConsumer {

    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static MessageHandler messageHandler;

    public static Object createProxy(Class serverName, final String param) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{serverName}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 判断客户端是否初始化
                if (messageHandler == null) {
                    initClient();
                }
                // 设置参数，这里注意，如果不使用 get() 方法阻塞响应结果，这里的 setparam 也会出现问题，
                // 线程多次 setparam 会覆盖之前的设置的请求参数
                messageHandler.setParam(param + args[0]);
                return executorService.submit(messageHandler).get();
            }
        });

    }


    /**
     * 初始化客户端
     */
    private static void initClient() throws InterruptedException {

        messageHandler = new MessageHandler();
        // 1，创建一个线程组
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        // 2，客户端配置
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors)
                // TCP 连接
                .option(ChannelOption.TCP_NODELAY, true)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(messageHandler);
                    }
                });

        bootstrap.connect("127.0.0.1", 8888).sync();
    }


}
