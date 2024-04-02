package org.aston.task.repository.impl;

import org.aston.task.db.ConnectionManager;
import org.aston.task.db.ConnectionManagerImpl;
import org.aston.task.exceptions.NotFoundException;
import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.repository.UserEntityRepository;
import org.aston.task.repository.mapper.UserResultSetMapper;
import org.aston.task.repository.mapper.impl.UserResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserEntityRepositoryImpl implements UserEntityRepository {

    private final ConnectionManager connectionManager;

    private final UserResultSetMapper userResultSetMapper;


    public UserEntityRepositoryImpl() {
        userResultSetMapper = new UserResultSetMapperImpl();
        connectionManager = new ConnectionManagerImpl();
    }

    @Override
    public UserEntity findById(UUID id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users " +
                     "WHERE user_id = (?)")) {
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            UserEntity user = new UserEntity();
            if (resultSet.next()) {
                user = userResultSetMapper.map(resultSet);
            }
            return user;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public boolean deleteById(UUID id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users " +
                     "WHERE user_id = (?)")) {
            preparedStatement.setString(1, id.toString());
            return preparedStatement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public List<UserEntity> findAll() {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<UserEntity> userEntities = new ArrayList<>();
            while (resultSet.next()) {
                userEntities.add(userResultSetMapper.map(resultSet));
            }
            return userEntities;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    @Override
    public UserEntity save(UserEntity user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users VALUES (?, ?)")) {
            preparedStatement.setString(1, user.getId().toString());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.execute();
            return findById(user.getId());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public UserEntity update(UserEntity user, UUID uuid) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET user_name = ? " +
                     "WHERE user_id = ?")) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getId().toString());
            preparedStatement.execute();
            return findById(uuid);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    @Override
    public void check(UUID id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users" +
                     " WHERE user_id = ?")) {
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                throw new NotFoundException("User with id " + id.toString() + " not found");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
