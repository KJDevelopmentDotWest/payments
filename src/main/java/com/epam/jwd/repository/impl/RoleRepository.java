package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.api.Repository;
import com.epam.jwd.repository.connectionpool.ConnectionPool;
import com.epam.jwd.repository.connectionpool.ConnectionPoolImpl;
import com.epam.jwd.repository.model.user.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoleRepository implements Repository<Role, Integer> {

    private static final String SQL_SAVE_ROLE = "INSERT INTO roles (permission_can_unlock, permission_can_view_all, permission_can_create_role, name) VALUES (?, ?, ?, ?)";

    private static final String SQL_FIND_ALL_ROLE = "SELECT id, permission_can_unlock, permission_can_view_all, permission_can_create_role, name FROM roles";
    private static final String SQL_FIND_ROLE_BY_ID = "SELECT id, permission_can_unlock, permission_can_view_all, permission_can_create_role, name FROM roles";

    private static final String SQL_UPDATE_ROLE_BY_ID = "UPDATE roles SET permission_can_unlock = ?, permission_can_view_all = ?, permission_can_create_role = ?, name = ? WHERE id = ?";

    private static final String SQL_DELETE_ROLE_BY_ID = "DELETE FROM roles WHERE id = ?";

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public Boolean save(Role entity) {
        Connection connection = connectionPool.takeConnection();
        try {
            saveRole(connection, entity);
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
        Connection connection = connectionPool.takeConnection();
        try {
            return updateRoleById(connection, entity);
        } catch (SQLException throwables) {
            //todo implement logger and custom exception
            throwables.printStackTrace();
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(Role entity) {
        Connection connection = connectionPool.takeConnection();
        try {
            return deleteRoleById(connection, entity.getId());
        } catch (SQLException throwables) {
            //todo implement logger and custom exception
            throwables.printStackTrace();
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Role> findAll() {
        Connection connection = connectionPool.takeConnection();
        List<Role> roles = new ArrayList<>();
        try {
            roles = findAllRole(connection);
        } catch (SQLException e){
            //todo implement logger and custom exception
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public Role findById(Integer id) {
        Connection connection = connectionPool.takeConnection();
        Role role = null;
        try {
            role = findRoleByID(connection, id);
        } catch (SQLException e){
            //todo implement logger and custom exception
            e.printStackTrace();
        }
        return role;
    }

    private void saveRole(Connection connection, Role role) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_ROLE);
        preparedStatement.setBoolean(1, role.getPermissionCanUnlock());
        preparedStatement.setBoolean(2, role.getPermissionCanViewAll());
        preparedStatement.setBoolean(3, role.getPermissionCanCreateRole());
        preparedStatement.setString(4, role.getName());
        preparedStatement.executeUpdate();
    }

    private List<Role> findAllRole(Connection connection) throws SQLException{
        List<Role> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_ROLE);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            Role role = new Role(resultSet.getInt(1),
                    resultSet.getBoolean(2),
                    resultSet.getBoolean(3),
                    resultSet.getBoolean(4),
                    resultSet.getString(5));
            result.add(role);
        }
        return result;
    }

    private Role findRoleByID(Connection connection, Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ROLE_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return new Role(resultSet.getInt(1),
                    resultSet.getBoolean(2),
                    resultSet.getBoolean(3),
                    resultSet.getBoolean(4),
                    resultSet.getString(5));
        } else {
            return null;
        }
    }

    private Boolean updateRoleById(Connection connection, Role role) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ROLE_BY_ID);
        preparedStatement.setBoolean(1, role.getPermissionCanUnlock());
        preparedStatement.setBoolean(2, role.getPermissionCanViewAll());
        preparedStatement.setBoolean(3, role.getPermissionCanCreateRole());
        preparedStatement.setString(4, role.getName());
        preparedStatement.setInt(5, role.getId());
        return Objects.equals(preparedStatement.executeUpdate(), 1);
    }

    private Boolean deleteRoleById(Connection connection, Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ROLE_BY_ID);
        preparedStatement.setInt(1, id);
        return Objects.equals(preparedStatement.executeUpdate(), 1);
    }
}
