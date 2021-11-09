package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.connectionpool.api.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.model.creditcard.BankAccount;
import com.epam.jwd.dao.model.creditcard.CreditCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

public class CreditCardDao implements Dao<CreditCard, Integer> {

    private static final Logger logger = LogManager.getLogger(CreditCardDao.class);

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

    private static final String SQL_COUNT_USERS = "SELECT COUNT(id) as credit_cards_number FROM credit_cards";


    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public CreditCard save(CreditCard entity) {
        logger.info("save method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        try {
            CreditCard creditCard;
            connection.setAutoCommit(false);
            creditCard = saveCreditCard(connection, entity);
            connection.commit();
            connection.setAutoCommit(true);
            return creditCard;
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
            return null;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean update(CreditCard entity) {
        logger.info("update method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        try {
            connection.setAutoCommit(false);
            Boolean result = updateCreditCardById(connection, entity);
            connection.commit();
            connection.setAutoCommit(true);
            return result;
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(CreditCard entity) {
        logger.info("delete method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        try {
            connection.setAutoCommit(false);
            Boolean result = deleteCreditCardById(connection, entity.getId());
            connection.commit();
            connection.setAutoCommit(true);
            return result;
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<CreditCard> findAll() {
        logger.info("find all method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        List<CreditCard> creditCards = new ArrayList<>();

        try {
            creditCards = findAllCreditCard(connection);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return creditCards;
    }

    @Override
    public Integer getRowsNumber() {
        logger.info("get row number method " + CreditCardDao.class);
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

    @Override
    public CreditCard findById(Integer id) {
        logger.info("find by id method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        CreditCard creditCard = null;
        try {
            creditCard = findCreditCardById(connection, id);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return creditCard;
    }

    public List<CreditCard> findByUserId(Integer id){
        logger.info("find by user id method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            creditCards = findCreditCardByAccountId(connection, id);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        }
        return creditCards;
    }

    private CreditCard saveCreditCard(Connection connection, CreditCard card) throws SQLException {
        saveBankAccount(connection, card.getBankAccount());
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_CREDIT_CARD);
        preparedStatement.setString(1, card.getName());
        preparedStatement.setString(2, card.getExpireDate().toString());
        preparedStatement.setInt(3, card.getUserId());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        Integer id = resultSet.getInt(1);
        card.setId(id);
        preparedStatement.close();
        return card;
    }

    private void saveBankAccount(Connection connection, BankAccount account) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_BANK_ACCOUNT);
        preparedStatement.setInt(1, account.getBalance());
        preparedStatement.setBoolean(2, account.getBlocked());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        Integer id = resultSet.getInt(1);
        account.setId(id);
        preparedStatement.close();
        resultSet.close();
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
        preparedStatement.close();
        resultSet.close();
        return result;
    }

    private CreditCard findCreditCardById(Connection connection, Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_CREDIT_CARD_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        CreditCard creditCard;
        if (resultSet.next()){
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
        } else {
            creditCard =  null;
        }
        preparedStatement.close();
        resultSet.close();
        return creditCard;
    }

    private BankAccount findBankAccountById(Connection connection, Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BANK_ACCOUNT_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        BankAccount bankAccount;
        if (resultSet.next()){
            bankAccount = new BankAccount(resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getBoolean(3));
        } else {
            bankAccount = null;
        }
        preparedStatement.close();
        resultSet.close();
        return bankAccount;
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
        preparedStatement.close();
        resultSet.close();
        return result;
    }

    private Boolean updateCreditCardById(Connection connection, CreditCard creditCard) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_CREDIT_CARD_BY_ID);
        preparedStatement.setString(1, creditCard.getName());
        preparedStatement.setString(2, creditCard.getExpireDate().toString());
        preparedStatement.setInt(3, creditCard.getUserId());
        preparedStatement.setInt(4, creditCard.getId());
        Boolean result = Objects.equals(preparedStatement.executeUpdate(), 1)
                && updateBankAccountById(connection, creditCard.getBankAccount());
        preparedStatement.close();
        return result;
    }

    private Boolean updateBankAccountById(Connection connection, BankAccount bankAccount) throws  SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BANK_ACCOUNT_BY_ID);
        preparedStatement.setInt(1, bankAccount.getBalance());
        preparedStatement.setBoolean(2, bankAccount.getBlocked());
        preparedStatement.setInt(3, bankAccount.getId());
        Boolean result = Objects.equals(preparedStatement.executeUpdate(), 1);
        preparedStatement.close();
        return result;
    }

    private Boolean deleteCreditCardById(Connection connection, Integer id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_CREDIT_CARD_BY_ID);
        preparedStatement.setInt(1, id);
        Boolean result = Objects.equals(preparedStatement.executeUpdate(), 1)
                && deleteBankAccountById(connection, id);
        preparedStatement.close();
        return result;
    }

    private Boolean deleteBankAccountById(Connection connection, Integer id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BANK_ACCOUNT_BY_ID);
        preparedStatement.setInt(1, id);
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
