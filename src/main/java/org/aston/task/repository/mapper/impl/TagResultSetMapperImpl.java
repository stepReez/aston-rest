package org.aston.task.repository.mapper.impl;

import org.aston.task.model.TagEntity;
import org.aston.task.repository.mapper.TagResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagResultSetMapperImpl implements TagResultSetMapper {
    @Override
    public TagEntity map(ResultSet resultSet) throws SQLException {
        TagEntity tag = new TagEntity();
        tag.setId(resultSet.getInt("tag_id"));
        tag.setName(resultSet.getString("tag_name"));
        return tag;
    }
}
