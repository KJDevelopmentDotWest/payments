package com.epam.jwd.dao.model.user;

import com.epam.jwd.dao.model.Entity;

import java.util.Arrays;
import java.util.Objects;

public enum Role {

    ADMIN(1),
    CUSTOMER(2);

    private final Integer id;

    Role(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static Role getById(Integer id) {
        return Arrays.stream(Role.values())
                .filter(role -> role.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
