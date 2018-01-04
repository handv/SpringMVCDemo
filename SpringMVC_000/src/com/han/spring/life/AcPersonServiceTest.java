package com.han.spring.life;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by handv on 2017/3/29.
 */
public class AcPersonServiceTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        ApplicationContext ac = new ClassPathXmlApplicationContext("com/han/spring/life/applicationContext.xml");

        System.out.println("xml加载完毕");
        Person person1 = (Person) ac.getBean("person1");
        System.out.println(person1);
        System.out.println("关闭容器");
        ((ClassPathXmlApplicationContext)ac).close();

    }

}