package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.api.Repository;
import com.epam.jwd.repository.connectionpool.ConnectionPool;
import com.epam.jwd.repository.connectionpool.ConnectionPoolImpl;
import com.epam.jwd.repository.model.user.Account;
import com.epam.jwd.repository.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserRepository implements Repository<User, Integer> {

    private static final String SQL_SAVE_USER = "INSERT INTO users (login, password) VALUES (?, ?)";
    private static final String SQL_SAVE_ACCOUNT = "INSERT INTO accounts (name, surname, role_id) VALUES (?, ?, ?)";

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public Boolean save(User entity) {
        Connection connection = connectionPool.takeConnection();
        try {
            connection.setAutoCommit(false);
            saveAccount(entity.getAccount(), connection);
            saveUser(entity, connection);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return null;
    }

    @Override
    public Boolean update(User entity) {
        return null;
    }

    @Override
    public Boolean delete(User entity) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(Integer id) {
        return null;
    }

    private void saveUser(User user, Connection connection) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER);
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.executeUpdate();
    }

    private void saveAccount(Account account, Connection connection) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_ACCOUNT);
        preparedStatement.setString(1, account.getName());
        preparedStatement.setString(2, account.getSurname());
        preparedStatement.setInt(3, account.getRoleId());
        preparedStatement.executeUpdate();
    }
}
