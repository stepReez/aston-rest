package org.aston.task.repository.mapper;

import org.aston.task.model.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserResultSetMapper {
    UserEntity map(ResultSet resultSet) throws SQLException;
}
