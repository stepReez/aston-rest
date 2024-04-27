package org.aston.task.service.impl;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.repository.RecordEntityRepository;
import org.aston.task.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class UserServiceTest {
    UserServiceImpl userService;

    @BeforeEach
    public void beforeEach() {
        userService = new UserServiceImpl();
    }

    @Test
    void createUserTest() {
        UserEntity userEntity = new UserEntity();
        UUID uuid = UUID.randomUUID();
        String name = "Name";

        userEntity.setId(uuid);
        userEntity.setName(name);

        UserEntityRepository userEntityRepositoryMock = Mockito.mock(UserEntityRepository.class);
        userService.setUserEntityRepository(userEntityRepositoryMock);

        Mockito
                .when(userEntityRepositoryMock.save(userEntity))
                .thenReturn(userEntity);

        UserEntity outComeUser = userService.createUser(userEntity);

        Assertions.assertEquals(name, outComeUser.getName(), "Name must be equal " + name);
        Assertions.assertNotNull(outComeUser.getId(), "Id must be not null");
        Assertions.assertNotNull(outComeUser.getRecords(), "Records must be not null");
    }

    @Test
    void findUserByIdTest() {
        UserEntityRepository userEntityRepositoryMock = Mockito.mock(UserEntityRepository.class);
        userService.setUserEntityRepository(userEntityRepositoryMock);

        RecordEntityRepository recordEntityRepositoryMock = Mockito.mock(RecordEntityRepository.class);
        userService.setRecordEntityRepository(recordEntityRepositoryMock);

        UserEntity userEntity = new UserEntity();
        UUID id = UUID.randomUUID();
        String name = "Name";

        userEntity.setId(id);
        userEntity.setName(name);

        Mockito
                .when(userEntityRepositoryMock.findById(id))
                .thenReturn(Optional.of(userEntity));

        Mockito
                .when(recordEntityRepositoryMock.findByAuthorId(Mockito.any()))
                .thenReturn(new ArrayList<>());

        UserEntity outComingUser = userService.findUserById(id);

        Assertions.assertEquals(id, outComingUser.getId(), "Id must be equal " + id);
        Assertions.assertEquals(name, outComingUser.getName(), "Name must be equal " + name);
        Assertions.assertNotNull(outComingUser.getRecords(), "Records must be not null");
    }

    @Test
    void updateUserTest() {
        UserEntityRepository userEntityRepositoryMock = Mockito.mock(UserEntityRepository.class);
        userService.setUserEntityRepository(userEntityRepositoryMock);

        RecordEntityRepository recordEntityRepositoryMock = Mockito.mock(RecordEntityRepository.class);
        userService.setRecordEntityRepository(recordEntityRepositoryMock);

        UserEntity userEntity = new UserEntity();
        UUID id = UUID.randomUUID();
        String name = "Name";

        userEntity.setId(id);
        userEntity.setName(name);

        Mockito
                .when(userEntityRepositoryMock.save(userEntity))
                .thenReturn(userEntity);

        Mockito
                .when(recordEntityRepositoryMock.findByAuthorId(Mockito.any()))
                .thenReturn(new ArrayList<>());

        UserEntity outComingUser = userService.updateUser(userEntity, id);

        Assertions.assertEquals(id, outComingUser.getId(), "Id must be equal " + id);
        Assertions.assertEquals(name, outComingUser.getName(), "Name must be equal " + name);
        Assertions.assertNotNull(outComingUser.getRecords(), "Records must be not null");
    }

    @Test
    void findAllTest() {
        UserEntityRepository userEntityRepositoryMock = Mockito.mock(UserEntityRepository.class);
        userService.setUserEntityRepository(userEntityRepositoryMock);

        RecordEntityRepository recordEntityRepositoryMock = Mockito.mock(RecordEntityRepository.class);
        userService.setRecordEntityRepository(recordEntityRepositoryMock);

        UserEntity userEntity1 = new UserEntity();
        UUID id1 = UUID.randomUUID();
        String name1 = "Name1";

        userEntity1.setId(id1);
        userEntity1.setName(name1);

        UserEntity userEntity2 = new UserEntity();
        UUID id2 = UUID.randomUUID();
        String name2 = "Name2";

        userEntity2.setId(id2);
        userEntity2.setName(name2);

        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(userEntity1);
        userEntities.add(userEntity2);

        Mockito
                .when(userEntityRepositoryMock.findAll())
                .thenReturn(userEntities);

        List<RecordEntity> recordEntities = new ArrayList<>();
        Mockito
                .when(recordEntityRepositoryMock.findByAuthorId(Mockito.any()))
                .thenReturn(recordEntities);

        List<UserEntity> userEntityList = userService.findAll();

        Assertions.assertEquals(2, userEntityList.size(), "Size must be equal " + 2);
        Assertions.assertEquals(id1, userEntityList.get(0).getId(), "Id must be equal " + id1);
        Assertions.assertEquals(id2, userEntityList.get(1).getId(), "Id must be equal " + id2);
        Assertions.assertNotNull(userEntityList.get(0).getRecords());
        Assertions.assertNotNull(userEntityList.get(1).getRecords());
    }
}
