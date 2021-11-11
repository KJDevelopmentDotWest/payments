package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.connectionpool.api.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.model.user.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccountDao implements Dao<Account, Integer> {

    private static final Logger logger = LogManager.getLogger(AccountDao.class);

    private static final String SQL_SAVE_ACCOUNT = "INSERT INTO accounts (name, surname, profile_picture_id) VALUES (?, ?, ?)";

    private static final String SQL_FIND_ALL_ACCOUNTS = "SELECT id, name, surname, profile_picture_id FROM accounts";
    private static final String SQL_FIND_ACCOUNT_BY_ID = "SELECT id, name, surname, profile_picture_id FROM accounts WHERE id = ?";

    private static final String SQL_DELETE_ACCOUNT_BY_ID = "DELETE FROM accounts WHERE id = ?";

    private static final String SQL_UPDATE_ACCOUNT_BY_ID = "UPDATE accounts SET name = ?, surname = ?, profile_picture_id = ? WHERE id = ?";

    private static final String SQL_COUNT_USERS = "SELECT COUNT(id) as accounts_number FROM accounts";

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public Account save(Account entity) {
        logger.info("save method " + AccountDao.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return saveAccount(connection, entity);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
            return null;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean update(Account entity) {
        logger.info("update method " + AccountDao.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return updateAccountById(connection, entity);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(Account entity) {
        logger.info("delete method " + AccountDao.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return deleteAccountById(connection, entity.getId());
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Account> findAll() {
        logger.info("find all method " + AccountDao.class);
        Connection connection = connectionPool.takeConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            accounts = findAllAccounts(connection);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return accounts;
    }

    @Override
    public Account findById(Integer id) {
        logger.info("find by id method " + AccountDao.class);
        Connection connection = connectionPool.takeConnection();
        Account account = null;
        try {
            account = findAccountById(connection, id);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return account;
    }

    @Override
    public Integer getRowsNumber() {
        logger.info("get row number method " + AccountDao.class);
        Connection connection = connectionPool.takeConnection();
        Integer result = null;
        try {
            result = getRowNumber(connection);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }


    private Account saveAccount(Connection connection, Account account) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_ACCOUNT, new String[] {"id"});
        preparedStatement.setString(1, account.getName());
        preparedStatement.setString(2, account.getSurname());
        preparedStatement.setInt(3, account.getProfilePictureId());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        Integer id = resultSet.getInt(1);
        account.setId(id);
        preparedStatement.close();
        resultSet.close();
        return account;
    }

    private List<Account> findAllAccounts(Connection connection) throws SQLException{
        List<Account> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_ACCOUNTS);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            result.add(new Account(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4)));
        }
        preparedStatement.close();
        resultSet.close();
        return result;
    }

    private Account findAccountById(Connection connection, Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Account account;
        if (resultSet.next()){
            account = new Account(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4));
        } else {
            account = null;
        }
        preparedStatement.close();
        resultSet.close();
        return account;
    }

    private Boolean deleteAccountById(Connection connection, Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ACCOUNT_BY_ID);
        preparedStatement.setInt(1, id);
        Boolean result = Objects.equals(preparedStatement.executeUpdate(), 1);
        preparedStatement.close();
        return result;
    }

    private Boolean updateAccountById(Connection connection, Account account) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ACCOUNT_BY_ID);
        preparedStatement.setString(1, account.getName());
        preparedStatement.setString(2, account.getSurname());
        preparedStatement.setInt(3, account.getProfilePictureId());
        preparedStatement.setInt(4, account.getId());
        Boolean result = Objects.equals(preparedStatement.executeUpdate(), 1);
        preparedStatement.close();
        return result;
    }

    private Integer getRowNumber(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_COUNT_USERS);
        ResultSet resultSet = preparedStatement.executeQuery();
        Integer result;
        if (resultSet.next()){
            result = resultSet.getInt(1);
        } else {
            result = 0;
        }
        preparedStatement.close();
        resultSet.close();
        return result;
    }
}
