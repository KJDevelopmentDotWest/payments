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
import java.util.*;

public class CreditCardDao implements Dao<CreditCard, Integer> {

    private static final Logger logger = LogManager.getLogger(CreditCardDao.class);

    private static final String SQL_SAVE_CREDIT_CARD = "INSERT INTO credit_cards (name, expire_date, user_id, number) VALUES (?, ?, ?, ?)";
    private static final String SQL_SAVE_BANK_ACCOUNT = "INSERT INTO bank_accounts (balance, blocked, credit_card_id) VALUES (?, ?, ?)";

    private static final String SQL_FIND_ALL_CREDIT_CARDS = "SELECT credit_cards.id, name, expire_date, user_id, number, bank_accounts.id, balance, blocked " +
            " FROM credit_cards JOIN bank_accounts ON credit_cards.id = credit_card_id";
    private static final String SQL_FIND_CREDIT_CARD_BY_ID = "SELECT credit_cards.id, name, expire_date, user_id, number, bank_accounts.id, balance, blocked " +
            "FROM credit_cards JOIN bank_accounts ON credit_cards.id = credit_card_id " +
            "WHERE credit_cards.id = ?";
    private static final String SQL_FIND_CREDIT_CARD_BY_NUMBER = "SELECT credit_cards.id, name, expire_date, user_id, number, bank_accounts.id, balance, blocked " +
            "FROM credit_cards JOIN bank_accounts ON credit_cards.id = credit_card_id " +
            "WHERE number = ?";
    private static final String SQL_FIND_CREDIT_CARD_BY_USER_ID = "SELECT credit_cards.id, name, expire_date, user_id, number, bank_accounts.id, balance, blocked " +
            "FROM credit_cards JOIN bank_accounts ON credit_cards.id = credit_card_id " +
            "WHERE user_id = ?";
    private static final String SQL_FIND_CREDIT_CARD_BY_USER_ID_WITHIN_RANGE = "SELECT credit_cards.id, name, expire_date, user_id, number, bank_accounts.id, balance, blocked " +
            "FROM credit_cards JOIN bank_accounts ON credit_cards.id = credit_card_id " +
            "WHERE user_id = ? " +
            "LIMIT ? OFFSET ?";

    private static final String SQL_FIND_CREDIT_CARD_BY_USER_ID_ORDERED_BY_NAME_WITHIN_RANGE = "SELECT credit_cards.id, name, expire_date, user_id, number, bank_accounts.id, balance, blocked " +
            "FROM credit_cards JOIN bank_accounts ON credit_cards.id = credit_card_id " +
            "WHERE user_id = ? " +
            "ORDER BY name " +
            "LIMIT ? OFFSET ?";

    private static final String SQL_FIND_CREDIT_CARD_BY_USER_ID_ORDERED_BY_EXPIRE_DATE_WITHIN_RANGE = "SELECT credit_cards.id, name, expire_date, user_id, number, bank_accounts.id, balance, blocked " +
            "FROM credit_cards JOIN bank_accounts ON credit_cards.id = credit_card_id " +
            "WHERE user_id = ? " +
            "ORDER BY expire_date " +
            "LIMIT ? OFFSET ?";

    private static final String SQL_FIND_CREDIT_CARD_BY_USER_ID_ORDERED_BY_BALANCE_WITHIN_RANGE = "SELECT credit_cards.id, name, expire_date, user_id, number, bank_accounts.id, balance, blocked " +
            "FROM credit_cards JOIN bank_accounts ON credit_cards.id = credit_card_id " +
            "WHERE user_id = ? " +
            "ORDER BY balance " +
            "LIMIT ? OFFSET ?";

    private static final String SQL_FIND_CREDIT_CARD_BY_USER_ID_ORDERED_BY_STATE_WITHIN_RANGE = "SELECT credit_cards.id, name, expire_date, user_id, number, bank_accounts.id, balance, blocked " +
            "FROM credit_cards JOIN bank_accounts ON credit_cards.id = credit_card_id " +
            "WHERE user_id = ? " +
            "ORDER BY blocked " +
            "LIMIT ? OFFSET ?";

