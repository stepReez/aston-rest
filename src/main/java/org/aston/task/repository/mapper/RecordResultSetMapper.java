package org.aston.task.repository.mapper;

import org.aston.task.model.RecordEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RecordResultSetMapper {
    RecordEntity map(ResultSet resultSet) throws SQLException;
}
