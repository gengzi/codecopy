package fun.gengzi;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.SneakyThrows;

/**
 * <h1>发送者</h1>
 *
 * @author Administrator
 * @date 2022/3/31 19:48
 */
public class Send {

    private static final String QUEUE_NAME = "hello";

    @SneakyThrows
    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("gengzi666");
        factory.setPassword("gengzi666");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // 声明队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // 消息
            String message = "Hello World!111";
            // 发布消息
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }


}
