package org.aston.task.repository.impl;

import org.aston.task.db.ConnectionManager;
import org.aston.task.exceptions.NotFoundException;
import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class LikeRepositoryTest {

    @Container
    public static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:14-alpine")
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withInitScript("db/schema.SQL")
                    .withPassword("test");

    LikeRepositoryImpl likeRepository;

    RecordEntityRepositoryImpl recordEntityRepository;

    UserEntityRepositoryImpl userEntityRepository;

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

        String jdbcURL = "?" + container.getJdbcUrl();
        likeRepository.setConnectionManager(new TestConnectionManager(jdbcURL));
        recordEntityRepository.setConnectionManager(new TestConnectionManager(jdbcURL));
        userEntityRepository.setConnectionManager(new TestConnectionManager(jdbcURL));
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


        userEntityRepository.save(user);
        recordEntityRepository.save(record);

        likeRepository.addLike(recordId, userId);

        List<RecordEntity> recordEntities = likeRepository.findLikesByUserId(userId);

        Assertions.assertEquals(1, recordEntities.size());
        Assertions.assertEquals(recordId, recordEntities.get(0).getId());
        Assertions.assertEquals(title, recordEntities.get(0).getTitle());
        Assertions.assertEquals(text, recordEntities.get(0).getText());

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


        userEntityRepository.save(user);
        recordEntityRepository.save(record);

        likeRepository.addLike(recordId, userId);

        List<UserEntity> userEntities = likeRepository.findLikesByRecordId(recordId);

        Assertions.assertEquals(1, userEntities.size());
        Assertions.assertEquals(userId, userEntities.get(0).getId());
        Assertions.assertEquals(name, userEntities.get(0).getName());
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
