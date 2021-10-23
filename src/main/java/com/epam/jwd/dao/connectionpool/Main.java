package com.epam.jwd.dao.connectionpool;

import com.epam.jwd.dao.connectionpool.api.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    private static final String SELECT_STATEMENT = "SELECT id, name FROM users";
    private static final String INSERT_STATEMENT = "insert into users (name) values ('name')";

    public static void main(String[] args) throws SQLException {
        ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STATEMENT);
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement(SELECT_STATEMENT);
        ResultSet set = preparedStatement.executeQuery();

        while (set.next()){
            System.out.println(set.getInt(1));
            System.out.println(set.getString(2));
        }

        connection.close();
        connectionPool.returnConnection(connection);
    }

}
