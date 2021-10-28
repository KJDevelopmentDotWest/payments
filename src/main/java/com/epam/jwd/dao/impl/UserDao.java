package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.connectionpool.api.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.model.user.Role;
import com.epam.jwd.dao.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDao implements Dao<User, java.lang.Integer> {

    private static final String SQL_SAVE_USER = "INSERT INTO users (login, password, account_id, is_active, role_id) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_FIND_ALL_USERS = "SELECT id, login, password, account_id, is_active, role_id FROM users";
    private static final String SQL_FIND_USER_BY_ID = "SELECT id, login, password, account_id, is_active, role_id FROM users WHERE id = ?";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT id, login, password, account_id, is_active, role_id FROM users WHERE login = ?";

    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";

    private static final String SQL_UPDATE_USER_BY_ID = "UPDATE users SET login = ?, password = ?, account_id = ?, is_active = ?, role_id = ? WHERE id = ?";

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public User save(User entity) {
        Connection connection = connectionPool.takeConnection();
        try {
            return saveUser(connection, entity);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            e.printStackTrace();
            return null;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //updates user with id = entity.getId()
    //all data contained in row will be replaced by new
    @Override
    public Boolean update(User entity) {
        Connection connection = connectionPool.takeConnection();
        try {
            return updateUserById(connection, entity);
        } catch (SQLException throwables) {
            //todo implement logger and custom exception
            throwables.printStackTrace();
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //deletes user with id = entity.getId() and his account
    @Override
    public Boolean delete(User entity) {
        Connection connection = connectionPool.takeConnection();
        try {
            return deleteUserById(connection, entity.getId());
        } catch (SQLException throwables) {
            //todo implement logger and custom exception
            throwables.printStackTrace();
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<User> findAll() {
        Connection connection = connectionPool.takeConnection();
        List<User> users = new ArrayList<>();
        try {
            users = findAllUsers(connection);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return users;
    }

    @Override
    public User findById(Integer id) {
        Connection connection = connectionPool.takeConnection();
        User user = null;
        try {
            user = findUserById(connection, id);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    public User findByLogin(String login) {
        Connection connection = connectionPool.takeConnection();
        User user = null;
        try {
            user = findUserByLogin(connection, login);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    private User saveUser(Connection connection, User user) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER);
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setInt(3, user.getAccountId());
        preparedStatement.setBoolean(4, user.getActive());
        preparedStatement.setInt(5, user.getRole().getId());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        Integer id = resultSet.getInt(1);
        user.setId(id);
        preparedStatement.close();
        return user;
    }

    private List<User> findAllUsers(Connection connection) throws SQLException{
        List<User> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_USERS);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            User user = new User(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getBoolean(5),
                    Role.getById(resultSet.getInt(6)));
            result.add(user);
        }
        resultSet.close();
        preparedStatement.close();
        return result;
    }

    private User findUserByLogin(Connection connection, String login) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN);
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();
        preparedStatement.close();
        if (resultSet.next()){
            User user = new User(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getBoolean(5),
                    Role.getById(resultSet.getInt(6)));
            resultSet.close();
            return user;
        } else {
            resultSet.close();
            return null;
        }
    }

    private User findUserById(Connection connection, java.lang.Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        preparedStatement.close();
        if (resultSet.next()){
            User user = new User(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getBoolean(5),
                    Role.getById(resultSet.getInt(6)));
            resultSet.close();
            return user;
        } else {
            resultSet.close();
            return null;
        }
    }

    private Boolean deleteUserById(Connection connection, java.lang.Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER_BY_ID);
        preparedStatement.setInt(1, id);
        Boolean result = Objects.equals(preparedStatement.executeUpdate(), 1);
        preparedStatement.close();
        return result;
    }

    private Boolean updateUserById(Connection connection, User user) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_BY_ID);
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setInt(3, user.getAccountId());
        preparedStatement.setBoolean(4, user.getActive());
        preparedStatement.setInt(5, user.getRole().getId());
        preparedStatement.setInt(6, user.getId());
        Boolean result = Objects.equals(preparedStatement.executeUpdate(), 1);
        preparedStatement.close();
        return result;
    }
}
