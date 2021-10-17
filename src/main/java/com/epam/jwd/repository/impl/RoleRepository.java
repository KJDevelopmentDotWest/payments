package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.api.Repository;
import com.epam.jwd.repository.connectionpool.ConnectionPool;
import com.epam.jwd.repository.connectionpool.ConnectionPoolImpl;
import com.epam.jwd.repository.model.user.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RoleRepository implements Repository<Role, Integer> {

    private static final String SQL_SAVE_ROLE = "INSERT INTO roles (permission_can_unlock, permission_can_view_all, permission_can_create_role, name) VALUES (?, ?, ?, ?)";

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public Boolean save(Role entity) {
        Connection connection = connectionPool.takeConnection();
        try {
            saveRole(entity, connection);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            e.printStackTrace();
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
        return true;
    }

    @Override
    public Boolean update(Role entity) {
        return null;
    }

    @Override
    public Boolean delete(Role entity) {
        return null;
    }

    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public Role findById(Integer id) {
        return null;
    }

    private void saveRole(Role role, Connection connection) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_ROLE);
        preparedStatement.setBoolean(1, role.getPermissionCanUnlock());
        preparedStatement.setBoolean(2, role.getPermissionCanViewAll());
        preparedStatement.setBoolean(3, role.getPermissionCanCreateRole());
        preparedStatement.setString(4, role.getName());
        preparedStatement.executeUpdate();
    }
}
