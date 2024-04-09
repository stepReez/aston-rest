package org.aston.task.repository.mapper;

import org.aston.task.model.TagEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface TagResultSetMapper {

    TagEntity map(ResultSet resultSet) throws SQLException;
}
