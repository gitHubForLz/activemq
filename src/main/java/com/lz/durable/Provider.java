package com.lz.durable;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Provider {

    static ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://182.61.26.234:61616");

    public static void main(String[] args) {
        Connection connection = null;
        // 1、创建连接
        try {
            connection = activeMQConnectionFactory.createConnection();
            connection.start();
            // 2、创建session，此处如果为true 事务方式处理，如果不提交，activemq不会记录被消费
            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
            // dothing -------------------------------
            Topic topic = session.createTopic("topic-durable-01");
            // 创建producer
            MessageProducer producer = session.createProducer(topic);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
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
