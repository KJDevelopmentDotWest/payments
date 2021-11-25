package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.connectionpool.api.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.model.payment.Payment;
import com.epam.jwd.dao.model.user.Role;
import com.epam.jwd.dao.model.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserDao implements Dao<User, java.lang.Integer> {

    private static final Logger logger = LogManager.getLogger(UserDao.class);

    private static final String SQL_SAVE_USER = "INSERT INTO users (login, password, account_id, is_active, role_id) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_FIND_ALL_USERS = "SELECT id, login, password, account_id, is_active, role_id FROM users";
    private static final String SQL_FIND_USER_BY_ID = "SELECT id, login, password, account_id, is_active, role_id FROM users WHERE id = ?";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT id, login, password, account_id, is_active, role_id FROM users WHERE login = ?";

    private static final String SQL_FIND_ALL_USERS_ORDERED_BY_ID = "SELECT id, login, password, account_id, is_active, role_id FROM users ORDER BY id LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_USERS_ORDERED_BY_LOGIN = "SELECT id, login, password, account_id, is_active, role_id FROM users ORDER BY login LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_USERS_ORDERED_BY_ROLE = "SELECT id, login, password, account_id, is_active, role_id FROM users ORDER BY role_id LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_USERS_ORDERED_BY_ACTIVE = "SELECT id, login, password, account_id, is_active, role_id FROM users ORDER BY is_active LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_USERS_ORDERED_BY_ACCOUNT_NAME = "SELECT id, login, password, account_id, is_active, role_id " +
            "FROM users LEFT JOIN accounts ON account_id = accounts.id ORDER BY name ON users.id = user_id LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_USERS_ORDERED_BY_ACCOUNT_SURNAME = "SELECT id, login, password, account_id, is_active, role_id " +
            "FROM users LEFT JOIN accounts ON account_id = accounts.id ORDER BY surname LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_USERS_ORDERED_BY_ACCOUNT_PROFILE_PICTURE_ID = "SELECT id, login, password, account_id, is_active, role_id " +
            "FROM users LEFT JOIN accounts ON account_id = accounts.id ORDER BY profile_picture_id LIMIT ? OFFSET ?";

    private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";

    private static final String SQL_UPDATE_USER_BY_ID = "UPDATE users SET login = ?, password = ?, account_id = ?, is_active = ?, role_id = ? WHERE id = ?";

    private static final String SQL_COUNT_USERS = "SELECT COUNT(id) as users_number FROM users";

    private static final Integer KEY_ORDER_BY_ID = 1;
    private static final Integer KEY_ORDER_BY_LOGIN = 2;
    private static final Integer KEY_ORDER_BY_ROLE = 3;
    private static final Integer KEY_ORDER_BY_ACTIVE = 4;
    private static final Integer KEY_ORDER_BY_ACCOUNT_NAME = 5;
    private static final Integer KEY_ORDER_BY_ACCOUNT_SURNAME = 6;
    private static final Integer KEY_ORDER_BY_ACCOUNT_PROFILE_PICTURE_ID = 7;

    private static final Map<Integer, String> ORDER_BY_KEY_TO_SQL_STRING = new HashMap<>();

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    static {
        ORDER_BY_KEY_TO_SQL_STRING.put(KEY_ORDER_BY_ID, SQL_FIND_ALL_USERS_ORDERED_BY_ID);
        ORDER_BY_KEY_TO_SQL_STRING.put(KEY_ORDER_BY_LOGIN, SQL_FIND_ALL_USERS_ORDERED_BY_LOGIN);
        ORDER_BY_KEY_TO_SQL_STRING.put(KEY_ORDER_BY_ROLE, SQL_FIND_ALL_USERS_ORDERED_BY_ROLE);
        ORDER_BY_KEY_TO_SQL_STRING.put(KEY_ORDER_BY_ACTIVE, SQL_FIND_ALL_USERS_ORDERED_BY_ACTIVE);
        ORDER_BY_KEY_TO_SQL_STRING.put(KEY_ORDER_BY_ACCOUNT_NAME, SQL_FIND_ALL_USERS_ORDERED_BY_ACCOUNT_NAME);
        ORDER_BY_KEY_TO_SQL_STRING.put(KEY_ORDER_BY_ACCOUNT_SURNAME, SQL_FIND_ALL_USERS_ORDERED_BY_ACCOUNT_SURNAME);
        ORDER_BY_KEY_TO_SQL_STRING.put(KEY_ORDER_BY_ACCOUNT_PROFILE_PICTURE_ID, SQL_FIND_ALL_USERS_ORDERED_BY_ACCOUNT_PROFILE_PICTURE_ID);
    }

    @Override
    public User save(User entity) {
        logger.info("save method " + UserDao.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return saveUser(connection, entity);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
            return null;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //updates user with id = entity.getId()
    //all data contained in row will be replaced by new
    @Override
    public Boolean update(User entity) {
        logger.info("update method " + UserDao.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return updateUserById(connection, entity);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    //deletes user with id = entity.getId()
    @Override
    public Boolean delete(User entity) {
        logger.info("delete method " + UserDao.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return deleteUserById(connection, entity.getId());
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<User> findAll() {
        logger.info("find all method " + UserDao.class);
        Connection connection = connectionPool.takeConnection();
        List<User> users = new ArrayList<>();
        try {
            users = findAllUsers(connection);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return users;
    }

    @Override
    public User findById(Integer id) {
        logger.info("find by id method " + UserDao.class);
        Connection connection = connectionPool.takeConnection();
        User user = null;
        try {
            user = findUserById(connection, id);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    @Override
    public Integer getRowsNumber() {
        logger.info("get row number method " + UserDao.class);
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

    public User findByLogin(String login) {
        logger.info("find by login method " + UserDao.class);
        Connection connection = connectionPool.takeConnection();
        User user = null;
        try {
            user = findUserByLogin(connection, login);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    public List<User> findAllOrderedByIdWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by id within range method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<User> users = new ArrayList<>();
        try {
            users = findAllUsersOrderedByWithinRange(connection, limit, offset, KEY_ORDER_BY_ID);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return users;
    }

    public List<User> findAllOrderedByLoginWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by login within range method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<User> users = new ArrayList<>();
        try {
            users = findAllUsersOrderedByWithinRange(connection, limit, offset, KEY_ORDER_BY_LOGIN);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return users;
    }

    public List<User> findAllOrderedByRoleWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by role within range method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<User> users = new ArrayList<>();
        try {
            users = findAllUsersOrderedByWithinRange(connection, limit, offset, KEY_ORDER_BY_ROLE);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return users;
    }

    public List<User> findAllOrderedByActiveWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by active within range method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<User> users = new ArrayList<>();
        try {
            users = findAllUsersOrderedByWithinRange(connection, limit, offset, KEY_ORDER_BY_ACTIVE);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return users;
    }

    public List<User> findAllOrderedByAccountNameWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by account name within range method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<User> users = new ArrayList<>();
        try {
            users = findAllUsersOrderedByWithinRange(connection, limit, offset, KEY_ORDER_BY_ACCOUNT_NAME);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return users;
    }

    public List<User> findAllOrderedByAccountSurnameWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by account surname within range method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<User> users = new ArrayList<>();
        try {
            users = findAllUsersOrderedByWithinRange(connection, limit, offset, KEY_ORDER_BY_ACCOUNT_SURNAME);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return users;
    }

    public List<User> findAllOrderedByAccountProfilePictureIdWithinRange(Integer limit, Integer offset){
        logger.info("find all ordered by account profile picture id within range method " + PaymentDao.class);
        Connection connection = connectionPool.takeConnection();
        List<User> users = new ArrayList<>();
        try {
            users = findAllUsersOrderedByWithinRange(connection, limit, offset, KEY_ORDER_BY_ACCOUNT_PROFILE_PICTURE_ID);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return users;
    }



    private User saveUser(Connection connection, User user) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER, new String[] {"id"});
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setObject(3, user.getAccountId());
        preparedStatement.setBoolean(4, user.getActive());
        preparedStatement.setInt(5, user.getRole().getId());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        Integer id = resultSet.getInt(1);
        user.setId(id);
        preparedStatement.close();
        resultSet.close();
        return user;
    }

    private List<User> findAllUsers(Connection connection) throws SQLException{
        List<User> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_USERS);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            result.add(convertResultSetToUser(resultSet));
        }
        preparedStatement.close();
        resultSet.close();
        return result;
    }

    private List<User> findAllUsersOrderedByWithinRange(Connection connection, Integer limit, Integer offset, Integer orderBy) throws SQLException{
        List<User> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(ORDER_BY_KEY_TO_SQL_STRING.get(orderBy));
        preparedStatement.setInt(1, limit);
        preparedStatement.setInt(2, offset);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            result.add(convertResultSetToUser(resultSet));
        }
        preparedStatement.close();
        resultSet.close();
        return result;
    }

    private User findUserByLogin(Connection connection, String login) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN);
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();
        User user;
        if (resultSet.next()){
            user = convertResultSetToUser(resultSet);
        } else {
            user = null;
        }
        preparedStatement.close();
        resultSet.close();
        return user;
    }

    private User findUserById(Connection connection, java.lang.Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        User user;
        if (resultSet.next()){
            user = convertResultSetToUser(resultSet);
        } else {
            user = null;
        }
        preparedStatement.close();
        resultSet.close();
        return user;
    }

    private Boolean deleteUserById(Connection connection, java.lang.Integer id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER_BY_ID);
        preparedStatement.setInt(1, id);
        Boolean result = Objects.equals(preparedStatement.executeUpdate(), 1);
        preparedStatement.close();
        return result;
    }

    private Boolean updateUserById(Connection connection, User user) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_BY_ID);
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setInt(3, user.getAccountId());
        preparedStatement.setBoolean(4, user.getActive());
        preparedStatement.setInt(5, user.getRole().getId());
        preparedStatement.setInt(6, user.getId());
        Boolean result = Objects.equals(preparedStatement.executeUpdate(), 1);
        preparedStatement.close();
        return result;
    }

    private Integer getRowNumber(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_COUNT_USERS);
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

    private User convertResultSetToUser(ResultSet resultSet) throws SQLException{
        return new User(resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getInt(4) != 0
                        ? resultSet.getInt(4)
                        : null,
                resultSet.getBoolean(5),
                Role.getById(resultSet.getInt(6)));
    }
}
