package org.aston.task.repository.impl;

import org.aston.task.db.ConnectionManager;
import org.aston.task.exceptions.NotFoundException;
import org.aston.task.model.TagEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class TagRepositoryTest {
    @Container
    public static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:14-alpine")
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withInitScript("db/schema.sql")
                    .withPassword("test");

    TagEntityRepositoryImpl tagRepository = new TagEntityRepositoryImpl();


    @BeforeEach
    public void beforeEach() {
        container.start();
        tagRepository = new TagEntityRepositoryImpl();

        String jdbcURL = "?" + container.getJdbcUrl();
        tagRepository.setConnectionManager(new TestConnectionManager(jdbcURL));}

    @AfterEach
    public void afterEach() {
        container.stop();
    }

    private static class TestConnectionManager implements ConnectionManager {
        String jdbcUrl;
        TestConnectionManager(String jdbcUrl) {
            this.jdbcUrl = jdbcUrl;
        }

        @Override
        public Connection getConnection() throws SQLException, ClassNotFoundException {
            return container.createConnection(jdbcUrl);
        }
    }

    @Test
    void createFindAllTest() {
        TagEntity tagEntity = new TagEntity();
        String name = "Tag";

        tagEntity.setName(name);

        tagRepository.addTag(name);
        List<TagEntity> tagEntities = tagRepository.getAllTags();
        TagEntity tag = tagEntities.get(0);

        Assertions.assertEquals(1, tag.getId());
        Assertions.assertEquals(name, tag.getName());
    }

    @Test
    void removeFindAllTest() {
        TagEntity tagEntity = new TagEntity();
        String name = "Tag";

        tagEntity.setName(name);

        tagRepository.addTag(name);
        tagRepository.removeTag(1);
        List<TagEntity> tagEntities = tagRepository.getAllTags();
        Assertions.assertEquals(0, tagEntities.size());
    }

    @Test
    void checkTest() {
        Assertions.assertThrows(NotFoundException.class, () -> tagRepository.check(0));
    }
}
