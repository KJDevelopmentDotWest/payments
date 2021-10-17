package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.api.Repository;
import com.epam.jwd.repository.connectionpool.ConnectionPool;
import com.epam.jwd.repository.connectionpool.ConnectionPoolImpl;
import com.epam.jwd.repository.model.creditcard.BankAccount;
import com.epam.jwd.repository.model.creditcard.CreditCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CreditCardRepository implements Repository<CreditCard, Integer> {

    private static final String SQL_SAVE_CREDIT_CARD = "INSERT INTO credit_cards (name, expire_date, account_id) VALUES (?, ?, ?)";
    private static final String SQL_SAVE_BANK_ACCOUNT = "INSERT INTO bank_accounts (balance, blocked) VALUES (?, ?)";

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public Boolean save(CreditCard entity) {
        Connection connection = connectionPool.takeConnection();
        try {
            connection.setAutoCommit(false);
            saveBankAccount(entity.getBankAccount(), connection);
            saveCreditCard(entity, connection);
            connection.commit();
            connection.setAutoCommit(true);
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
    public Boolean update(CreditCard entity) {
        return null;
    }

    @Override
    public Boolean delete(CreditCard entity) {
        return null;
    }

    @Override
    public List<CreditCard> findAll() {
        return null;
    }

    @Override
    public CreditCard findById(Integer id) {
        return null;
    }

    private void saveCreditCard(CreditCard card, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_CREDIT_CARD);
        preparedStatement.setString(1, card.getName());
        preparedStatement.setString(2, card.getExpireDate().toString());
        preparedStatement.setInt(3, card.getAccountId());
        preparedStatement.executeUpdate();
    }

    private void saveBankAccount(BankAccount account, Connection connection) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_BANK_ACCOUNT);
        preparedStatement.setInt(1, account.getBalance());
        preparedStatement.setBoolean(2, account.getBlocked());
        preparedStatement.executeUpdate();
    }
}
