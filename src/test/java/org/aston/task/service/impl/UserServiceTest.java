package org.aston.task.service.impl;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.repository.LikeRepository;
import org.aston.task.repository.RecordEntityRepository;
import org.aston.task.repository.UserEntityRepository;
import org.aston.task.repository.impl.LikeRepositoryImpl;
import org.aston.task.repository.impl.RecordEntityRepositoryImpl;
import org.aston.task.repository.impl.UserEntityRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
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
        String name = "Name";

        userEntity.setUserName(name);

        UserEntityRepository userEntityRepositoryMock = Mockito.mock(UserEntityRepositoryImpl.class);
        userService.setUserEntityRepository(userEntityRepositoryMock);

        Mockito
                .when(userEntityRepositoryMock.save(userEntity))
                .thenReturn(userEntity);

        UserEntity outComeUser = userService.createUser(userEntity);

        Assertions.assertEquals(name, outComeUser.getUserName(), "Name must be equal " + name);
        Assertions.assertNotNull(outComeUser.getId(), "Id must be not null");
        Assertions.assertNotNull(outComeUser.getLikes(), "Likes must be not null");
        Assertions.assertNotNull(outComeUser.getRecords(), "Records must be not null");
    }

    @Test
    void findUserByIdTest() {
        UserEntityRepository userEntityRepositoryMock = Mockito.mock(UserEntityRepositoryImpl.class);
        userService.setUserEntityRepository(userEntityRepositoryMock);

        RecordEntityRepository recordEntityRepositoryMock = Mockito.mock(RecordEntityRepositoryImpl.class);
        userService.setRecordEntityRepository(recordEntityRepositoryMock);

        LikeRepository likeRepositoryMock = Mockito.mock(LikeRepositoryImpl.class);
        userService.setLikeRepository(likeRepositoryMock);

        UserEntity userEntity = new UserEntity();
        UUID id = UUID.randomUUID();
        String name = "Name";

        userEntity.setId(id);
        userEntity.setUserName(name);

        Mockito
                .when(userEntityRepositoryMock.findById(id))
                .thenReturn(userEntity);

        Mockito
                .when(recordEntityRepositoryMock.findRecordByAuthorId(Mockito.any()))
                .thenReturn(new ArrayList<>());

        Mockito
                .when(likeRepositoryMock.findLikesByUserId(Mockito.any()))
                .thenReturn(new ArrayList<>());

        UserEntity outComingUser = userService.findUserById(id);

        Assertions.assertEquals(id, outComingUser.getId(), "Id must be equal " + id);
        Assertions.assertEquals(name, outComingUser.getUserName(), "Name must be equal " + name);
        Assertions.assertNotNull(outComingUser.getLikes(), "Likes must be not null");
        Assertions.assertNotNull(outComingUser.getRecords(), "Records must be not null");
    }

    @Test
    void updateUserTest() {
        UserEntityRepository userEntityRepositoryMock = Mockito.mock(UserEntityRepositoryImpl.class);
        userService.setUserEntityRepository(userEntityRepositoryMock);

        RecordEntityRepository recordEntityRepositoryMock = Mockito.mock(RecordEntityRepositoryImpl.class);
        userService.setRecordEntityRepository(recordEntityRepositoryMock);

        LikeRepository likeRepositoryMock = Mockito.mock(LikeRepositoryImpl.class);
        userService.setLikeRepository(likeRepositoryMock);

        UserEntity userEntity = new UserEntity();
        UUID id = UUID.randomUUID();
        String name = "Name";

        userEntity.setId(id);
        userEntity.setUserName(name);

        Mockito
                .when(userEntityRepositoryMock.update(userEntity, id))
                .thenReturn(userEntity);

        Mockito
                .when(recordEntityRepositoryMock.findRecordByAuthorId(Mockito.any()))
                .thenReturn(new ArrayList<>());

        Mockito
                .when(likeRepositoryMock.findLikesByUserId(Mockito.any()))
                .thenReturn(new ArrayList<>());

        UserEntity outComingUser = userService.updateUser(userEntity, id);

        Assertions.assertEquals(id, outComingUser.getId(), "Id must be equal " + id);
        Assertions.assertEquals(name, outComingUser.getUserName(), "Name must be equal " + name);
        Assertions.assertNotNull(outComingUser.getLikes(), "Likes must be not null");
        Assertions.assertNotNull(outComingUser.getRecords(), "Records must be not null");
    }

    @Test
    void deleteUserTest() {
        UserEntityRepository userEntityRepositoryMock = Mockito.mock(UserEntityRepositoryImpl.class);
        userService.setUserEntityRepository(userEntityRepositoryMock);

        UUID id = UUID.randomUUID();

        Mockito
                .when(userEntityRepositoryMock.deleteById(id))
                .thenReturn(true);

        boolean isDeleted = userService.deleteUser(id);

        Assertions.assertTrue(isDeleted);
    }

    @Test
    void findAllTest() {
        UserEntityRepository userEntityRepositoryMock = Mockito.mock(UserEntityRepositoryImpl.class);
        userService.setUserEntityRepository(userEntityRepositoryMock);

        RecordEntityRepository recordEntityRepositoryMock = Mockito.mock(RecordEntityRepositoryImpl.class);
        userService.setRecordEntityRepository(recordEntityRepositoryMock);

        LikeRepository likeRepositoryMock = Mockito.mock(LikeRepositoryImpl.class);
        userService.setLikeRepository(likeRepositoryMock);

        UserEntity userEntity1 = new UserEntity();
        UUID id1 = UUID.randomUUID();
        String name1 = "Name1";

        userEntity1.setId(id1);
        userEntity1.setUserName(name1);

        UserEntity userEntity2 = new UserEntity();
        UUID id2 = UUID.randomUUID();
        String name2 = "Name2";

        userEntity2.setId(id2);
        userEntity2.setUserName(name2);

        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(userEntity1);
        userEntities.add(userEntity2);

        Mockito
                .when(userEntityRepositoryMock.findAll())
                .thenReturn(userEntities);

        List<RecordEntity> recordEntities = new ArrayList<>();
        Mockito
                .when(recordEntityRepositoryMock.findRecordByAuthorId(Mockito.any()))
                .thenReturn(recordEntities);


        List<RecordEntity> likes = new ArrayList<>();
        Mockito
                .when(likeRepositoryMock.findLikesByUserId(Mockito.any()))
                .thenReturn(likes);

        List<UserEntity> userEntityList = userService.findAll();

        Assertions.assertEquals(2, userEntityList.size(), "Size must be equal " + 2);
        Assertions.assertEquals(id1, userEntityList.get(0).getId(), "Id must be equal " + id1);
        Assertions.assertEquals(id2, userEntityList.get(1).getId(), "Id must be equal " + id2);
        Assertions.assertNotNull(userEntityList.get(0).getRecords());
        Assertions.assertNotNull(userEntityList.get(1).getRecords());
        Assertions.assertNotNull(userEntityList.get(0).getLikes());
        Assertions.assertNotNull(userEntityList.get(1).getLikes());
    }
}
