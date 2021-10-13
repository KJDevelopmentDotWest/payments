package com.epam.jwd.repository.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Deque;
import java.util.Objects;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionPoolImpl implements ConnectionPool{

    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/test";
    private static final String USER = "postgres";
    private static final String PASS = "1234";
    private static final int MAX_CONNECTIONS = 8;
    private static final int PREFERRED_CONNECTIONS = 4;

    private boolean initialized = false;

    private final BlockingDeque<ProxyConnection> availableConnections = new LinkedBlockingDeque<>();
    private final BlockingDeque<ProxyConnection> givenAwayConnections = new LinkedBlockingDeque<>();

    private static ConnectionPoolImpl instance = null;

    private ConnectionPoolImpl(){
        instance.initialize();
    }

    public static ConnectionPoolImpl getInstance(){
        if(Objects.isNull(instance)){
            instance = new ConnectionPoolImpl();
        }
        return instance;
    }


    @Override
    public synchronized Connection takeConnection() {

        if (!availableConnections.isEmpty()){
            return availableConnections.poll();
        } else if (givenAwayConnections.size() < MAX_CONNECTIONS){
            try {
                createConnectionAndAddToPool();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return availableConnections.poll();
        }

        while (availableConnections.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                //todo implement logger and custom exception
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        return availableConnections.poll();
    }

    @Override
    public synchronized void returnConnection(Connection connection) {
        if (givenAwayConnections.remove((ProxyConnection) connection)){
            if (availableConnections.size() + givenAwayConnections.size() < PREFERRED_CONNECTIONS || availableConnections.size() == 0){
                availableConnections.add((ProxyConnection) connection);
                notifyAll();
            } else {
                closeConnection((ProxyConnection) connection);
            }
        }
    }

    @Override
    public void shutdown() {
        closeConnection(availableConnections);
        closeConnection(givenAwayConnections);
        availableConnections.clear();
        givenAwayConnections.clear();
        initialized = false;
    }

    @Override
    public void initialize() {
        if (!initialized){
            try{
                for (int i = 0; i < PREFERRED_CONNECTIONS; i++) {
                    createConnectionAndAddToPool();
                }
                initialized = true;
            } catch (SQLException throwables) {
                //todo implement logger and custom exception
                throwables.printStackTrace();
            }
        }
    }

    private void createConnectionAndAddToPool() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        ProxyConnection proxyConnection = new ProxyConnection(connection, this);
        availableConnections.add(proxyConnection);
    }

    private void closeConnection(Deque<ProxyConnection> connections){
        connections.forEach(this::closeConnection);
    }

    private void closeConnection(ProxyConnection connection){
        try {
            connection.realClose();
        } catch (SQLException e) {
            //todo implement logger and custom exception
            e.printStackTrace();
        }
    }
}
