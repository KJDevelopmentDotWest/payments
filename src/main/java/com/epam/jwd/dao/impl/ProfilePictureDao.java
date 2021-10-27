package com.epam.jwd.dao.impl;

import com.epam.jwd.dao.api.Dao;
import com.epam.jwd.dao.connectionpool.api.ConnectionPool;
import com.epam.jwd.dao.connectionpool.impl.ConnectionPoolImpl;
import com.epam.jwd.dao.model.payment.Payment;
import com.epam.jwd.dao.model.profilepicture.ProfilePicture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfilePictureDao implements Dao<ProfilePicture, Integer> {

    private static final String SQL_SAVE_PROFILE_PICTURE = "INSERT_INTO profile_pictures (name, path) VALUES (?, ?)";

    private static final String SQL_FIND_ALL_PROFILE_PICTURES = "SELECT id, name, path FROM profile_pictures";
    private static final String SQL_FIND_PROFILE_PICTURE_BY_ID = "SELECT id, name, path FROM profile_pictures WHERE id = ?";

    private static final String SQL_UPDATE_PROFILE_PICTURE_BY_ID = "UPDATE profile_pictures SET name = ?, path = ? WHERE id = ?";

    private static final String SQL_DELETE_PROFILE_PICTURE_BY_ID = "DELETE FROM profile_pictures WHERE id = ?";

    private final ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();

    @Override
    public ProfilePicture save(ProfilePicture entity) {
        Connection connection = connectionPool.takeConnection();
        try {
            return saveProfilePicture(connection, entity);
        } catch (SQLException e) {
            //todo implement logger and custom exception
            e.printStackTrace();
            return null;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean update(ProfilePicture entity) {
        Connection connection = connectionPool.takeConnection();
        try {
            return updateProfilePictureById(connection, entity);
        } catch (SQLException throwables) {
            //todo implement logger and custom exception
            throwables.printStackTrace();
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Boolean delete(ProfilePicture entity) {
        Connection connection = connectionPool.takeConnection();
        try {
            return deleteProfilePictureById(connection, entity.getId());
        } catch (SQLException throwables) {
            //todo implement logger and custom exception
            throwables.printStackTrace();
            return false;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<ProfilePicture> findAll() {
        Connection connection = connectionPool.takeConnection();
        List<ProfilePicture> profilePictures = new ArrayList<>();
        try {
            profilePictures = findAllProfilePictures(connection);
        } catch (SQLException e){
            //todo implement logger and custom exception
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return profilePictures;
    }

    @Override
    public ProfilePicture findById(Integer id) {
        Connection connection = connectionPool.takeConnection();
        ProfilePicture profilePicture = null;
        try {
            profilePicture = findProfilePictureById(connection, id);
        } catch (SQLException e){
            //todo implement logger and custom exception
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return profilePicture;
    }

    private ProfilePicture saveProfilePicture(Connection connection, ProfilePicture profilePicture) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_PROFILE_PICTURE);
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
            result.add(new ProfilePicture(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3)));
        }
        resultSet.close();
        preparedStatement.close();
        return result;
    }

    private ProfilePicture findProfilePictureById(Connection connection, Integer id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PROFILE_PICTURE_BY_ID);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        ProfilePicture result = null;
        if (resultSet.next()){
            result = new ProfilePicture(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3));
        }
        resultSet.close();
        preparedStatement.close();
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
}
