package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.connectionpool.api.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.model.payment.Payment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.*;

public class PaymentDao implements Dao<Payment, Integer> {

    private static final Logger logger = LogManager.getLogger(PaymentDao.class);

    private static final String SQL_SAVE_PAYMENT = "INSERT INTO payments (user_id, destination_address, price, committed, time, name) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_FIND_ALL_PAYMENTS = "select id, user_id, destination_address, price, committed, time, name FROM payments";
    private static final String SQL_FIND_PAYMENT_BY_ID = "select id, user_id, destination_address, price, committed, time, name FROM payments WHERE id = ?";
    private static final String SQL_FIND_PAYMENTS_BY_USER_ID = "select id, user_id, destination_address, price, committed, time, name FROM payments WHERE user_id = ?";
    private static final String SQL_FIND_PAYMENTS_BY_USER_ID_WITHIN_RANGE = "select id, user_id, destination_address, price, committed, time, name FROM payments WHERE user_id = ? LIMIT ? OFFSET ?";

    private static final String SQL_FIND_PAYMENTS_BY_USER_ID_ORDER_BY_NAME_WITHIN_RANGE = "select id, user_id, destination_address, price, committed, time, name FROM payments ORDER BY name WHERE user_id = ? LIMIT ? OFFSET ?";
    private static final String SQL_FIND_PAYMENTS_BY_USER_ID_ORDER_BY_PRICE_WITHIN_RANGE = "select id, user_id, destination_address, price, committed, time, name FROM payments ORDER BY price WHERE user_id = ? LIMIT ? OFFSET ?";
    private static final String SQL_FIND_PAYMENTS_BY_USER_ID_ORDER_BY_DESTINATION_WITHIN_RANGE = "select id, user_id, destination_address, price, committed, time, name FROM payments ORDER BY destination_address WHERE user_id = ? LIMIT ? OFFSET ?";
    private static final String SQL_FIND_PAYMENTS_BY_USER_ID_ORDER_BY_TIME_WITHIN_RANGE = "select id, user_id, destination_address, price, committed, time, name FROM payments ORDER BY time WHERE user_id = ? LIMIT ? OFFSET ?";
    private static final String SQL_FIND_PAYMENTS_BY_USER_ID_ORDER_BY_COMMITTED_WITHIN_RANGE = "select id, user_id, destination_address, price, committed, time, name FROM payments ORDER BY committed WHERE user_id = ? LIMIT ? OFFSET ?";

    private static final String SQL_FIND_ALL_PAYMENTS_ORDERED_BY_USER_ID_WITHIN_RANGE = "select id, user_id, destination_address, price, committed, time, name FROM payments ORDER BY user_id LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_PAYMENTS_ORDERED_BY_NAME_WITHIN_RANGE = "select id, user_id, destination_address, price, committed, time, name FROM payments ORDER BY name LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_PAYMENTS_ORDERED_BY_PRICE_WITHIN_RANGE = "select id, user_id, destination_address, price, committed, time, name FROM payments ORDER BY price LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_PAYMENTS_ORDERED_BY_DESTINATION_ADDRESS_WITHIN_RANGE = "select id, user_id, destination_address, price, committed, time, name FROM payments ORDER BY destination_address LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_PAYMENTS_ORDERED_BY_TIME_WITHIN_RANGE = "select id, user_id, destination_address, price, committed, time, name FROM payments ORDER BY time LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_PAYMENTS_ORDERED_BY_COMMITTED_WITHIN_RANGE = "select id, user_id, destination_address, price, committed, time, name FROM payments ORDER BY committed LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_PAYMENTS_ORDERED_BY_ID_WITHIN_RANGE = "select id, user_id, destination_address, price, committed, time, name FROM payments ORDER BY id LIMIT ? OFFSET ?";

    private static final String SQL_UPDATE_PAYMENT_BY_ID = "UPDATE payments SET user_id = ?, destination_address = ?, price = ?, committed = ?, time = ?, name = ? WHERE id = ?";

    private static final String SQL_DELETE_PAYMENT_BY_ID = "DELETE FROM payments WHERE id = ?";

    private static final String SQL_COUNT_PAYMENTS = "SELECT COUNT(id) as payments_number FROM payments";
    private static final String SQL_COUNT_PAYMENTS_WITH_USER_ID = "SELECT COUNT(id) as payments_number FROM payments WHERE user_id = ?";

