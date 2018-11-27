package com.lz.provider;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ActiveMTProvider {
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
            // 创建producer
            MessageProducer producer = session.createProducer(topic);
            //创建message
            TextMessage textMessage = session.createTextMessage("hello ,my name is topic 1");
            producer.send(textMessage);
            //end ------------------------------
            // 3、提交session
            session.commit();
            // 关闭session
            session.close();
            // 关闭连接
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
