package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.model.user.Account;
import com.epam.jwd.dao.model.user.User;
import org.junit.jupiter.api.Test;

class UserDAOTest {

    @Test
    public void saveTest(){
        UserDAO repository = new UserDAO();

        Account account = new Account("name", "surname",  1);
        User user = new User("login", "password", account);

        repository.save(user);
    }

    @Test
    public void findAllTest(){
        DAO<User, Integer> DAO = new UserDAO();
        System.out.println(DAO.findAll().toString());
    }

}