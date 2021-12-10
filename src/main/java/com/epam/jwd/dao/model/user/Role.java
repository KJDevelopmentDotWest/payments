package com.epam.jwd.dao.model.user;

import com.epam.jwd.dao.model.Entity;

import java.util.Arrays;
import java.util.Objects;

/**
 * This enum represent list of roles
 */
public enum Role {

    ADMIN(1),
    CUSTOMER(2);

    private final Integer id;

    /**
     *
     * @param id id of role
     */
    Role(int id) {
        this.id = id;
    }

    /**
     *
     * @return id of role
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id key id
     * @return Role with provided id, null if there is no role with provided id
     */
    public static Role getById(Integer id) {
        return Arrays.stream(Role.values())
                .filter(role -> Objects.equals(role.getId(), id))
                .findFirst()
                .orElse(null);
    }
}
