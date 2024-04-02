package org.aston.task.repository.mapper.impl;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.repository.mapper.RecordResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class RecordResultSetMapperImpl implements RecordResultSetMapper {
    @Override
    public RecordEntity map(ResultSet resultSet) throws SQLException {
        RecordEntity recordEntity = new RecordEntity();
        recordEntity.setId(UUID.fromString(resultSet.getString("record_id")));
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.fromString(resultSet.getString("author_id")));
        recordEntity.setAuthor(userEntity);
        recordEntity.setTitle(resultSet.getString("title"));
        recordEntity.setText(resultSet.getString("text"));
        return recordEntity;
    }
}
