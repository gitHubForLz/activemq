package com.lz.spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;


public class Receive {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        JmsTemplate myJmsTemplate = (JmsTemplate) context.getBean("myJmsTemplate");
        for (int i = 0; i < 10; i++) {
            Object o = myJmsTemplate.receiveAndConvert();
            System.out.println(o);

        }
    }
}