    private static final String SQL_FIND_CREDIT_CARD_BY_USER_ID_ORDERED_BY_NUMBER_WITHIN_RANGE = "SELECT credit_cards.id, name, expire_date, user_id, number, bank_accounts.id, balance, blocked " +
            "FROM credit_cards JOIN bank_accounts ON credit_cards.id = credit_card_id " +
            "WHERE user_id = ? " +
            "ORDER BY number " +
            "LIMIT ? OFFSET ?";

    private static final String SQL_FIND_ALL_CREDIT_CARDS_ORDERED_BY_ID_WITHIN_RANGE = "SELECT credit_cards.id, name, expire_date, user_id, number, bank_accounts.id, balance, blocked " +
            "FROM credit_cards JOIN bank_accounts ON credit_cards.id = credit_card_id " +
            "ORDER BY credit_cards.id " +
            "LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_CREDIT_CARDS_ORDERED_BY_USER_ID_WITHIN_RANGE = "SELECT credit_cards.id, name, expire_date, user_id, number, bank_accounts.id, balance, blocked " +
            "FROM credit_cards JOIN bank_accounts ON credit_cards.id = credit_card_id " +
            "ORDER BY user_id " +
            "LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_CREDIT_CARDS_ORDERED_BY_NAME_WITHIN_RANGE = "SELECT credit_cards.id, name, expire_date, user_id, number, bank_accounts.id, balance, blocked " +
            "FROM credit_cards JOIN bank_accounts ON credit_cards.id = credit_card_id " +
            "ORDER BY name " +
            "LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_CREDIT_CARDS_ORDERED_BY_NUMBER_WITHIN_RANGE = "SELECT credit_cards.id, name, expire_date, user_id, number, bank_accounts.id, balance, blocked " +
            "FROM credit_cards JOIN bank_accounts ON credit_cards.id = credit_card_id " +
            "ORDER BY number " +
            "LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_CREDIT_CARDS_ORDERED_BY_EXPIRE_DATE_WITHIN_RANGE = "SELECT credit_cards.id, name, expire_date, user_id, number, bank_accounts.id, balance, blocked " +
            "FROM credit_cards JOIN bank_accounts ON credit_cards.id = credit_card_id " +
            "ORDER BY expire_date " +
            "LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_CREDIT_CARDS_ORDERED_BY_BALANCE_WITHIN_RANGE = "SELECT credit_cards.id, name, expire_date, user_id, number, bank_accounts.id, balance, blocked " +
            "FROM credit_cards JOIN bank_accounts ON credit_cards.id = credit_card_id " +
            "ORDER BY balance " +
            "LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_CREDIT_CARDS_ORDERED_BY_STATE_WITHIN_RANGE = "SELECT credit_cards.id, name, expire_date, user_id, number, bank_accounts.id, balance, blocked " +
            "FROM credit_cards JOIN bank_accounts ON credit_cards.id = credit_card_id " +
            "ORDER BY blocked " +
            "LIMIT ? OFFSET ?";

    private static final String SQL_UPDATE_CREDIT_CARD_BY_ID = "UPDATE credit_cards SET name = ?, expire_date = ?, user_id = ?, number = ? WHERE id = ?";
    private static final String SQL_UPDATE_BANK_ACCOUNT_BY_ID = "UPDATE bank_accounts SET balance = ?, blocked = ?, credit_card_id = ? WHERE id = ?";

    private static final String SQL_DELETE_CREDIT_CARD_BY_ID = "DELETE FROM credit_cards WHERE id = ?";
    private static final String SQL_DELETE_BANK_ACCOUNT_BY_ID = "DELETE FROM bank_accounts WHERE id = ?";

    private static final String SQL_COUNT_CREDIT_CARDS = "SELECT COUNT(id) as credit_cards_number FROM credit_cards";
    private static final String SQL_COUNT_CREDIT_CARDS_WITH_USER_ID = "SELECT COUNT(id) as credit_cards_number FROM credit_cards WHERE user_id = ?";

