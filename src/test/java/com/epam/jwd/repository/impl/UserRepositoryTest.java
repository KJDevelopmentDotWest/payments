package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.model.user.Account;
import com.epam.jwd.repository.model.user.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    @Test
    public void saveTest(){
        UserRepository repository = new UserRepository();

        Account account = new Account("name", "surname", new ArrayList<Integer>(), 1);
        User user = new User("login", "password", account);

        repository.save(user);
    }

}