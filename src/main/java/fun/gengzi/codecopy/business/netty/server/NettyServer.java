package fun.gengzi.codecopy.business.netty.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * <h1>netty 服务端</h1>
 *
 *
 * @author gengzi
 * @date 2021年9月7日22:17:44
 */
public class NettyServer {


    public static void main(String[] args) {
        // 请求接收线程池
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 业务处理线程池
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        // 2. 创建启动引导类
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 3.设置启动引导类配置
        // 添加到组，第一个连接线程池，第二个业务处理线程池
        serverBootstrap.group(bossGroup,workGroup);



    }






}
