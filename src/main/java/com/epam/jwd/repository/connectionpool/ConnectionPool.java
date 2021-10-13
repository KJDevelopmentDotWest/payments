package com.epam.jwd.repository.connectionpool;

import java.sql.Connection;
import java.util.concurrent.BlockingDeque;

public interface ConnectionPool {

    Connection takeConnection();
    void returnConnection(Connection connection);
    void shutdown();
    void initialize();

}
