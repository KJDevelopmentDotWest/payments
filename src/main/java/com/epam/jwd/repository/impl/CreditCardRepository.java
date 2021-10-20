package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.api.Repository;
import com.epam.jwd.repository.connectionpool.ConnectionPool;
import com.epam.jwd.repository.connectionpool.ConnectionPoolImpl;
import com.epam.jwd.repository.model.creditcard.BankAccount;
import com.epam.jwd.repository.model.creditcard.CreditCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CreditCardRepository implements Repository<CreditCard, Integer> {

    private static final String SQL_SAVE_CREDIT_CARD = "INSERT INTO credit_cards (name, expire_date, user_id) VALUES (?, ?, ?)";
    private static final String SQL_SAVE_BANK_ACCOUNT = "INSERT INTO bank_accounts (balance, blocked) VALUES (?, ?)";

    private static final String SQL_FIND_ALL_CREDIT_CARD = "SELECT id, name, expire_date, user_id FROM credit_cards";
    private static final String SQL_FIND_CREDIT_CARD_BY_ID = "SELECT id, name, expire_date, user_id FROM credit_cards WHERE id = ?";
    private static final String SQL_FIND_CREDIT_CARD_BY_USER_ID = "SELECT id, name, expire_date, user_id FROM credit_cards WHERE user_id = ?";

    private static final String SQL_FIND_BANK_ACCOUNT_BY_ID = "SELECT id, balance, blocked FROM bank_accounts WHERE id = ?";

    private static final String SQL_UPDATE_CREDIT_CARD_BY_ID = "UPDATE credit_cards SET name = ?, expire_date = ?, user_id = ? WHERE id = ?";
    private static final String SQL_UPDATE_BANK_ACCOUNT_BY_ID = "UPDATE bank_accounts SET balance = ?, blocked = ? WHERE id = ?";

    private static final String SQL_DELETE_CREDIT_CARD_BY_ID = "DELETE FROM credit_cards WHERE id = ?";
    private static final String SQL_DELETE_BANK_ACCOUNT_BY_ID = "DELETE FROM bank_accounts WHERE id = ?";


    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public Boolean save(CreditCard entity) {
        Connection connection = connectionPool.takeConnection();
        try {
            connection.setAutoCommit(false);
            saveBankAccount(connection, entity.getBankAccount());
            saveCreditCard(connection, entity);
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
        Connection connection = connectionPool.takeConnection();
        try {
            connection.setAutoCommit(false);
            Boolean result = updateCreditCardById(connection, entity);
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
    public Boolean delete(CreditCard entity) {
        Connection connection = connectionPool.takeConnection();
        try {
            connection.setAutoCommit(false);
            Boolean result = deleteCreditCardById(connection, entity.getId());
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
    public List<CreditCard> findAll() {
        Connection connection = connectionPool.takeConnection();
        List<CreditCard> creditCards = new ArrayList<>();

        try {
            creditCards = findAllCreditCard(connection);
        } catch (SQLException e){
            //todo implement logger and custom exception
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }

        return creditCards;
    }

    @Override
    public CreditCard findById(Integer id) {
        Connection connection = connectionPool.takeConnection();
        CreditCard creditCard = null;
        try {
            creditCard = findCreditCardById(connection, id);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return creditCard;
    }

    public List<CreditCard> findCByAccountId(Integer id){
        Connection connection = connectionPool.takeConnection();
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            creditCards = findCreditCardByAccountId(connection, id);
        } catch (SQLException e){
            //todo implement logger and custom exception
            e.printStackTrace();
        }
        return creditCards;
    }

    private void saveCreditCard(Connection connection, CreditCard card) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_CREDIT_CARD);
        preparedStatement.setString(1, card.getName());
        preparedStatement.setString(2, card.getExpireDate().toString());
        preparedStatement.setInt(3, card.getAccountId());
        preparedStatement.executeUpdate();
    }

    private void saveBankAccount(Connection connection, BankAccount account) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_BANK_ACCOUNT);
        preparedStatement.setInt(1, account.getBalance());
        preparedStatement.setBoolean(2, account.getBlocked());
        preparedStatement.executeUpdate();
    }

    private List<CreditCard> findAllCreditCard(Connection connection) throws SQLException{
        List<CreditCard> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_CREDIT_CARD);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            try {
                CreditCard creditCard = new CreditCard(resultSet.getInt(1),
                        findBankAccountById(connection, resultSet.getInt(1)),
                        resultSet.getString(2),
                        new SimpleDateFormat().parse(resultSet.getString(3)),
                        resultSet.getInt(4));
                result.add(creditCard);
            } catch (ParseException e) {
                CreditCard creditCard = new CreditCard(findBankAccountById(connection, resultSet.getInt(1)),
                        resultSet.getString(2),
                        new Date(),
                        resultSet.getInt(4));
                //todo and logger
                result.add(creditCard);
                e.printStackTrace();
            }
        }
        return result;
    }

    private CreditCard findCreditCardById(Connection connection, Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_CREDIT_CARD_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        if (resultSet.next()){
            CreditCard creditCard;
            try {
                creditCard = new CreditCard(resultSet.getInt(1),
                        findBankAccountById(connection, resultSet.getInt(1)),
                        resultSet.getString(2),
                        new SimpleDateFormat().parse(resultSet.getString(3)),
                        resultSet.getInt(4));
            } catch (ParseException e) {
                creditCard = new CreditCard(findBankAccountById(connection, resultSet.getInt(1)),
                        resultSet.getString(2),
                        new Date(),
                        resultSet.getInt(4));
                //todo and logger
                e.printStackTrace();
            }
            return creditCard;
        } else {
            return null;
        }
    }

    private BankAccount findBankAccountById(Connection connection, Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BANK_ACCOUNT_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return new BankAccount(resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getBoolean(3));
        } else {
            return null;
        }
    }

    private List<CreditCard> findCreditCardByAccountId(Connection connection, Integer accountId)throws SQLException{
        List<CreditCard> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_CREDIT_CARD_BY_USER_ID);
        preparedStatement.setInt(1, accountId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            try {
                CreditCard creditCard = new CreditCard(resultSet.getInt(1),
                        findBankAccountById(connection, resultSet.getInt(1)),
                        resultSet.getString(2),
                        new SimpleDateFormat().parse(resultSet.getString(3)),
                        resultSet.getInt(4));
                result.add(creditCard);
            } catch (ParseException e) {
                CreditCard creditCard = new CreditCard(findBankAccountById(connection, resultSet.getInt(1)),
                        resultSet.getString(2),
                        new Date(),
                        resultSet.getInt(4));
                //todo and logger
                result.add(creditCard);
                e.printStackTrace();
            }
        }
        return result;
    }

    private Boolean updateCreditCardById(Connection connection, CreditCard creditCard) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_CREDIT_CARD_BY_ID);
        preparedStatement.setString(1, creditCard.getName());
        preparedStatement.setString(2, creditCard.getExpireDate().toString());
        preparedStatement.setInt(3, creditCard.getAccountId());
        preparedStatement.setInt(4, creditCard.getId());
        return Objects.equals(preparedStatement.executeUpdate(), 1)
                && updateBankAccountById(connection, creditCard.getBankAccount());
    }

    private Boolean updateBankAccountById(Connection connection, BankAccount bankAccount) throws  SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BANK_ACCOUNT_BY_ID);
        preparedStatement.setInt(1, bankAccount.getBalance());
        preparedStatement.setBoolean(2, bankAccount.getBlocked());
        preparedStatement.setInt(3, bankAccount.getId());
        return  Objects.equals(preparedStatement.executeUpdate(), 1);
    }

    private Boolean deleteCreditCardById(Connection connection, Integer id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_CREDIT_CARD_BY_ID);
        preparedStatement.setInt(1, id);
        return Objects.equals(preparedStatement.executeUpdate(), 1)
                && deleteBankAccountById(connection, id);
    }

    private Boolean deleteBankAccountById(Connection connection, Integer id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BANK_ACCOUNT_BY_ID);
        preparedStatement.setInt(1, id);
        return Objects.equals(preparedStatement.executeUpdate(), 1);
    }
}
