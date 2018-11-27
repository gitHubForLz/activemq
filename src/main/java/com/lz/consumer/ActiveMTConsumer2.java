package com.lz.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMTConsumer2 {
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
            Topic topic = session.createTopic("topic-01");
            //创建消费者
            MessageConsumer consumer = session.createConsumer(topic);
            // 接收
            TextMessage receive = (TextMessage) consumer.receive();
            System.out.println(receive.getText());
            // 7. 创建一个监听器
           /* while (1==1) {
                consumer.setMessageListener(new MessageListener() {
                    public void onMessage(Message message) {
                        try {
                            System.out.println("接收消息  = [" + ((TextMessage) message).getText() + "]");
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }*/
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
