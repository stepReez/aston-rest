package org.aston.task.repository.impl;

import org.aston.task.db.ConnectionManager;
import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.repository.RecordEntityRepository;
import org.aston.task.repository.mapper.RecordResultSetMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecordEntityRepositoryImpl implements RecordEntityRepository {

    private RecordResultSetMapper recordResultSetMapper;
    private ConnectionManager connectionManager;

    @Override
    public RecordEntity findById(UUID id) {
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM records " +
                    "WHERE record_id == (?)");
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return recordResultSetMapper.map(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean deleteById(UUID id) {
        return false;
    }

    @Override
    public List<RecordEntity> findAll() {
        return null;
    }

    @Override
    public RecordEntity save(RecordEntity recordEntity) {
        return null;
    }

    @Override
    public RecordEntity update(RecordEntity recordEntity, UUID uuid) {
        return null;
    }

    @Override
    public void addLike(RecordEntity recordEntity, UserEntity user) {

    }

    @Override
    public RecordEntity createRecord(RecordEntity recordEntity, UserEntity user) {
        return null;
    }
}
