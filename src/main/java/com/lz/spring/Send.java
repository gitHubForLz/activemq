package com.lz.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

public class Send {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        JmsTemplate myJmsTemplate = (JmsTemplate) context.getBean("myJmsTemplate");
        for (int i = 0; i < 10; i++) {
            final int j =i;
            myJmsTemplate.send(new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                    // Queue queue = session.createQueue("spring-queue01");
                    // MessageProducer producer = session.createProducer(queue);
                    TextMessage message = session.createTextMessage("你好"+j);
                    return message;
                }
            });

        }
    }
}
