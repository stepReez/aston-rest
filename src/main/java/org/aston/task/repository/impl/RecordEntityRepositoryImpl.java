package org.aston.task.repository.impl;

import org.aston.task.db.ConnectionManager;
import org.aston.task.db.ConnectionManagerImpl;
import org.aston.task.exceptions.NotFoundException;
import org.aston.task.model.RecordEntity;
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
    private ConnectionManager connectionManager;

    public RecordEntityRepositoryImpl() {
        recordResultSetMapper = new RecordResultSetMapperImpl();
        connectionManager = new ConnectionManagerImpl();
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public RecordEntity findById(UUID id) {
        try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM records AS r " +
                "WHERE r.record_id = (?)")) {
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            RecordEntity record = new RecordEntity();
            if (resultSet.next()) {
                record = recordResultSetMapper.map(resultSet);
            }
            return record;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean deleteById(UUID id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE  FROM records " +
                     "WHERE record_id = (?)")) {
            preparedStatement.setString(1, id.toString());
            return preparedStatement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<RecordEntity> findAll() {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM records AS r")) {
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
            if (recordEntity.getTag() != null) {
                recordEntity.getTag().forEach(x -> addTag(x.getId(), recordEntity.getId()));
            }
            return findById(recordEntity.getId());

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public RecordEntity update(RecordEntity recordEntity, UUID uuid) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE records " +
                     "SET title = ?, text = ? " +
                     "WHERE record_id = ?")) {
            preparedStatement.setString(1, recordEntity.getTitle());
            preparedStatement.setString(2, recordEntity.getText());
            preparedStatement.setString(3, uuid.toString());
            preparedStatement.execute();
            if (recordEntity.getTag() != null) {
                recordEntity.getTag().forEach(x -> addTag(x.getId(), recordEntity.getId()));
            }
            return findById(uuid);
        } catch (SQLException | ClassNotFoundException e) {
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
                throw new NotFoundException("Record with id " + id + " not found");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<RecordEntity> findRecordByAuthorId(UUID authorId) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM records AS r " +
                     "WHERE author_id = (?)")) {
            preparedStatement.setString(1, authorId.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            List<RecordEntity> recordEntities = new ArrayList<>();

            while (resultSet.next()) {
                recordEntities.add(recordResultSetMapper.map(resultSet));
            }

            return recordEntities;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void addTag(int tagId, UUID recordId) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tags_records " +
                     "VALUES (?, ?)")) {
            preparedStatement.setInt(1, tagId);
            preparedStatement.setString(2, recordId.toString());
            preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }
}
