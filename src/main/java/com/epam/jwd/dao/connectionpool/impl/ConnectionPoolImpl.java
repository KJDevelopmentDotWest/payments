package com.epam.jwd.dao.connectionpool.impl;

import com.epam.jwd.dao.connectionpool.api.ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Deque;
import java.util.Objects;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPoolImpl implements ConnectionPool {

    private static final Logger logger = LogManager.getLogger();

    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/payments";
    private static final String USER = "postgres";
    private static final String PASS = "1234";
    private static final String DRIVER = "org.postgresql.Driver";
    private static final int MAX_CONNECTIONS = 6;
    private static final int PREFERRED_CONNECTIONS = 4;

    private boolean initialized = false;

    private static final ReentrantLock getInstanceLock = new ReentrantLock();

    private final BlockingDeque<ProxyConnection> availableConnections = new LinkedBlockingDeque<>();
    private final BlockingDeque<ProxyConnection> givenAwayConnections = new LinkedBlockingDeque<>();

    private static ConnectionPoolImpl instance;

    private ConnectionPoolImpl(){
    }

    public static ConnectionPool getInstance(){
        getInstanceLock.lock();
        if(Objects.isNull(instance)){
            instance = new ConnectionPoolImpl();
            instance.initialize();
        }
        getInstanceLock.unlock();
        return instance;
    }


    @Override
    public synchronized Connection takeConnection() {

        logger.info("Connection taken");

        if (!availableConnections.isEmpty()){
            ProxyConnection connection = availableConnections.poll();
            givenAwayConnections.add(connection);
            return connection;
        } else if (givenAwayConnections.size() < MAX_CONNECTIONS){
            try {
                createConnectionAndAddToPool();
                ProxyConnection connection = availableConnections.poll();
                givenAwayConnections.add(connection);
                return connection;
            } catch (SQLException e) {
                //todo implement logger and custom exception
                e.printStackTrace();
            }
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
        ProxyConnection connection = availableConnections.poll();
        givenAwayConnections.add(connection);
        return connection;
    }

    @Override
    public synchronized void returnConnection(Connection connection) {
        if (givenAwayConnections.remove((ProxyConnection) connection)){
            logger.info("Connection returned");
            try {
                if (!connection.getAutoCommit()){
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                //todo implement logger and custom exception
                e.printStackTrace();
            }

            if (availableConnections.size() + givenAwayConnections.size() < PREFERRED_CONNECTIONS
                    || (availableConnections.isEmpty() && givenAwayConnections.size() < MAX_CONNECTIONS)){
                availableConnections.add((ProxyConnection) connection);
                logger.info(availableConnections.size() + "lkj");
            } else {
                closeConnection((ProxyConnection) connection);
            }
        }
        notify();
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
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e) {
                //todo implement logger and custom exception
                e.printStackTrace();
            }
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
        logger.info("Connection closed");
        try {
            connection.realClose();
        } catch (SQLException e) {
            //todo implement logger and custom exception
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        shutdown();
        super.finalize();
    }
}
