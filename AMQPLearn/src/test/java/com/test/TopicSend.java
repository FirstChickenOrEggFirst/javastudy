package com.test;

/**
 * ProjectName: JavaStudy
 * ClassName: TopicSend
 * PackageName: com.test
 * Description:
 *
 * @author: wangjingbiao
 * @date: 2018-08-06 17:24
 */
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class TopicSend {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) {
        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");

            connection = factory.newConnection();
            channel = connection.createChannel();
//			声明一个匹配模式的交换器
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            // 待发送的消息
            String[] routingKeys = new String[]{"lazy.orange.elephant"};
//			发送消息
            for(String severity :routingKeys){
                String message = "From "+severity+" routingKey' s message!";
                channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
                System.out.println("TopicSend [x] Sent '" + severity + "':'" + message + "'");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignore) {
                }
            }
        }
    }
}

