package com.lz.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.TimeUnit;

public class ActiveMQConsumer2 {
    static ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://182.61.26.234:61616");

    public static void main(String[] args) {
        Connection connection = null;
        // 1、创建连接
        try {
            connection = activeMQConnectionFactory.createConnection();
            connection.start();
            // 2、创建session
            Session session = connection.createSession(false, Session.DUPS_OK_ACKNOWLEDGE);
            // dothing -----------------------------------------------

            Queue queue = session.createQueue("queue-01");
            //创建消费者
            MessageConsumer consumer = session.createConsumer(queue);
            //接收第一种方式
            // TextMessage receive = (TextMessage) consumer.receive();
            // System.out.println(receive.getText());

            // 接收第二种方式异步
            consumer.setMessageListener(msg -> {
                try {
                    TextMessage message = (TextMessage) msg;
                    System.out.println("consumer2收到消息： " + message.getText());
                    // session.commit(); Session.AUTO_ACKNOWLEDGE
                    message.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });
            try {
                //主线程睡一会儿
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //end ---------------------------------------------
            // 3、提交session
            // session.commit();
            // 关闭session
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    // 关闭连接
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
