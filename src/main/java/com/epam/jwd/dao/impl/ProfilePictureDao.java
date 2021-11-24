package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.connectionpool.api.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.model.profilepicture.ProfilePicture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfilePictureDao implements Dao<ProfilePicture, Integer> {

    private static final Logger logger = LogManager.getLogger(ProfilePictureDao.class);

    private static final String SQL_SAVE_PROFILE_PICTURE = "INSERT_INTO profile_pictures (name, path) VALUES (?, ?)";

    private static final String SQL_FIND_ALL_PROFILE_PICTURES = "SELECT id, name, path FROM profile_pictures";
    private static final String SQL_FIND_PROFILE_PICTURE_BY_ID = "SELECT id, name, path FROM profile_pictures WHERE id = ?";

    private static final String SQL_UPDATE_PROFILE_PICTURE_BY_ID = "UPDATE profile_pictures SET name = ?, path = ? WHERE id = ?";

    private static final String SQL_DELETE_PROFILE_PICTURE_BY_ID = "DELETE FROM profile_pictures WHERE id = ?";

    private static final String SQL_COUNT_USERS = "SELECT COUNT(id) as pictures_number FROM profile_pictures";


    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public ProfilePicture save(ProfilePicture entity) {
        logger.info("save method " + ProfilePictureDao.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return saveProfilePicture(connection, entity);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
            return null;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean update(ProfilePicture entity) {
        logger.info("update method " + ProfilePictureDao.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return updateProfilePictureById(connection, entity);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(ProfilePicture entity) {
        logger.info("delete method " + ProfilePictureDao.class);
        Connection connection = connectionPool.takeConnection();
        try {
            return deleteProfilePictureById(connection, entity.getId());
        } catch (SQLException e) {
            //todo implement logger and custom exception
            logger.error(e);
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<ProfilePicture> findAll() {
        logger.info("find all method " + ProfilePictureDao.class);
        Connection connection = connectionPool.takeConnection();
        List<ProfilePicture> profilePictures = new ArrayList<>();
        try {
            profilePictures = findAllProfilePictures(connection);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return profilePictures;
    }

    @Override
    public ProfilePicture findById(Integer id) {
        logger.info("find by id method " + ProfilePictureDao.class);
        Connection connection = connectionPool.takeConnection();
        ProfilePicture profilePicture = null;
        try {
            profilePicture = findProfilePictureById(connection, id);
        } catch (SQLException e){
            //todo implement logger and custom exception
            logger.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return profilePicture;
    }

    @Override
    public Integer getRowsNumber() {
        logger.info("get row number method " + ProfilePictureDao.class);
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

    private ProfilePicture saveProfilePicture(Connection connection, ProfilePicture profilePicture) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_PROFILE_PICTURE, new String[] {"id"});
        preparedStatement.setString(1, profilePicture.getName());
        preparedStatement.setString(2, profilePicture.getPath());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        Integer id = resultSet.getInt(1);
        profilePicture.setId(id);
        preparedStatement.close();
        resultSet.close();
        return profilePicture;
    }

    private List<ProfilePicture> findAllProfilePictures(Connection connection) throws SQLException {
        List<ProfilePicture> result = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_PROFILE_PICTURES);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            result.add(convertResultSetToProfilePicture(resultSet));
        }
        preparedStatement.close();
        resultSet.close();
        return result;
    }

    private ProfilePicture findProfilePictureById(Connection connection, Integer id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PROFILE_PICTURE_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        ProfilePicture result = null;
        if (resultSet.next()){
            result = convertResultSetToProfilePicture(resultSet);
        }
        preparedStatement.close();
        resultSet.close();
        return result;
    }

    private Boolean updateProfilePictureById(Connection connection, ProfilePicture profilePicture) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PROFILE_PICTURE_BY_ID);
        preparedStatement.setString(1, profilePicture.getName());
        preparedStatement.setString(2, profilePicture.getPath());
        preparedStatement.setInt(3, profilePicture.getId());
        Boolean result = Objects.equals(preparedStatement.executeUpdate(), 1);
        preparedStatement.close();
        return result;
    }

    private Boolean deleteProfilePictureById(Connection connection, Integer id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_PROFILE_PICTURE_BY_ID);
        preparedStatement.setInt(1, id);
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

    private ProfilePicture convertResultSetToProfilePicture(ResultSet resultSet) throws SQLException {
        return new ProfilePicture(resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3));
    }
}
