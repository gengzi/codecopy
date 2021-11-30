package fun.gengzi.codecopy.business.netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * <h1>消息处理器</h1>
 * <p>
 * 实现callable ，用于在获取请求后的结果
 */
public class MessageHandler extends ChannelInboundHandlerAdapter implements Callable {

    // 通道处理器上线文
    private ChannelHandlerContext context;

    // 结果集
    private volatile String result;

    // 请求参数
    private String param;


    public void setParam(String param) {
        this.param = param;
    }

    // 启动时，进行赋值
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.context = ctx;
    }

    /**
     * 发送请求先执行 call 方法，writeAndFlush 发送请求到服务端
     *
     * 这里注意，我们使用线程池submit执行任务，使用get方法阻塞的获取响应结果。
     * 所以每个请求触发到响应结果，只会有一个线程触发请求并响应成功后。才会有下一个请求触发
     *
     *
     * 为了控制请求一条数据，等待服务端响应，所以要加入 wait  和  notify 来控制，wait 让线程等待服务端唤醒，notify 服务端响应数据后唤醒线程 返回。
     * 可以理解为 生产者消费者，一条数据生产好后，再进行消费
     *
     *
     * 如果我们让线程池，执行一个任务，不再阻塞。那么所有的线程都会一拥而入，再使用 wait 和 nnotify 就无法控制 result 的结果了
     *
     * @return
     * @throws Exception
     */
    public synchronized Object call() throws Exception {
        // 发送请求参数
        System.out.println("发送请求："+param);
        context.writeAndFlush(param);
        // 阻塞当前线程，等待被唤醒
        wait();
        return result;
    }


    /**
     * 获取服务器的响应
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        System.out.println("服务端响应数据：" + result);
        notify();
    }
}
