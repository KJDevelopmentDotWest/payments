package com.epam.jwd.dao.connectionpool.api;

import java.sql.Connection;

public interface ConnectionPool {

    Connection takeConnection();
    void returnConnection(Connection connection);
    void shutdown();
    void initialize();

}
