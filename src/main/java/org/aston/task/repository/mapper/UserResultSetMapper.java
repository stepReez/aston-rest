package org.aston.task.repository.mapper;

import org.aston.task.model.UserEntity;

import java.sql.ResultSet;

public interface UserResultSetMapper {
    UserEntity map(ResultSet resultSet);
}
