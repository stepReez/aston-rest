package org.aston.task.repository.impl;

import org.aston.task.db.ConnectionManager;
import org.aston.task.db.ConnectionManagerImpl;
import org.aston.task.exceptions.NotFoundException;
import org.aston.task.model.RecordEntity;
import org.aston.task.model.TagEntity;
import org.aston.task.model.UserEntity;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LikeRepositoryTest {

    @Container
    public static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:14-alpine")
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withInitScript("db/schema.sql")
                    .withPassword("test");

    LikeRepositoryImpl likeRepository;

    RecordEntityRepositoryImpl recordEntityRepository;

    UserEntityRepositoryImpl userEntityRepository;

    TagEntityRepositoryImpl tagEntityRepository;

    @BeforeAll
    static void start() {
        container.start();
    }

    @AfterAll
    static void end() {
        container.stop();
    }

    @BeforeEach
    public void beforeEach() {
        likeRepository = new LikeRepositoryImpl();
        recordEntityRepository = new RecordEntityRepositoryImpl();
        userEntityRepository = new UserEntityRepositoryImpl();
        tagEntityRepository = new TagEntityRepositoryImpl();

        String jdbcURL = "?" + container.getJdbcUrl();
        likeRepository.setConnectionManager(new TestConnectionManager(jdbcURL));
        recordEntityRepository.setConnectionManager(new TestConnectionManager(jdbcURL));
        userEntityRepository.setConnectionManager(new TestConnectionManager(jdbcURL));
        tagEntityRepository.setConnectionManager(new TestConnectionManager(jdbcURL));
        tagEntityRepository.addTag("name");
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
    void addAndFindLikeByUserTest() throws SQLException {
        UserEntity user = new UserEntity();

        UUID userId = UUID.randomUUID();
        String name = "name";

        user.setId(userId);
        user.setName(name);

        RecordEntity record = new RecordEntity();

        UUID recordId = UUID.randomUUID();
        String title = "Title";
        String text = "Text";

        record.setId(recordId);
        record.setTitle(title);
        record.setText(text);
        record.setAuthor(user);

        TagEntity tag = new TagEntity();
        tag.setId(1);
        tag.setName("name");

        List<TagEntity> tagEntities = new ArrayList<>();
        tagEntities.add(tag);
        record.setTag(tagEntities);

        userEntityRepository.save(user);
        recordEntityRepository.save(record);

        likeRepository.addLike(recordId, userId);

        List<UUID> userLikes = likeRepository.findLikesByUserId(userId).getUserLikes();

        Assertions.assertEquals(1, userLikes.size());
        Assertions.assertEquals(recordId, userLikes.get(0));

    }

    @Test
    void addAndFindLikeByRecordTest() {
        UserEntity user = new UserEntity();

        UUID userId = UUID.randomUUID();
        String name = "name";

        user.setId(userId);
        user.setName(name);

        RecordEntity record = new RecordEntity();

        UUID recordId = UUID.randomUUID();
        String title = "Title";
        String text = "Text";

        record.setId(recordId);
        record.setTitle(title);
        record.setText(text);
        record.setAuthor(user);

        TagEntity tag = new TagEntity();
        tag.setId(1);
        tag.setName("name");

        List<TagEntity> tagEntities = new ArrayList<>();
        tagEntities.add(tag);
        record.setTag(tagEntities);

        userEntityRepository.save(user);
        recordEntityRepository.save(record);

        likeRepository.addLike(recordId, userId);

        List<UUID> userLikes = likeRepository.findLikesByUserId(userId).getUserLikes();

        Assertions.assertEquals(1, userLikes.size());
        Assertions.assertEquals(recordId, userLikes.get(0));
    }

    @Test
    void deleteLikeTest() {
        UserEntity user = new UserEntity();

        UUID userId = UUID.randomUUID();
        String name = "name";

        user.setId(userId);
        user.setName(name);

        RecordEntity record = new RecordEntity();

        UUID recordId = UUID.randomUUID();
        String title = "Title";
        String text = "Text";

        record.setId(recordId);
        record.setTitle(title);
        record.setText(text);
        record.setAuthor(user);

        TagEntity tag = new TagEntity();
        tag.setId(1);
        tag.setName("name");

        List<TagEntity> tagEntities = new ArrayList<>();
        tagEntities.add(tag);
        record.setTag(tagEntities);

        userEntityRepository.save(user);
        recordEntityRepository.save(record);

        likeRepository.addLike(recordId, userId);
        likeRepository.removeLike(recordId, userId);

        Assertions.assertThrows(NotFoundException.class, () -> likeRepository.check(recordId, userId));
    }

    @Test
    void checkRandomUUIDTest() {
        Assertions.assertThrows(NotFoundException.class, () -> likeRepository.check(UUID.randomUUID(), UUID.randomUUID()));
    }
}
