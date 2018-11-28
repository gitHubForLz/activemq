package com.lz.durable;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 持久化订阅，先订阅消息就不会丢失
 */
public class Consumer {

    static ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://182.61.26.234:61616");

    public static void main(String[] args) {
        Connection connection = null;
        // 1、创建连接
        try {
            connection = activeMQConnectionFactory.createConnection();
            //持久化订阅给一个客户ID
            connection.setClientID("client-01");
            connection.start();
            // 2、创建session
            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
            // dothing -------------------------------
            Topic topic = session.createTopic("topic-durable-01");
            //创建消费者持久化
            MessageConsumer consumer = session.createDurableSubscriber(topic,"client-01");
            //  创建一个监听器
            consumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    try {
                        System.out.println("接收消息  = [" + ((TextMessage) message).getText() + "]");
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            //end ------------------------------
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
