package fun.gengzi.codecopy.business.netty.server;

import fun.gengzi.codecopy.business.netty.server.impl.MessageServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Random;

public class MessageServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Random rand = new Random();
        if (msg.toString().startsWith("gengzi666")) {
            // 调用服务端方法，处理业务
            String result = new MessageServiceImpl().sendMessage(msg.toString().replace("gengzi666", ""));
            // 执行完业务，返回响应结果
            Thread.sleep(rand.nextInt(2000));
            ctx.writeAndFlush(result);
        }
    }
}
