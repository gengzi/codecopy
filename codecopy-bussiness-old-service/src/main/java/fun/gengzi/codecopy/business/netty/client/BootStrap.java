package fun.gengzi.codecopy.business.netty.client;


import fun.gengzi.codecopy.business.netty.client.rpc.RpcConsumer;
import fun.gengzi.codecopy.business.netty.common.MessageService;

public class BootStrap {

    public static void main(String[] args) throws InterruptedException {
        // 启动客户端
        System.out.println("客户端启动");
        MessageService proxy = (MessageService) RpcConsumer.createProxy(MessageService.class, "gengzi666#");

//        for (;;){
//            Thread.sleep(1000);
//            proxy.sendMessage("wwwww");
//        }
        // 发送请求到服务端
        for (int i = 0; i < 100; i++) {
//            Thread.sleep(1000);
            String s = proxy.sendMessage("test" + i);
            System.out.println(s);
        }


    }
}
