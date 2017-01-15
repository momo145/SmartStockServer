package com.chenxinjian.smartstock.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by apple on 16/9/17.
 */
public class App {
    public static void main( String[] args )
    {
        new ClassPathXmlApplicationContext("spring-base.xml");
    }
}
