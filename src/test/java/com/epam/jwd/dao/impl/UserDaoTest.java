package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.model.user.Role;
import com.epam.jwd.dao.model.user.User;
import org.junit.jupiter.api.Test;

class UserDaoTest {

    @Test
    public void saveTest(){
        UserDao repository = new UserDao();

//        Integer integer = new Integer("name", "surname",  1);
//        User user = new User("login", "password", integer);

        //repository.save(user);
    }

    @Test
    public void findAllTest(){
        Dao<User, java.lang.Integer> dao = new UserDao();
        User user = new User("login3", "password", null, true, Role.CUSTOMER);
        System.out.println("++++++++++++++++++");
        System.out.println(dao.save(user));
        System.out.println(dao.findAll().toString());
        System.out.println("++++++++++++++++++");
    }

}