    private static final Integer KEY_FIND_ALL_ORDER_BY_ID = 1;
    private static final Integer KEY_FIND_ALL_ORDER_BY_USER_ID = 2;
    private static final Integer KEY_FIND_ALL_ORDER_BY_NAME = 3;
    private static final Integer KEY_FIND_ALL_ORDER_BY_NUMBER = 4;
    private static final Integer KEY_FIND_ALL_ORDER_BY_EXPIRE_DATE = 5;
    private static final Integer KEY_FIND_ALL_ORDER_BY_BALANCE = 6;
    private static final Integer KEY_FIND_ALL_ORDER_BY_STATE = 7;

    private static final Integer KEY_FIND_BY_USER_ID_ORDER_BY_NAME = 1;
    private static final Integer KEY_FIND_BY_USER_ID_ORDER_BY_EXPIRE_DATE = 2;
    private static final Integer KEY_FIND_BY_USER_ID_ORDER_BY_BALANCE = 3;
    private static final Integer KEY_FIND_BY_USER_ID_ORDER_BY_STATE = 4;
    private static final Integer KEY_FIND_BY_USER_ID_ORDER_BY_NUMBER = 5;

    private static final Map<Integer, String> FIND_ALL_ORDER_BY_KEY_TO_SQL_STRING = new HashMap<>();
    private static final Map<Integer, String> FIND_BY_USER_ID_ORDER_BY_KEY_TO_SQL_STRING = new HashMap<>();

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    static {
        FIND_ALL_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_ALL_ORDER_BY_ID, SQL_FIND_ALL_CREDIT_CARDS_ORDERED_BY_ID_WITHIN_RANGE);
        FIND_ALL_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_ALL_ORDER_BY_USER_ID, SQL_FIND_ALL_CREDIT_CARDS_ORDERED_BY_USER_ID_WITHIN_RANGE);
        FIND_ALL_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_ALL_ORDER_BY_NAME, SQL_FIND_ALL_CREDIT_CARDS_ORDERED_BY_NAME_WITHIN_RANGE);
        FIND_ALL_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_ALL_ORDER_BY_NUMBER, SQL_FIND_ALL_CREDIT_CARDS_ORDERED_BY_NUMBER_WITHIN_RANGE);
        FIND_ALL_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_ALL_ORDER_BY_EXPIRE_DATE, SQL_FIND_ALL_CREDIT_CARDS_ORDERED_BY_EXPIRE_DATE_WITHIN_RANGE);
        FIND_ALL_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_ALL_ORDER_BY_BALANCE, SQL_FIND_ALL_CREDIT_CARDS_ORDERED_BY_BALANCE_WITHIN_RANGE);
        FIND_ALL_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_ALL_ORDER_BY_STATE, SQL_FIND_ALL_CREDIT_CARDS_ORDERED_BY_STATE_WITHIN_RANGE);

        FIND_BY_USER_ID_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_BY_USER_ID_ORDER_BY_NAME, SQL_FIND_CREDIT_CARD_BY_USER_ID_ORDERED_BY_NAME_WITHIN_RANGE);
        FIND_BY_USER_ID_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_BY_USER_ID_ORDER_BY_EXPIRE_DATE, SQL_FIND_CREDIT_CARD_BY_USER_ID_ORDERED_BY_EXPIRE_DATE_WITHIN_RANGE);
        FIND_BY_USER_ID_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_BY_USER_ID_ORDER_BY_BALANCE, SQL_FIND_CREDIT_CARD_BY_USER_ID_ORDERED_BY_BALANCE_WITHIN_RANGE);
        FIND_BY_USER_ID_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_BY_USER_ID_ORDER_BY_STATE, SQL_FIND_CREDIT_CARD_BY_USER_ID_ORDERED_BY_STATE_WITHIN_RANGE);
        FIND_BY_USER_ID_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_BY_USER_ID_ORDER_BY_NUMBER, SQL_FIND_CREDIT_CARD_BY_USER_ID_ORDERED_BY_NUMBER_WITHIN_RANGE);
    }

    /**
     *
     * @param entity credit card to be saved
     * @return credit card with id generated by database, null if saving was unsuccessful
     */
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

    /**
     * method finds row with id == entity.id and updates all others values
     * @param entity credit card to be updated
     * @return true if update was successful, false otherwise
     */
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

    /**
     * method deletes row with id == entity.id
     * @param entity credit card to be deleted
     * @return true if delete was successful, false otherwise
     */
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

    /**
     *
     * @return list of all credit cards
     */
    @Override
    public List<CreditCard> findAll() {
        logger.info("find all method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        List<CreditCard> creditCards = new ArrayList<>();

        try {
            creditCards = findAllCreditCards(connection);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }

        return creditCards;
    }

    /**
     *
     * @param id id of credit card
     * @return credit card with provided id, null if there is no credit card with provided id
     */
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

    /**
     *
     * @return number of credit cards
     */
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

    /**
     *
     * @param id id of user
     * @return number of credit cards with userId == id
     */
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

    /**
     *
     * @param id id of user
     * @return list of credit cards where userId == id, empty list if there are no such credit cards
     */
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

    /**
     *
     * @param id user id
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of credit cards
     */
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

    /**
     *
     * @param number number of credit cards
     * @return credit card with provided number, null if there is no credit card with provided number
     */
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

    /**
     *
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of credit cards ordered by id
     */
    public List<CreditCard> findAllOrderedByIdWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by user id within range method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            creditCards = findAllCreditCardsOrderedByWithinRange(connection, limit, offset, KEY_FIND_ALL_ORDER_BY_ID);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return creditCards;
    }

    /**
     *
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of credit cards ordered by user id
     */
    public List<CreditCard> findAllOrderedByUserIdWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by user id within range method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            creditCards = findAllCreditCardsOrderedByWithinRange(connection, limit, offset, KEY_FIND_ALL_ORDER_BY_USER_ID);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return creditCards;
    }

    /**
     *
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of credit cards ordered by name
     */
    public List<CreditCard> findAllOrderedByNameWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by user id within range method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            creditCards = findAllCreditCardsOrderedByWithinRange(connection, limit, offset, KEY_FIND_ALL_ORDER_BY_NAME);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return creditCards;
    }

    /**
     *
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of credit cards ordered by number
     */
    public List<CreditCard> findAllOrderedByNumberWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by user id within range method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            creditCards = findAllCreditCardsOrderedByWithinRange(connection, limit, offset, KEY_FIND_ALL_ORDER_BY_NUMBER);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return creditCards;
    }

    /**
     *
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of credit cards ordered by expire date
     */
    public List<CreditCard> findAllOrderedByExpireDateWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by user id within range method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            creditCards = findAllCreditCardsOrderedByWithinRange(connection, limit, offset, KEY_FIND_ALL_ORDER_BY_EXPIRE_DATE);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return creditCards;
    }

    /**
     *
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of credit cards ordered by bank account balance
     */
    public List<CreditCard> findAllOrderedByBalanceWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by user id within range method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            creditCards = findAllCreditCardsOrderedByWithinRange(connection, limit, offset, KEY_FIND_ALL_ORDER_BY_BALANCE);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return creditCards;
    }

    /**
     *
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of credit cards ordered by bank account state
     */
    public List<CreditCard> findAllOrderedByStateWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by user id within range method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            creditCards = findAllCreditCardsOrderedByWithinRange(connection, limit, offset, KEY_FIND_ALL_ORDER_BY_STATE);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return creditCards;
    }

    /**
     *
     * @param id id of user
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of credit cards with provided user id ordered by name
     */
    public List<CreditCard> findByUserIdOrderedByNameWithinRange(Integer id, Integer limit, Integer offset) {
        logger.info("find by user id ordered by name within range method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            creditCards = findCreditCardByUserIdWithinRangeOrderedBy(connection, id, limit, offset, KEY_FIND_BY_USER_ID_ORDER_BY_NAME);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return creditCards;
    }

    /**
     *
     * @param id id of user
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of credit cards with provided user id ordered by expire date
     */
    public List<CreditCard> findByUserIdOrderedByExpireDateWithinRange(Integer id, Integer limit, Integer offset) {
        logger.info("find by user id ordered by expire date within range method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            creditCards = findCreditCardByUserIdWithinRangeOrderedBy(connection, id, limit, offset, KEY_FIND_BY_USER_ID_ORDER_BY_EXPIRE_DATE);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return creditCards;
    }

    /**
     *
     * @param id id of user
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of credit cards with provided user id ordered by bank account balance
     */
    public List<CreditCard> findByUserIdOrderedByBalanceWithinRange(Integer id, Integer limit, Integer offset) {
        logger.info("find by user id ordered by balance within range method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            creditCards = findCreditCardByUserIdWithinRangeOrderedBy(connection, id, limit, offset, KEY_FIND_BY_USER_ID_ORDER_BY_BALANCE);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return creditCards;
    }

    /**
     *
     * @param id id of user
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of credit cards with provided user id ordered by bank account state
     */
    public List<CreditCard> findByUserIdOrderedByStateWithinRange(Integer id, Integer limit, Integer offset) {
        logger.info("find by user id ordered by state within range method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            creditCards = findCreditCardByUserIdWithinRangeOrderedBy(connection, id, limit, offset, KEY_FIND_BY_USER_ID_ORDER_BY_STATE);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return creditCards;
    }

    /**
     *
     * @param id id of user
     * @param limit amount of credit cards
     * @param offset offset from start of list in database
     * @return list of credit cards with provided user id ordered by number
     */
    public List<CreditCard> findByUserIdOrderedByNumberWithinRange(Integer id, Integer limit, Integer offset) {
        logger.info("find by user id ordered by number within range method " + CreditCardDao.class);
        Connection connection = connectionPool.takeConnection();
        List<CreditCard> creditCards = new ArrayList<>();
        try {
            creditCards = findCreditCardByUserIdWithinRangeOrderedBy(connection, id, limit, offset, KEY_FIND_BY_USER_ID_ORDER_BY_NUMBER);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return creditCards;
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

    private List<CreditCard> findAllCreditCards(Connection connection) throws SQLException{
        List<CreditCard> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_CREDIT_CARDS);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            result.add(convertResultSetToCreditCard(resultSet));
        }
        preparedStatement.close();
        resultSet.close();
        return result;
    }

    private List<CreditCard> findAllCreditCardsOrderedByWithinRange(Connection connection, Integer limit, Integer offset, Integer orderBy) throws SQLException{
        List<CreditCard> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ORDER_BY_KEY_TO_SQL_STRING.get(orderBy));
        preparedStatement.setInt(1, limit);
        preparedStatement.setInt(2, offset);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            result.add(convertResultSetToCreditCard(resultSet));
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
            creditCard = convertResultSetToCreditCard(resultSet);
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
            creditCard = convertResultSetToCreditCard(resultSet);
        } else {
            creditCard =  null;
        }
        preparedStatement.close();
        resultSet.close();
        return creditCard;
    }

    private List<CreditCard> findCreditCardByUserId(Connection connection, Integer userId)throws SQLException{
        List<CreditCard> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_CREDIT_CARD_BY_USER_ID);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            result.add(convertResultSetToCreditCard(resultSet));
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
            result.add(convertResultSetToCreditCard(resultSet));
        }
        preparedStatement.close();
        resultSet.close();
        return result;
    }

    private List<CreditCard> findCreditCardByUserIdWithinRangeOrderedBy(Connection connection, Integer userId, Integer limit, Integer offset, Integer orderBy)throws SQLException{
        List<CreditCard> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USER_ID_ORDER_BY_KEY_TO_SQL_STRING.get(orderBy));
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, limit);
        preparedStatement.setInt(3, offset);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            result.add(convertResultSetToCreditCard(resultSet));
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

    private CreditCard convertResultSetToCreditCard(ResultSet resultSet) throws SQLException {
        CreditCard creditCard;
        try {
            creditCard = new CreditCard(resultSet.getInt(1),
                    new BankAccount(
                            resultSet.getInt(6),
                            resultSet.getLong(7),
                            resultSet.getBoolean(8)),
                    resultSet.getString(2),
                    Date.from(Instant.parse(resultSet.getString(3))),
                    resultSet.getInt(4),
                    resultSet.getLong(5));

        } catch (DateTimeParseException | NullPointerException e) {
            creditCard = new CreditCard(resultSet.getInt(1),
                    new BankAccount(
                            resultSet.getInt(6),
                            resultSet.getLong(7),
                            resultSet.getBoolean(8)),
                    resultSet.getString(2),
                    null,
                    resultSet.getInt(4),
                    resultSet.getLong(5));
            //todo and logger
            logger.error(e);
        }
        return creditCard;
    }
}
