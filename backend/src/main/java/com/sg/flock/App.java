package com.sg.flock;


import com.sg.flock.dao.Dao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class App {

    public static void main(String[] args)  {
        Dao dao=new Dao();
        dao.createTables();
        Dao sl =new Dao();

        //System.out.println(dao.convertTweetsToStrings(dao.getAllTweets()));
        SpringApplication.run(App.class, args);

    }
}