    private static final Integer KEY_FIND_ALL_ORDER_BY_ID = 1;
    private static final Integer KEY_FIND_ALL_ORDER_BY_USER_ID = 2;
    private static final Integer KEY_FIND_ALL_ORDER_BY_NAME = 3;
    private static final Integer KEY_FIND_ALL_ORDER_BY_PRICE = 4;
    private static final Integer KEY_FIND_ALL_ORDER_BY_DESTINATION_ADDRESS = 5;
    private static final Integer KEY_FIND_ALL_ORDER_BY_TIME = 6;
    private static final Integer KEY_FIND_ALL_ORDER_BY_COMMITTED = 7;

    private static final Integer KEY_FIND_BY_USER_ID_ORDER_BY_NAME = 1;
    private static final Integer KEY_FIND_BY_USER_ID_ORDER_BY_PRICE = 2;
    private static final Integer KEY_FIND_BY_USER_ID_ORDER_BY_DESTINATION_ADDRESS = 3;
    private static final Integer KEY_FIND_BY_USER_ID_ORDER_BY_TIME = 4;
    private static final Integer KEY_FIND_BY_USER_ID_ORDER_BY_COMMITTED = 5;

    private static final Map<Integer, String> FIND_ALL_ORDER_BY_KEY_TO_SQL_STRING = new HashMap<>();
    private static final Map<Integer, String> FIND_BY_USER_ID_ORDER_BY_KEY_TO_SQL_STRING = new HashMap<>();

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    static {
        FIND_ALL_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_ALL_ORDER_BY_ID, SQL_FIND_ALL_PAYMENTS_ORDERED_BY_ID_WITHIN_RANGE);
        FIND_ALL_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_ALL_ORDER_BY_USER_ID, SQL_FIND_ALL_PAYMENTS_ORDERED_BY_USER_ID_WITHIN_RANGE);
        FIND_ALL_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_ALL_ORDER_BY_NAME, SQL_FIND_ALL_PAYMENTS_ORDERED_BY_NAME_WITHIN_RANGE);
        FIND_ALL_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_ALL_ORDER_BY_PRICE, SQL_FIND_ALL_PAYMENTS_ORDERED_BY_PRICE_WITHIN_RANGE);
        FIND_ALL_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_ALL_ORDER_BY_DESTINATION_ADDRESS, SQL_FIND_ALL_PAYMENTS_ORDERED_BY_DESTINATION_ADDRESS_WITHIN_RANGE);
        FIND_ALL_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_ALL_ORDER_BY_TIME, SQL_FIND_ALL_PAYMENTS_ORDERED_BY_TIME_WITHIN_RANGE);
        FIND_ALL_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_ALL_ORDER_BY_COMMITTED, SQL_FIND_ALL_PAYMENTS_ORDERED_BY_COMMITTED_WITHIN_RANGE);

        FIND_BY_USER_ID_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_BY_USER_ID_ORDER_BY_NAME, SQL_FIND_PAYMENTS_BY_USER_ID_ORDER_BY_NAME_WITHIN_RANGE);
        FIND_BY_USER_ID_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_BY_USER_ID_ORDER_BY_PRICE, SQL_FIND_PAYMENTS_BY_USER_ID_ORDER_BY_PRICE_WITHIN_RANGE);
        FIND_BY_USER_ID_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_BY_USER_ID_ORDER_BY_DESTINATION_ADDRESS, SQL_FIND_PAYMENTS_BY_USER_ID_ORDER_BY_DESTINATION_WITHIN_RANGE);
        FIND_BY_USER_ID_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_BY_USER_ID_ORDER_BY_TIME, SQL_FIND_PAYMENTS_BY_USER_ID_ORDER_BY_TIME_WITHIN_RANGE);
        FIND_BY_USER_ID_ORDER_BY_KEY_TO_SQL_STRING.put(KEY_FIND_BY_USER_ID_ORDER_BY_COMMITTED, SQL_FIND_PAYMENTS_BY_USER_ID_ORDER_BY_COMMITTED_WITHIN_RANGE);
    }

    @Override
    public Payment save(Payment entity) {
        logger.info("save method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return savePayment(connection, entity);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
            return null;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean update(Payment entity) {
        logger.info("update method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return updatePaymentById(connection, entity);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(Payment entity) {
        logger.info("delete method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return deletePaymentById(connection, entity.getId());
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Payment> findAll() {
        logger.info("find all method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<Payment> payments = new ArrayList<>();
        try {
            payments = findAllPayments(connection);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return payments;
    }

    @Override
    public Payment findById(Integer id) {
        logger.info("find by id method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        Payment payment = null;
        try {
            payment = findPaymentById(connection, id);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return payment;
    }

    public List<Payment> findByUserId(Integer id) {
        logger.info("find by user id method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<Payment> payments = new ArrayList<>();
        try {
            payments = findPaymentsByUserId(connection, id);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return payments;
    }

    @Override
    public Integer getRowsNumber() {
        logger.info("get row number method " + PaymentDao.class);
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

    public List<Payment> findByUserIdWithinRange(Integer id, Integer limit, Integer offset) {
        logger.info("find by user id within range method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<Payment> payments = new ArrayList<>();
        try {
            payments = findPaymentsByUserIdWithinRange(connection, id, limit, offset);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return payments;
    }

    public List<Payment> findByUserIdOrderedByNameWithinRange(Integer id, Integer limit, Integer offset) {
        logger.info("find by user id ordered by name within range method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<Payment> payments = new ArrayList<>();
        try {
            payments = findPaymentsByUserIdOrderedByWithinRange(connection, id, limit, offset, KEY_FIND_BY_USER_ID_ORDER_BY_NAME);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return payments;
    }

    public List<Payment> findByUserIdOrderedByPriceWithinRange(Integer id, Integer limit, Integer offset) {
        logger.info("find by user id ordered by price within range method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<Payment> payments = new ArrayList<>();
        try {
            payments = findPaymentsByUserIdOrderedByWithinRange(connection, id, limit, offset, KEY_FIND_BY_USER_ID_ORDER_BY_PRICE);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return payments;
    }

    public List<Payment> findByUserIdOrderedByDestinationWithinRange(Integer id, Integer limit, Integer offset) {
        logger.info("find by user id ordered by destination address within range method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<Payment> payments = new ArrayList<>();
        try {
            payments = findPaymentsByUserIdOrderedByWithinRange(connection, id, limit, offset, KEY_FIND_BY_USER_ID_ORDER_BY_DESTINATION_ADDRESS);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return payments;
    }

    public List<Payment> findByUserIdOrderedByTimeWithinRange(Integer id, Integer limit, Integer offset) {
        logger.info("find by user id ordered by time within range method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<Payment> payments = new ArrayList<>();
        try {
            payments = findPaymentsByUserIdOrderedByWithinRange(connection, id, limit, offset, KEY_FIND_BY_USER_ID_ORDER_BY_TIME);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return payments;
    }

    public List<Payment> findByUserIdOrderedByCommittedWithinRange(Integer id, Integer limit, Integer offset) {
        logger.info("find by user id ordered by committed within range method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<Payment> payments = new ArrayList<>();
        try {
            payments = findPaymentsByUserIdOrderedByWithinRange(connection, id, limit, offset, KEY_FIND_BY_USER_ID_ORDER_BY_COMMITTED);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return payments;
    }

    public Integer getRowsNumberWithUserId(Integer id) {
        logger.info("get row number with user id method " + PaymentDao.class);
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

    public List<Payment> findAllOrderedByIdWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by user id within range method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<Payment> payments = new ArrayList<>();
        try {
            payments = findAllPaymentsOrderedByWithinRange(connection, limit, offset, KEY_FIND_ALL_ORDER_BY_ID);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return payments;
    }

    public List<Payment> findAllOrderedByUserIdWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by user id within range method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<Payment> payments = new ArrayList<>();
        try {
            payments = findAllPaymentsOrderedByWithinRange(connection, limit, offset, KEY_FIND_ALL_ORDER_BY_USER_ID);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return payments;
    }

    public List<Payment> findAllOrderedByNameWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by name within range method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<Payment> payments = new ArrayList<>();
        try {
            payments = findAllPaymentsOrderedByWithinRange(connection, limit, offset, KEY_FIND_ALL_ORDER_BY_NAME);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return payments;
    }

    public List<Payment> findAllOrderedByPriceWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by price within range method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<Payment> payments = new ArrayList<>();
        try {
            payments = findAllPaymentsOrderedByWithinRange(connection, limit, offset, KEY_FIND_ALL_ORDER_BY_PRICE);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return payments;
    }

    public List<Payment> findAllOrderedByAddressWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by destination address within range method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<Payment> payments = new ArrayList<>();
        try {
            payments = findAllPaymentsOrderedByWithinRange(connection, limit, offset, KEY_FIND_ALL_ORDER_BY_DESTINATION_ADDRESS);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return payments;
    }

    public List<Payment> findAllOrderedByTimeWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by expire time within range method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<Payment> payments = new ArrayList<>();
        try {
            payments = findAllPaymentsOrderedByWithinRange(connection, limit, offset, KEY_FIND_ALL_ORDER_BY_TIME);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return payments;
    }

    public List<Payment> findAllOrderedByCommittedWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by committed within range method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<Payment> payments = new ArrayList<>();
        try {
            payments = findAllPaymentsOrderedByWithinRange(connection, limit, offset, KEY_FIND_ALL_ORDER_BY_COMMITTED);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return payments;
    }

    private Payment savePayment(Connection connection, Payment payment) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_PAYMENT, new String[] {"id"});
        preparedStatement.setInt(1, payment.getUserId());
        preparedStatement.setString(2, payment.getDestinationAddress());
        preparedStatement.setLong(3, payment.getPrice());
        preparedStatement.setBoolean(4, payment.isCommitted());
        if (!Objects.isNull(payment.getTime())){
            preparedStatement.setString(5, payment.getTime().toInstant().toString());
        } else {
            preparedStatement.setString(5, null);
        }
        preparedStatement.setString(6, payment.getName());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        logger.info(resultSet);
        if (resultSet.next()){

        }
        Integer id = resultSet.getInt(1);
        payment.setId(id);
        preparedStatement.close();
        resultSet.close();
        return payment;
    }

    private List<Payment> findAllPayments(Connection connection) throws SQLException{
        List<Payment> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_PAYMENTS);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            result.add(convertResultToPayment(resultSet));
        }
        preparedStatement.close();
        resultSet.close();
        return result;
    }

    private List<Payment> findPaymentsByUserId(Connection connection, Integer userId) throws SQLException{
        List<Payment> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PAYMENTS_BY_USER_ID);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            result.add(convertResultToPayment(resultSet));
        }
        preparedStatement.close();
        resultSet.close();
        return result;
    }

    private List<Payment> findPaymentsByUserIdWithinRange(Connection connection, Integer userId, Integer limit, Integer offset) throws SQLException{
        List<Payment> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PAYMENTS_BY_USER_ID_WITHIN_RANGE);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, limit);
        preparedStatement.setInt(3, offset);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            result.add(convertResultToPayment(resultSet));
        }
        preparedStatement.close();
        resultSet.close();
        return result;
    }

    private List<Payment> findPaymentsByUserIdOrderedByWithinRange(Connection connection, Integer userId, Integer limit, Integer offset, Integer orderBy) throws SQLException{
        List<Payment> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USER_ID_ORDER_BY_KEY_TO_SQL_STRING.get(orderBy));
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, limit);
        preparedStatement.setInt(3, offset);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            result.add(convertResultToPayment(resultSet));
        }
        preparedStatement.close();
        resultSet.close();
        return result;
    }

    private List<Payment> findAllPaymentsOrderedByWithinRange(Connection connection, Integer limit, Integer offset, Integer orderBy) throws SQLException{
        List<Payment> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ORDER_BY_KEY_TO_SQL_STRING.get(orderBy));
        preparedStatement.setInt(1, limit);
        preparedStatement.setInt(2, offset);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            result.add(convertResultToPayment(resultSet));
        }
        preparedStatement.close();
        resultSet.close();
        return result;
    }

    private Payment findPaymentById(Connection connection, Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PAYMENT_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Payment payment;
        if (resultSet.next()){
            payment = convertResultToPayment(resultSet);
        } else {
            payment = null;
        }
        preparedStatement.close();
        resultSet.close();
        return payment;
    }

    private Boolean updatePaymentById(Connection connection, Payment payment) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PAYMENT_BY_ID);
        preparedStatement.setInt(1, payment.getUserId());
        preparedStatement.setString(2, payment.getDestinationAddress());
        preparedStatement.setLong(3, payment.getPrice());
        preparedStatement.setBoolean(4, payment.isCommitted());
        if (!Objects.isNull(payment.getTime())){
            preparedStatement.setString(5, payment.getTime().toInstant().toString());
        } else {
            preparedStatement.setString(5, null);
        }
        preparedStatement.setString(6, payment.getName());
        preparedStatement.setInt(7, payment.getId());
        Boolean result = Objects.equals(preparedStatement.executeUpdate(), 1);
        preparedStatement.close();
        return result;
    }

    private Boolean deletePaymentById(Connection connection, Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_PAYMENT_BY_ID);
        preparedStatement.setInt(1, id);
        Boolean result = Objects.equals(preparedStatement.executeUpdate(), 1);
        preparedStatement.close();
        return result;
    }

    private Integer getRowNumber(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_COUNT_PAYMENTS);
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
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_COUNT_PAYMENTS_WITH_USER_ID);
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

    private Payment convertResultToPayment(ResultSet resultSet) throws SQLException{
        Payment payment;
        try {
            payment = new Payment(resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getString(3),
                    resultSet.getLong(4),
                    resultSet.getBoolean(5),
                    Date.from(Instant.parse(resultSet.getString(6))),
                    resultSet.getString(7));
        } catch (DateTimeParseException | NullPointerException e) {
            payment = new Payment(resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getString(3),
                    resultSet.getLong(4),
                    resultSet.getBoolean(5),
                    null,
                    resultSet.getString(7));
            //todo implement logger and custom exception
            logger.warn(e);
        }
        return payment;
    }
}
