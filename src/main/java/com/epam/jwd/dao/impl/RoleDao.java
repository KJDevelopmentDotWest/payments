package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.connectionpool.api.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.model.user.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoleDao implements Dao<Role, Integer> {

    private static final String SQL_SAVE_ROLE = "INSERT INTO roles (name) VALUES (?)";

    private static final String SQL_FIND_ALL_ROLE = "SELECT id, name FROM roles";
    private static final String SQL_FIND_ROLE_BY_ID = "SELECT id, name FROM roles WHERE id = ?";

    private static final String SQL_UPDATE_ROLE_BY_ID = "UPDATE roles SET name = ? WHERE id = ?";

    private static final String SQL_DELETE_ROLE_BY_ID = "DELETE FROM roles WHERE id = ?";

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public Role save(Role entity) {
        Connection connection = connectionPool.takeConnection();
        try {
            return saveRole(connection, entity);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            e.printStackTrace();
            return null;
        } finally {
            connectionPool.returnConnection(connection);
        }
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

    private Role saveRole(Connection connection, Role role) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_ROLE);
        preparedStatement.setString(1, role.getName());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        Integer id = resultSet.getInt(1);
        role.setId(id);
        preparedStatement.close();
        resultSet.close();
        return role;
    }

    private List<Role> findAllRole(Connection connection) throws SQLException{
        List<Role> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_ROLE);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            Role role = new Role(resultSet.getInt(1),
                    resultSet.getString(2));
            result.add(role);
        }
        preparedStatement.close();
        resultSet.close();
        return result;
    }

    private Role findRoleByID(Connection connection, Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ROLE_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        preparedStatement.close();
        if (resultSet.next()){
            Role role = new Role(resultSet.getInt(1),
                    resultSet.getString(2));
            resultSet.close();
            return role;
        } else {
            resultSet.close();
            return null;
        }
    }

    private Boolean updateRoleById(Connection connection, Role role) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ROLE_BY_ID);
        preparedStatement.setString(1, role.getName());
        preparedStatement.setInt(2, role.getId());
        Boolean result = Objects.equals(preparedStatement.executeUpdate(), 1);
        preparedStatement.close();
        return result;
    }

    private Boolean deleteRoleById(Connection connection, Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ROLE_BY_ID);
        preparedStatement.setInt(1, id);
        Boolean result = Objects.equals(preparedStatement.executeUpdate(), 1);
        preparedStatement.close();
        return result;
    }
}
