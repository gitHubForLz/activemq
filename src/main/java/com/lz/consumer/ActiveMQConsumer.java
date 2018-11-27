package com.lz.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMQConsumer {
    static ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://182.61.26.234:61616");

    public static void main(String[] args) {
        Connection connection = null;
        // 1、创建连接
        try {
            connection = activeMQConnectionFactory.createConnection();
            connection.start();
            // 2、创建session
            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
            // dothing -------------------------------
            Queue queue = session.createQueue("queue-01");
            //创建消费者
            MessageConsumer consumer = session.createConsumer(queue);
            //接收阻塞
            // TextMessage receive = (TextMessage) consumer.receive();
            // System.out.println(receive.getText());
            while (true) {
                // 接收数据的时间（等待） 100 ms
                Message message = consumer.receive(1000 * 100);

                TextMessage text = (TextMessage) message;
                if (text != null) {
                    System.out.println("接收：" + text.getText());
                } else {
                    break;
                }
            }

            //end ------------------------------
            // 3、提交session
            session.commit();
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
