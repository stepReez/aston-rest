package org.aston.task.repository.impl;

import org.aston.task.db.ConnectionManager;
import org.aston.task.exceptions.NotFoundException;
import org.aston.task.model.UserEntity;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class UserRepositoryTest {

    @Container
    public static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:14-alpine")
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withInitScript("db/schema.SQL")
                    .withPassword("test");

    UserEntityRepositoryImpl userEntityRepository;

    UserEntity user;

    UUID userId;

    String name;


    @BeforeEach
    public void beforeEach() {
        user = new UserEntity();

        userId = UUID.randomUUID();
        name = "name";

        user.setId(userId);
        user.setName(name);

        container.start();
        userEntityRepository = new UserEntityRepositoryImpl();

        String jdbcURL = "?" + container.getJdbcUrl();
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
    void saveFindUserTest() {
        UserEntity userEntity = userEntityRepository.save(user);

        Assertions.assertEquals(userId, userEntity.getId());
        Assertions.assertEquals(name, userEntity.getName());
    }

    @Test
    void deleteUserTest() {
        UserEntity userEntity = userEntityRepository.save(user);

        Assertions.assertEquals(userId, userEntity.getId());
        Assertions.assertEquals(name, userEntity.getName());

        userEntityRepository.deleteById(userId);

        UserEntity userEntity1 = userEntityRepository.findById(userId);

        Assertions.assertNull(userEntity1.getId());
    }

    @Test
    void updateUserTest() {
        userEntityRepository.save(user);

        UserEntity userEntity = new UserEntity();
        String newName = "New name";
        userEntity.setName(newName);

        UserEntity userEntity1 = userEntityRepository.update(userEntity, userId);

        Assertions.assertEquals(userId, userEntity1.getId());
        Assertions.assertEquals(newName, userEntity1.getName());
    }

    @Test
    void findAllUserTest() {
        userEntityRepository.save(user);

        UserEntity userEntity = new UserEntity();
        UUID newId = UUID.randomUUID();
        String newName = "New name";
        userEntity.setId(newId);
        userEntity.setName(newName);

        userEntityRepository.save(userEntity);

        List<UserEntity> userEntities = userEntityRepository.findAll();

        Assertions.assertEquals(2, userEntities.size());
    }

    @Test
    void checkRandomUUIDTest() {
        Assertions.assertThrows(NotFoundException.class, () -> userEntityRepository.check(UUID.randomUUID()));
    }
}
