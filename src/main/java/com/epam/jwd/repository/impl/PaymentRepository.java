package com.epam.jwd.repository.impl;

import com.epam.jwd.repository.api.Repository;
import com.epam.jwd.repository.connectionpool.ConnectionPool;
import com.epam.jwd.repository.connectionpool.ConnectionPoolImpl;
import com.epam.jwd.repository.model.payment.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PaymentRepository implements Repository<Payment, Integer> {

    private static final String SQL_SAVE_PAYMENT = "INSERT INTO payments (account_id, destination_address, price, committed, time, name) VALUES (?, ?, ?, ?, ?, ?)";

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public Boolean save(Payment entity) {
        Connection connection = connectionPool.takeConnection();
        try {
            savePayment(entity, connection);
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
        return null;
    }

    @Override
    public Boolean delete(Payment entity) {
        return null;
    }

    @Override
    public List<Payment> findAll() {
        return null;
    }

    @Override
    public Payment findById(Integer id) {
        return null;
    }

    private void savePayment(Payment payment, Connection connection) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_PAYMENT);
        preparedStatement.setInt(1, payment.getAccountId());
        preparedStatement.setString(2, payment.getDestinationAddress());
        preparedStatement.setInt(3, payment.getPrice());
        preparedStatement.setBoolean(4, payment.isCommitted());
        preparedStatement.setString(5, payment.getTime().toString());
        preparedStatement.setString(6, payment.getName());
        preparedStatement.executeUpdate();
    }
}
