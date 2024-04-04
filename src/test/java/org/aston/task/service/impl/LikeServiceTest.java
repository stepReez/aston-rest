package org.aston.task.service.impl;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.repository.LikeRepository;
import org.aston.task.repository.impl.LikeRepositoryImpl;
import org.aston.task.service.LikeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class LikeServiceTest {

    LikeServiceImpl likeService;

    @BeforeEach
    public void beforeEach() {
        likeService = new LikeServiceImpl();
    }

    @Test
    void getLikesByUserTest() {
        LikeRepository likeRepositoryMock = Mockito.mock(LikeRepositoryImpl.class);
        likeService.setLikeRepository(likeRepositoryMock);

        List<RecordEntity> recordEntities = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            RecordEntity recordEntity = new RecordEntity();

            UUID uuid = UUID.randomUUID();
            String title = "Title";
            String text = "Text";

            recordEntity.setId(uuid);
            recordEntity.setTitle(title);
            recordEntity.setText(text);

            UserEntity userEntity = new UserEntity();
            UUID authorId = UUID.randomUUID();
            userEntity.setId(authorId);

            recordEntity.setAuthor(userEntity);
            recordEntities.add(recordEntity);
        }
        UUID id = UUID.randomUUID();
        Mockito
                .when(likeRepositoryMock.findLikesByUserId(id))
                .thenReturn(recordEntities);

        List<RecordEntity> recordEntityList = likeService.getLikesByUser(id);

        Assertions.assertEquals(2, recordEntityList.size());
    }

    @Test
    void  getLikesByRecordTest() {
        LikeRepositoryImpl likeRepositoryMock = Mockito.mock(LikeRepositoryImpl.class);
        likeService.setLikeRepository(likeRepositoryMock);

        List<UserEntity> userEntities = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            UserEntity user = new UserEntity();

            UUID uuid = UUID.randomUUID();
            String name = "name";

            user.setId(uuid);
            user.setUserName(name);

            userEntities.add(user);
        }

        UUID id = UUID.randomUUID();

        Mockito
                .when(likeRepositoryMock.findLikesByRecordId(id))
                .thenReturn(userEntities);

        List<UserEntity> userEntityList = likeService.getLikesByRecord(id);

        Assertions.assertEquals(2, userEntityList.size());
    }
}
