package org.aston.task.repository.impl;

import org.aston.task.db.ConnectionManager;
import org.aston.task.db.ConnectionManagerImpl;
import org.aston.task.exceptions.NotFoundException;
import org.aston.task.model.RecordLikes;
import org.aston.task.model.UserLikes;
import org.aston.task.repository.LikeRepository;
import org.aston.task.repository.mapper.RecordResultSetMapper;
import org.aston.task.repository.mapper.UserResultSetMapper;
import org.aston.task.repository.mapper.impl.RecordResultSetMapperImpl;
import org.aston.task.repository.mapper.impl.UserResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class LikeRepositoryImpl implements LikeRepository {

    private ConnectionManager connectionManager;

    private UserResultSetMapper userResultSetMapper;

    private RecordResultSetMapper recordResultSetMapper;

    public LikeRepositoryImpl() {
        connectionManager = new ConnectionManagerImpl();
        userResultSetMapper = new UserResultSetMapperImpl();
        recordResultSetMapper = new RecordResultSetMapperImpl();
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void addLike(UUID recordId, UUID userId) {
        try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO likes (user_id, record_id)  " +
                "SELECT u.user_id, r.record_id " +
                "FROM users as u, records r " +
                "WHERE u.user_id = (?) AND " +
                "r.record_id = (?)")) {
            preparedStatement.setString(1, userId.toString());
            preparedStatement.setString(2, recordId.toString());
            preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void removeLike(UUID recordId, UUID userId) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM likes " +
                     "WHERE user_id = ? AND record_id = ?")) {
            preparedStatement.setString(1, userId.toString());
            preparedStatement.setString(2, recordId.toString());
            preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public UserLikes findLikesByUserId(UUID userId) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM records " +
                     "FULL OUTER JOIN likes l on records.record_id = l.record_id " +
                     "WHERE l.user_id = ?")) {

            preparedStatement.setString(1, userId.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<UUID> uuids = new ArrayList<>();
            while (resultSet.next()) {
                uuids.add(UUID.fromString(resultSet.getString("record_id")));
            }
            UserLikes userLikes = new UserLikes();
            userLikes.setUserLikes(uuids);
            return userLikes;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public RecordLikes findLikesByRecordId(UUID recordId) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users " +
                     "FULL OUTER JOIN public.likes l on users.user_id = l.user_id " +
                     "WHERE l.record_id = ?")) {

            preparedStatement.setString(1, recordId.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<UUID> uuids = new ArrayList<>();
            while (resultSet.next()) {
                uuids.add(UUID.fromString(resultSet.getString("user_id")));
            }
            RecordLikes recordLikes = new RecordLikes();
            recordLikes.setRecordLikes(uuids);
            return recordLikes;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void check(UUID recordId, UUID userId) {
        try(Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM likes " +
                    "WHERE record_id = ? AND user_id = ?")) {
            preparedStatement.setString(1, recordId.toString());
            preparedStatement.setString(2, userId.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                throw new NotFoundException("Like not found");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }
}
