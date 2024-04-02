package org.aston.task.repository.impl;

import org.aston.task.db.ConnectionManager;
import org.aston.task.db.ConnectionManagerImpl;
import org.aston.task.exceptions.NotFoundException;
import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.repository.RecordEntityRepository;
import org.aston.task.repository.mapper.RecordResultSetMapper;
import org.aston.task.repository.mapper.impl.RecordResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecordEntityRepositoryImpl implements RecordEntityRepository {

    private final RecordResultSetMapper recordResultSetMapper;
    private final ConnectionManager connectionManager;

    public RecordEntityRepositoryImpl() {
        recordResultSetMapper = new RecordResultSetMapperImpl();
        connectionManager = new ConnectionManagerImpl();
    }

    @Override
    public RecordEntity findById(UUID id) {
        try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM records" +
                " WHERE record_id = (?)")) {
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            RecordEntity record = new RecordEntity();
            if (resultSet.next()) {
                record = recordResultSetMapper.map(resultSet);
            }
            return record;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public boolean deleteById(UUID id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM records " +
                     "WHERE record_id = (?)")) {
            preparedStatement.setString(1, id.toString());
            return preparedStatement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public List<RecordEntity> findAll() {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM records")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<RecordEntity> recordEntities = new ArrayList<>();
            while (resultSet.next()) {
                recordEntities.add(recordResultSetMapper.map(resultSet));
            }
            return recordEntities;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public RecordEntity save(RecordEntity recordEntity) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO records " +
                     "VALUES (?, ?, ?, ?)")) {
            preparedStatement.setString(1, recordEntity.getId().toString());
            preparedStatement.setString(2, recordEntity.getAuthor().getId().toString());
            preparedStatement.setString(3, recordEntity.getTitle());
            preparedStatement.setString(4, recordEntity.getText());
            preparedStatement.execute();
            return findById(recordEntity.getId());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public RecordEntity update(RecordEntity recordEntity, UUID uuid) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE records SET title = ?, text = ? " +
                     "WHERE record_id = ?")) {
            preparedStatement.setString(1, recordEntity.getTitle());
            preparedStatement.setString(2, recordEntity.getText());
            preparedStatement.setString(2, uuid.toString());
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
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM records" +
                     " WHERE record_id = ?")) {
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                throw new NotFoundException("Record with id " + id.toString() + " not found");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public List<RecordEntity> findRecordByAuthorId(UUID authorId) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM records " +
                     "WHERE author_id = (?)")) {
            preparedStatement.setString(1, authorId.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            List<RecordEntity> recordEntities = new ArrayList<>();

            while (resultSet.next()) {
                recordEntities.add(recordResultSetMapper.map(resultSet));
            }

            return recordEntities;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
