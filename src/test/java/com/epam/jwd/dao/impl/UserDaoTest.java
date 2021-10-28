package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;
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
        Dao<User, java.lang.Integer> DAO = new UserDao();
        System.out.println(DAO.findAll().toString());
    }

}