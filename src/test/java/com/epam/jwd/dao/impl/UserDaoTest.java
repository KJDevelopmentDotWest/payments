package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.model.user.Account;
import com.epam.jwd.dao.model.user.User;
import org.junit.jupiter.api.Test;

class UserDaoTest {

    @Test
    public void saveTest(){
        UserDao repository = new UserDao();

        Account account = new Account("name", "surname",  1);
        User user = new User("login", "password", account);

        repository.save(user);
    }

    @Test
    public void findAllTest(){
        Dao<User, Integer> DAO = new UserDao();
        System.out.println(DAO.findAll().toString());
    }

}