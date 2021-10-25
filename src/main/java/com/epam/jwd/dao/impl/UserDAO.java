package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.DAO;
import com.epam.jwd.dao.connectionpool.api.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.model.user.Account;
import com.epam.jwd.dao.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDAO implements DAO<User, Integer> {

    private static final String SQL_SAVE_USER = "INSERT INTO users (login, password) VALUES (?, ?)";
    private static final String SQL_SAVE_ACCOUNT = "INSERT INTO accounts (name, surname, role_id) VALUES (?, ?, ?)";

    private static final String SQL_FIND_ALL_USER = "SELECT id, login, password FROM users";
    private static final String SQL_FIND_USER_BY_ID = "SELECT id, login, password FROM users WHERE id = ?";
    private static final String SQL_FIND_ACCOUNT_BY_ID = "SELECT id, name, surname, role_id FROM accounts WHERE id = ?";

    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
    private static final String SQL_DELETE_ACCOUNT_BY_ID = "DELETE FROM accounts WHERE id = ?";

    private static final String SQL_UPDATE_USER_BY_ID = "UPDATE users SET login = ?, password = ? WHERE id = ?";
    private static final String SQL_UPDATE_ACCOUNT_BY_ID = "UPDATE accounts SET name = ?, surname = ?, role_id = ? WHERE id = ?";

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public User save(User entity) {
        Connection connection = connectionPool.takeConnection();
        try {
            User user;
            connection.setAutoCommit(false);
            user = saveUser(connection, entity);
            connection.commit();
            connection.setAutoCommit(true);
            return user;
        } catch (SQLException e) {
            //todo implement logger and custom exception
            e.printStackTrace();
            return null;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //updates user with id = entity.getId() and his account
    //all data contained in row will be replaced by new
    @Override
    public Boolean update(User entity) {
        Connection connection = connectionPool.takeConnection();
        try {
            connection.setAutoCommit(false);
            Boolean result = updateUserById(connection, entity);
            connection.commit();
            connection.setAutoCommit(true);
            return result;
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
            connection.setAutoCommit(false);
            Boolean result = deleteUserById(connection, entity.getId());
            connection.commit();
            connection.setAutoCommit(true);
            return result;
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
            users = findAllUser(connection);
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

    private User saveUser(Connection connection, User user) throws SQLException{
        saveAccount(connection, user.getAccount());
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER);
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        Integer id = resultSet.getInt(1);
        user.setId(id);
        preparedStatement.close();
        return user;
    }

    private void saveAccount(Connection connection, Account account) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_ACCOUNT);
        preparedStatement.setString(1, account.getName());
        preparedStatement.setString(2, account.getSurname());
        preparedStatement.setInt(3, account.getRoleId());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        Integer id = resultSet.getInt(1);
        account.setId(id);
        resultSet.close();
        preparedStatement.close();
    }

    private List<User> findAllUser(Connection connection) throws SQLException{
        List<User> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_USER);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            User user = new User(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    findAccountById(connection, resultSet.getInt(1)));
            result.add(user);
        }
        resultSet.close();
        preparedStatement.close();
        return result;
    }

    private User findUserById(Connection connection, Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        preparedStatement.close();
        if (resultSet.next()){
            User user = new User(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    findAccountById(connection, resultSet.getInt(1)));
            resultSet.close();
            return user;
        } else {
            resultSet.close();
            return null;
        }
    }

    private Account findAccountById(Connection connection, Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        preparedStatement.close();
        if (resultSet.next()){
            Account account = new Account(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4));
            resultSet.close();
            return account;
        } else {
            resultSet.close();
            return null;
        }
    }

    private Boolean deleteUserById(Connection connection, Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER_BY_ID);
        preparedStatement.setInt(1, id);
        Boolean result = Objects.equals(preparedStatement.executeUpdate(), 1)
                && deleteAccountById(connection, id);
        preparedStatement.close();
        return result;
    }

    private Boolean deleteAccountById(Connection connection, Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ACCOUNT_BY_ID);
        preparedStatement.setInt(1, id);
        Boolean result = Objects.equals(preparedStatement.executeUpdate(), 1);
        preparedStatement.close();
        return result;
    }

    private Boolean updateUserById(Connection connection, User user) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_BY_ID);
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setInt(3, user.getId());
        Boolean result = Objects.equals(preparedStatement.executeUpdate(), 1)
                && updateAccountById(connection, user.getAccount());
        preparedStatement.close();
        return result;
    }

    private Boolean updateAccountById(Connection connection, Account account) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ACCOUNT_BY_ID);
        preparedStatement.setString(1, account.getName());
        preparedStatement.setString(2, account.getSurname());
        preparedStatement.setInt(3, account.getRoleId());
        preparedStatement.setInt(4, account.getId());
        Boolean result = Objects.equals(preparedStatement.executeUpdate(), 1);
        preparedStatement.close();
        return result;
    }
}
