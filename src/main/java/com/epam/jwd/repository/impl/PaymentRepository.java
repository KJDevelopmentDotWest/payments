package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.api.Repository;
import com.epam.jwd.repository.connectionpool.ConnectionPool;
import com.epam.jwd.repository.connectionpool.ConnectionPoolImpl;
import com.epam.jwd.repository.model.payment.Payment;

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

public class PaymentRepository implements Repository<Payment, Integer> {

    private static final String SQL_SAVE_PAYMENT = "INSERT INTO payments (account_id, destination_address, price, committed, time, name) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_FIND_ALL_PAYMENT = "select id, account_id, destination_address, price, committed, time, name FROM payments";
    private static final String SQL_FIND_PAYMENT_BY_ID = "select id, account_id, destination_address, price, committed, time, name FROM payments WHERE id = ?";

    private static final String SQL_UPDATE_PAYMENT_BY_ID = "UPDATE payments SET account_id = ?, destination_address = ?, price = ?, committed = ?, time = ?, name = ? WHERE id = ?";

    private static final String SQL_DELETE_PAYMENT_BY_ID = "DELETE FROM payments WHERE id = ?";

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public Boolean save(Payment entity) {
        Connection connection = connectionPool.takeConnection();
        try {
            savePayment(connection, entity);
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
    public Boolean update(Payment entity) {
        Connection connection = connectionPool.takeConnection();
        try {
            return updatePaymentById(connection, entity);
        } catch (SQLException throwables) {
            //todo implement logger and custom exception
            throwables.printStackTrace();
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(Payment entity) {
        Connection connection = connectionPool.takeConnection();
        try {
            return deletePaymentById(connection, entity.getId());
        } catch (SQLException throwables) {
            //todo implement logger and custom exception
            throwables.printStackTrace();
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Payment> findAll() {
        Connection connection = connectionPool.takeConnection();
        List<Payment> payments = new ArrayList<>();
        try {
            payments = findAllPayment(connection);
        } catch (SQLException e){
            //todo implement logger and custom exception
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return payments;
    }

    @Override
    public Payment findById(Integer id) {
        Connection connection = connectionPool.takeConnection();
        Payment payment = null;
        try {
            payment = findPaymentById(connection, id);
        } catch (SQLException e){
            //todo implement logger and custom exception
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return payment;
    }

    private void savePayment(Connection connection, Payment payment) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_PAYMENT);
        preparedStatement.setInt(1, payment.getAccountId());
        preparedStatement.setString(2, payment.getDestinationAddress());
        preparedStatement.setInt(3, payment.getPrice());
        preparedStatement.setBoolean(4, payment.isCommitted());
        preparedStatement.setString(5, payment.getTime().toString());
        preparedStatement.setString(6, payment.getName());
        preparedStatement.executeUpdate();
    }

    private List<Payment> findAllPayment(Connection connection) throws SQLException{
        List<Payment> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_PAYMENT);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            Payment payment;
            try {
                payment = new Payment(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getBoolean(5),
                        new SimpleDateFormat().parse(resultSet.getString(6)),
                        resultSet.getString(7));
            } catch (ParseException e) {
                payment = new Payment(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getBoolean(5),
                        new Date(),
                        resultSet.getString(7));
                //todo implement logger and custom exception
                e.printStackTrace();
            }
            result.add(payment);
        }
        return result;
    }

    private Payment findPaymentById(Connection connection, Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PAYMENT_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            Payment payment;
            try {
                payment = new Payment(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getBoolean(5),
                        new SimpleDateFormat().parse(resultSet.getString(6)),
                        resultSet.getString(7));
            } catch (ParseException e) {
                payment = new Payment(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getBoolean(5),
                        new Date(),
                        resultSet.getString(7));
                //todo implement logger and custom exception
                e.printStackTrace();
            }
            return payment;
        } else {
            return null;
        }
    }

    private Boolean updatePaymentById(Connection connection, Payment payment) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PAYMENT_BY_ID);
        preparedStatement.setInt(1, payment.getAccountId());
        preparedStatement.setString(2, payment.getDestinationAddress());
        preparedStatement.setInt(3, payment.getPrice());
        preparedStatement.setBoolean(4, payment.isCommitted());
        preparedStatement.setString(5, payment.getTime().toString());
        preparedStatement.setString(6, payment.getName());
        preparedStatement.setInt(7, payment.getId());
        return Objects.equals(preparedStatement.executeUpdate(), 1);
    }

    private Boolean deletePaymentById(Connection connection, Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_PAYMENT_BY_ID);
        preparedStatement.setInt(1, id);
        return Objects.equals(preparedStatement.executeUpdate(), 1);
    }
}
