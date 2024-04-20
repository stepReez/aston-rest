package org.aston.task.service.impl;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.RecordLikes;
import org.aston.task.model.UserEntity;
import org.aston.task.repository.impl.LikeRepositoryImpl;
import org.aston.task.repository.impl.RecordEntityRepositoryImpl;
import org.aston.task.repository.impl.TagEntityRepositoryImpl;
import org.aston.task.repository.impl.UserEntityRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class RecordServiceTest {

    RecordServiceImpl recordService;

    @BeforeEach
    public void beforeEach() {
        recordService = new RecordServiceImpl();
    }

    @Test
    void createRecordTest() {
        RecordEntity recordEntity = new RecordEntity();

        String title = "Title";
        String text = "Text";

        recordEntity.setTitle(title);
        recordEntity.setText(text);

        UserEntity userEntity = new UserEntity();

        UUID userId = UUID.randomUUID();
        String name = "Name";

        userEntity.setId(userId);
        userEntity.setName(name);

        RecordEntityRepositoryImpl recordEntityRepository = Mockito.mock(RecordEntityRepositoryImpl.class);
        recordService.setRecordEntityRepository(recordEntityRepository);

        UserEntityRepositoryImpl userEntityRepository = Mockito.mock(UserEntityRepositoryImpl.class);
        recordService.setUserEntityRepository(userEntityRepository);

        TagEntityRepositoryImpl tagEntityRepository = Mockito.mock(TagEntityRepositoryImpl.class);
        recordService.setTagRepository(tagEntityRepository);

        Mockito
                .when(tagEntityRepository.getTagsByRecord(Mockito.any()))
                .thenReturn(new ArrayList<>());

        Mockito
                .when(userEntityRepository.findById(userId))
                .thenReturn(userEntity);

        Mockito
                .when(recordEntityRepository.save(recordEntity))
                .thenReturn(recordEntity);

        RecordEntity outRecordEntity = recordService.createRecord(recordEntity, userId);

        Assertions.assertEquals(title, outRecordEntity.getTitle());
        Assertions.assertEquals(text, outRecordEntity.getText());
        Assertions.assertEquals(userId, outRecordEntity.getAuthor().getId());
        Assertions.assertNotNull(outRecordEntity.getLikes());
    }

    @Test
    void findRecordByIdTest() {
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

        RecordEntityRepositoryImpl recordEntityRepository = Mockito.mock(RecordEntityRepositoryImpl.class);
        recordService.setRecordEntityRepository(recordEntityRepository);

        LikeRepositoryImpl likeRepository = Mockito.mock(LikeRepositoryImpl.class);
        recordService.setLikeRepository(likeRepository);

        TagEntityRepositoryImpl tagEntityRepository = Mockito.mock(TagEntityRepositoryImpl.class);
        recordService.setTagRepository(tagEntityRepository);

        Mockito
                .when(tagEntityRepository.getTagsByRecord(Mockito.any()))
                .thenReturn(new ArrayList<>());

        Mockito
                .when(recordEntityRepository.findById(uuid))
                .thenReturn(recordEntity);

        Mockito
                .when(likeRepository.findLikesByRecordId(uuid))
                .thenReturn(new RecordLikes());

        RecordEntity record = recordService.findRecordById(uuid);

        Assertions.assertEquals(uuid, record.getId());
        Assertions.assertEquals(title, record.getTitle());
        Assertions.assertEquals(text, record.getText());
        Assertions.assertEquals(authorId, record.getAuthor().getId());
        Assertions.assertNotNull(record.getLikes());
    }

    @Test
    void updateRecordTest() {
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

        RecordEntityRepositoryImpl recordEntityRepository = Mockito.mock(RecordEntityRepositoryImpl.class);
        recordService.setRecordEntityRepository(recordEntityRepository);

        LikeRepositoryImpl likeRepository = Mockito.mock(LikeRepositoryImpl.class);
        recordService.setLikeRepository(likeRepository);

        TagEntityRepositoryImpl tagEntityRepository = Mockito.mock(TagEntityRepositoryImpl.class);
        recordService.setTagRepository(tagEntityRepository);

        Mockito
                .when(tagEntityRepository.getTagsByRecord(Mockito.any()))
                .thenReturn(new ArrayList<>());

        Mockito
                .when(recordEntityRepository.update(recordEntity, uuid))
                .thenReturn(recordEntity);

        Mockito
                .when(likeRepository.findLikesByRecordId(uuid))
                .thenReturn(new RecordLikes());

        RecordEntity record = recordService.updateRecord(recordEntity, uuid);

        Assertions.assertEquals(uuid, record.getId());
        Assertions.assertEquals(title, record.getTitle());
        Assertions.assertEquals(text, record.getText());
        Assertions.assertEquals(authorId, record.getAuthor().getId());
        Assertions.assertNotNull(record.getLikes());
    }

    @Test
    void deleteUserTest() {
        UUID uuid = UUID.randomUUID();

        RecordEntityRepositoryImpl recordEntityRepository = Mockito.mock(RecordEntityRepositoryImpl.class);
        recordService.setRecordEntityRepository(recordEntityRepository);

        Mockito
                .when(recordEntityRepository.deleteById(uuid))
                .thenReturn(true);

        boolean isDeleted = recordService.deleteRecord(uuid);

        Assertions.assertTrue(isDeleted);
    }

    @Test
    void findAllTest() {
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

        RecordEntityRepositoryImpl recordEntityRepository = Mockito.mock(RecordEntityRepositoryImpl.class);
        recordService.setRecordEntityRepository(recordEntityRepository);

        LikeRepositoryImpl likeRepository = Mockito.mock(LikeRepositoryImpl.class);
        recordService.setLikeRepository(likeRepository);

        TagEntityRepositoryImpl tagEntityRepository = Mockito.mock(TagEntityRepositoryImpl.class);
        recordService.setTagRepository(tagEntityRepository);

        Mockito
                .when(tagEntityRepository.getTagsByRecord(Mockito.any()))
                .thenReturn(new ArrayList<>());

        Mockito
                .when(recordEntityRepository.findAll())
                .thenReturn(recordEntities);

        Mockito
                .when(likeRepository.findLikesByRecordId(Mockito.any()))
                .thenReturn(new RecordLikes());

        List<RecordEntity> recordEntityList = recordService.findAll();

        Assertions.assertEquals(2, recordEntityList.size());
        Assertions.assertNotNull(recordEntityList.get(0).getLikes());
        Assertions.assertNotNull(recordEntityList.get(1).getLikes());
    }
}
