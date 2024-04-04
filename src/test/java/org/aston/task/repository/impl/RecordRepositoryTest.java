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

public class RecordRepositoryTest {

    @Container
    public static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:14-alpine")
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withInitScript("db/schema.SQL")
                    .withPassword("test");

    RecordEntityRepositoryImpl recordEntityRepository;

    UserEntityRepositoryImpl userEntityRepository;

    UserEntity user;

    RecordEntity record;

    UUID userId;

    String name;

    UUID recordId;

    String title;

    String text;


    @BeforeEach
    public void beforeEach() {
        user = new UserEntity();

        userId = UUID.randomUUID();
        name = "name";

        user.setId(userId);
        user.setName(name);

        record = new RecordEntity();

        recordId = UUID.randomUUID();
        title = "Title";
        text = "Text";

        record.setId(recordId);
        record.setTitle(title);
        record.setText(text);
        record.setAuthor(user);

        container.start();
        recordEntityRepository = new RecordEntityRepositoryImpl();
        userEntityRepository = new UserEntityRepositoryImpl();

        String jdbcURL = "?" + container.getJdbcUrl();
        recordEntityRepository.setConnectionManager(new TestConnectionManager(jdbcURL));
        userEntityRepository.setConnectionManager(new TestConnectionManager(jdbcURL));
    }

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
    void saveFindRecordTest() {
        userEntityRepository.save(user);

        RecordEntity recordEntity = recordEntityRepository.save(record);

        Assertions.assertEquals(recordId, recordEntity.getId());
        Assertions.assertEquals(title, recordEntity.getTitle());
        Assertions.assertEquals(text, recordEntity.getText());
        Assertions.assertEquals(userId, recordEntity.getAuthor().getId());
    }

    @Test
    void deleteRecordTest() {
        userEntityRepository.save(user);
        recordEntityRepository.save(record);

        recordEntityRepository.deleteById(recordId);

        RecordEntity recordEntity = recordEntityRepository.findById(recordId);

        Assertions.assertNull(recordEntity.getId());
    }

    @Test
    void updateRecordTest() {
        userEntityRepository.save(user);
        recordEntityRepository.save(record);

        String title2 = "Another title";
        String text2 = "Another text";

        record.setTitle(title2);
        record.setText(text2);

        RecordEntity recordEntity = recordEntityRepository.update(record, recordId);

        Assertions.assertEquals(recordId, recordEntity.getId());
        Assertions.assertEquals(title2, recordEntity.getTitle());
        Assertions.assertEquals(text2, recordEntity.getText());
        Assertions.assertEquals(userId, recordEntity.getAuthor().getId());
    }

    @Test
    void findRecordByAuthorId() {
        userEntityRepository.save(user);
        recordEntityRepository.save(record);

        List<RecordEntity> recordEntities = recordEntityRepository.findRecordByAuthorId(userId);
        RecordEntity recordEntity = recordEntities.get(0);

        Assertions.assertEquals(1, recordEntities.size());
        Assertions.assertEquals(recordId, recordEntity.getId());
        Assertions.assertEquals(title, recordEntity.getTitle());
        Assertions.assertEquals(text, recordEntity.getText());
        Assertions.assertEquals(userId, recordEntity.getAuthor().getId());
    }

    @Test
    void findAllTest() {
        userEntityRepository.save(user);
        recordEntityRepository.save(record);

        List<RecordEntity> recordEntities = recordEntityRepository.findAll();
        RecordEntity recordEntity = recordEntities.get(0);

        Assertions.assertEquals(1, recordEntities.size());
        Assertions.assertEquals(recordId, recordEntity.getId());
        Assertions.assertEquals(title, recordEntity.getTitle());
        Assertions.assertEquals(text, recordEntity.getText());
        Assertions.assertEquals(userId, recordEntity.getAuthor().getId());
    }

    @Test
    void checkRandomUUIDTest() {
        Assertions.assertThrows(NotFoundException.class, () -> recordEntityRepository.check(UUID.randomUUID()));
    }
}
