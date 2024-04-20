package org.aston.task.repository.impl;

import org.aston.task.db.ConnectionManager;
import org.aston.task.db.ConnectionManagerImpl;
import org.aston.task.exceptions.NotFoundException;
import org.aston.task.model.TagEntity;
import org.aston.task.repository.TagRepository;
import org.aston.task.repository.mapper.TagResultSetMapper;
import org.aston.task.repository.mapper.impl.TagResultSetMapperImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TagEntityRepositoryImpl implements TagRepository {

    private ConnectionManager connectionManager;

    private final TagResultSetMapper tagResultSetMapper;

    public TagEntityRepositoryImpl() {
        connectionManager = new ConnectionManagerImpl();
        tagResultSetMapper = new TagResultSetMapperImpl();
    }

    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public void addTag(String name) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tags (tag_name) " +
                     "VALUES (?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void removeTag(int id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM tags " +
                     "WHERE tag_id = (?)")) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<TagEntity> getAllTags() {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tags")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TagEntity> tagEntities = new ArrayList<>();
            while (resultSet.next()) {
                tagEntities.add(tagResultSetMapper.map(resultSet));
            }
            return tagEntities;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void check(int id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tags " +
                     "WHERE tag_id = (?)")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                throw new NotFoundException("Tag with id " + id + " not found");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<TagEntity> getTagsByRecord(UUID id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tags AS t " +
                     "LEFT JOIN tags_records tr on t.tag_id = tr.tag_id " +
                     "WHERE tr.record_id = ?")) {
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TagEntity> tagEntities = new ArrayList<>();
            while (resultSet.next()) {
                tagEntities.add(tagResultSetMapper.map(resultSet));
            }
            return tagEntities;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }
}
