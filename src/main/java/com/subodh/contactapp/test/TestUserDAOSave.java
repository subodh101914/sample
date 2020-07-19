package com.subodh.contactapp.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.subodh.contactapp.config.SpringRootConfig;
import com.subodh.contactapp.dao.UserDAO;
import com.subodh.contactapp.domain.User;

public class TestUserDAOSave {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringRootConfig.class);
        UserDAO userDAO=ctx.getBean(UserDAO.class);
        //TODO: the user details will be taken from User-Reg-Form
        User u=new User();
        u.setName("Amit");
        u.setPhone("9303580884");
        u.setEmail("amit@ezeon.net");
        u.setAddress("Mumbai");
        u.setLoginname("amit");
        u.setPassword("amit123");
        u.setRole(1);//Admin Role 
        u.setLoginStatus(1); //Active
        userDAO.save(u);
        System.out.println("--------Data Saved------");
    }    
}
