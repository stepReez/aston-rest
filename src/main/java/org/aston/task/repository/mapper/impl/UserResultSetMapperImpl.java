package org.aston.task.repository.mapper.impl;

import org.aston.task.model.UserEntity;
import org.aston.task.repository.mapper.UserResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserResultSetMapperImpl implements UserResultSetMapper {
    @Override
    public UserEntity map(ResultSet resultSet) throws SQLException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.fromString(resultSet.getString("user_id")));
        userEntity.setName(resultSet.getString("user_name"));

        return userEntity;
    }
}
