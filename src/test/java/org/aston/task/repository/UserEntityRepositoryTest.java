package org.aston.task.repository;

import org.aston.task.model.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PersistenceConfigTest.class)
@Testcontainers
@Transactional
class UserEntityRepositoryTest {

    @Autowired
    private UserEntityRepository userEntityRepository;

    private UserEntity user;

    private String name;

    @BeforeEach
    void beforeEach() {
        user = new UserEntity();
        name = "name";
        user.setName(name);
    }

    @Test
    void createUserTest() {
        UserEntity userEntity = userEntityRepository.save(user);
        Assertions.assertEquals(name, userEntity.getName());
    }

    @Test
    void getUserTest() {
        UserEntity savedUser = userEntityRepository.save(user);
        UserEntity testedUser = userEntityRepository.findById(savedUser.getId()).get();
        Assertions.assertEquals(name, testedUser.getName());
    }

    @Test
    void deleteUserTest() {
        UserEntity savedUser = userEntityRepository.save(user);
        userEntityRepository.deleteById(savedUser.getId());
        Assertions.assertFalse(userEntityRepository.findById(savedUser.getId()).isPresent());
    }
}
