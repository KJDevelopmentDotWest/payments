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
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CreditCardDao implements Dao<CreditCard, Integer> {

    private static final Logger logger = LogManager.getLogger(CreditCardDao.class);

    private static final String SQL_SAVE_CREDIT_CARD = "INSERT INTO credit_cards (name, expire_date, user_id, number) VALUES (?, ?, ?, ?)";
    private static final String SQL_SAVE_BANK_ACCOUNT = "INSERT INTO bank_accounts (balance, blocked, credit_card_id) VALUES (?, ?, ?)";

    private static final String SQL_FIND_ALL_CREDIT_CARD = "SELECT id, name, expire_date, user_id, number FROM credit_cards";
    private static final String SQL_FIND_CREDIT_CARD_BY_ID = "SELECT id, name, expire_date, user_id, number FROM credit_cards WHERE id = ?";
    private static final String SQL_FIND_CREDIT_CARD_BY_NUMBER = "SELECT id, name, expire_date, user_id, number FROM credit_cards WHERE number = ?";
    private static final String SQL_FIND_CREDIT_CARD_BY_USER_ID = "SELECT id, name, expire_date, user_id, number FROM credit_cards WHERE user_id = ?";
    private static final String SQL_FIND_CREDIT_CARD_BY_USER_ID_WITHIN_RANGE = "SELECT id, name, expire_date, user_id, number FROM credit_cards WHERE user_id = ? LIMIT ? OFFSET ?";

    private static final String SQL_FIND_BANK_ACCOUNT_BY_ID = "SELECT id, balance, blocked, credit_card_id FROM bank_accounts WHERE id = ?";
    private static final String SQL_FIND_BANK_ACCOUNT_BY_CREDIT_CARD_ID = "SELECT id, balance, blocked, credit_card_id FROM bank_accounts WHERE credit_card_id = ?";

    private static final String SQL_UPDATE_CREDIT_CARD_BY_ID = "UPDATE credit_cards SET name = ?, expire_date = ?, user_id = ?, number = ? WHERE id = ?";
    private static final String SQL_UPDATE_BANK_ACCOUNT_BY_ID = "UPDATE bank_accounts SET balance = ?, blocked = ?, credit_card_id = ? WHERE id = ?";

    private static final String SQL_DELETE_CREDIT_CARD_BY_ID = "DELETE FROM credit_cards WHERE id = ?";
    private static final String SQL_DELETE_BANK_ACCOUNT_BY_ID = "DELETE FROM bank_accounts WHERE id = ?";

    private static final String SQL_COUNT_CREDIT_CARDS = "SELECT COUNT(id) as credit_cards_number FROM credit_cards";
    private static final String SQL_COUNT_CREDIT_CARDS_WITH_USER_ID = "SELECT COUNT(id) as credit_cards_number FROM credit_cards WHERE user_id = ?";


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

    public Integer getRowsNumberWithUserId(Integer id) {
        logger.info("get row number with user id method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        Integer result = null;
        try {
            result = getRowNumberWithUserID(connection, id);
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
            creditCards = findCreditCardByUserId(connection, id);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        }
        return creditCards;
    }

    public List<CreditCard> findByUserIdWithinRange(Integer id, Integer limit, Integer offset) {
        logger.info("find by user id within range method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            creditCards = findCreditCardByUserIdWithinRange(connection, id, limit, offset);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return creditCards;
    }

    public CreditCard findByCreditCardNumber(Long number){
        logger.info("find by credit card number method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        CreditCard creditCard = null;
        try {
            creditCard = findCreditCardByNumber(connection, number);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return creditCard;
    }

    private CreditCard saveCreditCard(Connection connection, CreditCard creditCard) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_CREDIT_CARD, new String[] {"id"});
        preparedStatement.setString(1, creditCard.getName());
        preparedStatement.setString(2, creditCard.getExpireDate().toInstant().toString());
        preparedStatement.setInt(3, creditCard.getUserId());
        preparedStatement.setLong(4, creditCard.getCardNumber());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        Integer id = resultSet.getInt(1);
        creditCard.setId(id);
        preparedStatement.close();
        saveBankAccount(connection, creditCard.getBankAccount(), creditCard.getId());
        return creditCard;
    }

    private void saveBankAccount(Connection connection, BankAccount account, Integer creditCardId) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_BANK_ACCOUNT, new String[] {"id"});
        preparedStatement.setLong(1, account.getBalance());
        preparedStatement.setBoolean(2, account.getBlocked());
        preparedStatement.setInt(3, creditCardId);
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
                        findBankAccountByCreditCardId(connection, resultSet.getInt(1)),
                        resultSet.getString(2),
                        Date.from(Instant.parse(resultSet.getString(3))),
                        resultSet.getInt(4),
                        resultSet.getLong(5));
                result.add(creditCard);
            } catch (DateTimeParseException | NullPointerException e) {
                CreditCard creditCard = new CreditCard(resultSet.getInt(1),
                        findBankAccountByCreditCardId(connection, resultSet.getInt(1)),
                        resultSet.getString(2),
                        new Date(),
                        resultSet.getInt(4),
                        resultSet.getLong(5));
                //todo and logger
                result.add(creditCard);
                logger.error(e);
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
        CreditCard creditCard;
        if (resultSet.next()){
            try {
                creditCard = new CreditCard(resultSet.getInt(1),
                        findBankAccountByCreditCardId(connection, resultSet.getInt(1)),
                        resultSet.getString(2),
                        Date.from(Instant.parse(resultSet.getString(3))),
                        resultSet.getInt(4),
                        resultSet.getLong(5));
            } catch (DateTimeParseException | NullPointerException e) {
                creditCard = new CreditCard(resultSet.getInt(1),
                        findBankAccountByCreditCardId(connection, resultSet.getInt(1)),
                        resultSet.getString(2),
                        new Date(),
                        resultSet.getInt(4),
                        resultSet.getLong(5));
                //todo and logger
                logger.error(e);
            }
        } else {
            creditCard =  null;
        }
        preparedStatement.close();
        resultSet.close();
        return creditCard;
    }

    private CreditCard findCreditCardByNumber(Connection connection, Long number) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_CREDIT_CARD_BY_NUMBER);
        preparedStatement.setLong(1, number);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        CreditCard creditCard;
        if (resultSet.next()){
            try {
                creditCard = new CreditCard(resultSet.getInt(1),
                        findBankAccountByCreditCardId(connection, resultSet.getInt(1)),
                        resultSet.getString(2),
                        Date.from(Instant.parse(resultSet.getString(3))),
                        resultSet.getInt(4),
                        resultSet.getLong(5));
            } catch (DateTimeParseException | NullPointerException e) {
                creditCard = new CreditCard(resultSet.getInt(1),
                        findBankAccountByCreditCardId(connection, resultSet.getInt(1)),
                        resultSet.getString(2),
                        new Date(),
                        resultSet.getInt(4),
                        resultSet.getLong(5));
                //todo and logger
                logger.error(e);
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
                    resultSet.getLong(2),
                    resultSet.getBoolean(3));
        } else {
            bankAccount = null;
        }
        preparedStatement.close();
        resultSet.close();
        return bankAccount;
    }

    private BankAccount findBankAccountByCreditCardId(Connection connection, Integer creditCardId) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BANK_ACCOUNT_BY_CREDIT_CARD_ID);
        preparedStatement.setInt(1, creditCardId);
        ResultSet resultSet = preparedStatement.executeQuery();
        BankAccount bankAccount;
        if (resultSet.next()){
            bankAccount = new BankAccount(resultSet.getInt(1),
                    resultSet.getLong(2),
                    resultSet.getBoolean(3));
        } else {
            bankAccount = null;
        }
        preparedStatement.close();
        resultSet.close();
        return bankAccount;
    }

    private List<CreditCard> findCreditCardByUserId(Connection connection, Integer userId)throws SQLException{
        List<CreditCard> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_CREDIT_CARD_BY_USER_ID);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            try {
                CreditCard creditCard = new CreditCard(resultSet.getInt(1),
                        findBankAccountByCreditCardId(connection, resultSet.getInt(1)),
                        resultSet.getString(2),
                        Date.from(Instant.parse(resultSet.getString(3))),
                        resultSet.getInt(4),
                        resultSet.getLong(5));
                result.add(creditCard);
            } catch (DateTimeParseException | NullPointerException e) {
                CreditCard creditCard = new CreditCard(resultSet.getInt(1),
                        findBankAccountByCreditCardId(connection, resultSet.getInt(1)),
                        resultSet.getString(2),
                        new Date(),
                        resultSet.getInt(4),
                        resultSet.getLong(5));
                //todo and logger
                result.add(creditCard);
                logger.error(e);
            }
        }
        preparedStatement.close();
        resultSet.close();
        return result;
    }

    private List<CreditCard> findCreditCardByUserIdWithinRange(Connection connection, Integer userId, Integer limit, Integer offset)throws SQLException{
        List<CreditCard> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_CREDIT_CARD_BY_USER_ID_WITHIN_RANGE);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, limit);
        preparedStatement.setInt(3, offset);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            try {
                CreditCard creditCard = new CreditCard(resultSet.getInt(1),
                        findBankAccountByCreditCardId(connection, resultSet.getInt(1)),
                        resultSet.getString(2),
                        Date.from(Instant.parse(resultSet.getString(3))),
                        resultSet.getInt(4),
                        resultSet.getLong(5));
                result.add(creditCard);
            } catch (DateTimeParseException | NullPointerException e) {
                CreditCard creditCard = new CreditCard(resultSet.getInt(1),
                        findBankAccountByCreditCardId(connection, resultSet.getInt(1)),
                        resultSet.getString(2),
                        new Date(),
                        resultSet.getInt(4),
                        resultSet.getLong(5));
                //todo and logger
                result.add(creditCard);
                logger.error(e);
            }
        }
        preparedStatement.close();
        resultSet.close();
        return result;
    }

    private Boolean updateCreditCardById(Connection connection, CreditCard creditCard) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_CREDIT_CARD_BY_ID);
        preparedStatement.setString(1, creditCard.getName());
        preparedStatement.setString(2, creditCard.getExpireDate().toInstant().toString());
        preparedStatement.setInt(3, creditCard.getUserId());
        preparedStatement.setLong(4, creditCard.getCardNumber());
        preparedStatement.setInt(5, creditCard.getId());
        Boolean result = Objects.equals(preparedStatement.executeUpdate(), 1)
                && updateBankAccountById(connection, creditCard.getBankAccount(), creditCard.getId());
        preparedStatement.close();
        return result;
    }

    private Boolean updateBankAccountById(Connection connection, BankAccount bankAccount, Integer userId) throws  SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BANK_ACCOUNT_BY_ID);
        preparedStatement.setLong(1, bankAccount.getBalance());
        preparedStatement.setBoolean(2, bankAccount.getBlocked());
        preparedStatement.setInt(3, userId);
        preparedStatement.setInt(4, bankAccount.getId());
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
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_COUNT_CREDIT_CARDS);
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

    private Integer getRowNumberWithUserID(Connection connection, Integer id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_COUNT_CREDIT_CARDS_WITH_USER_ID);
        preparedStatement.setInt(1, id);
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
