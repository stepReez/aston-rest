package org.aston.task.service.impl;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.repository.RecordEntityRepository;
import org.aston.task.repository.TagEntityRepository;
import org.aston.task.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class RecordServiceTest {

    RecordServiceImpl recordService;

    RecordEntityRepository recordEntityRepository;
    UserEntityRepository userEntityRepository;
    TagEntityRepository tagEntityRepository;

    @BeforeEach
    public void beforeEach() {
        recordEntityRepository = Mockito.mock(RecordEntityRepository.class);
        userEntityRepository = Mockito.mock(UserEntityRepository.class);
        tagEntityRepository = Mockito.mock(TagEntityRepository.class);

        recordService = new RecordServiceImpl(recordEntityRepository, userEntityRepository, tagEntityRepository);
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

        Mockito
                .when(tagEntityRepository.findByRecordId(Mockito.any()))
                .thenReturn(new ArrayList<>());

        Mockito
                .when(userEntityRepository.findById(userId))
                .thenReturn(Optional.of(userEntity));

        Mockito
                .when(recordEntityRepository.save(recordEntity))
                .thenReturn(recordEntity);

        RecordEntity outRecordEntity = recordService.createRecord(recordEntity, userId);

        Assertions.assertEquals(title, outRecordEntity.getTitle());
        Assertions.assertEquals(text, outRecordEntity.getText());
        Assertions.assertEquals(userId, outRecordEntity.getAuthor().getId());
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

        Mockito
                .when(tagEntityRepository.findByRecordId(Mockito.any()))
                .thenReturn(new ArrayList<>());

        Mockito
                .when(recordEntityRepository.findById(uuid))
                .thenReturn(Optional.of(recordEntity));


        RecordEntity record = recordService.findRecordById(uuid);

        Assertions.assertEquals(uuid, record.getId());
        Assertions.assertEquals(title, record.getTitle());
        Assertions.assertEquals(text, record.getText());
        Assertions.assertEquals(authorId, record.getAuthor().getId());
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

        Mockito
                .when(tagEntityRepository.findByRecordId(Mockito.any()))
                .thenReturn(new ArrayList<>());

        Mockito
                .when(recordEntityRepository.save(recordEntity))
                .thenReturn(recordEntity);

        RecordEntity record = recordService.updateRecord(recordEntity, uuid);

        Assertions.assertEquals(uuid, record.getId());
        Assertions.assertEquals(title, record.getTitle());
        Assertions.assertEquals(text, record.getText());
        Assertions.assertEquals(authorId, record.getAuthor().getId());
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

        Mockito
                .when(tagEntityRepository.findByRecordId(Mockito.any()))
                .thenReturn(new ArrayList<>());

        Mockito
                .when(recordEntityRepository.findAll())
                .thenReturn(recordEntities);

        List<RecordEntity> recordEntityList = recordService.findAll();

        Assertions.assertEquals(2, recordEntityList.size());
    }

    @Test
    void findByTagIdTest() {
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

        Mockito
                .when(tagEntityRepository.findByRecordId(Mockito.any()))
                .thenReturn(new ArrayList<>());

        Mockito
                .when(recordEntityRepository.findByTagId(1))
                .thenReturn(recordEntities);

        List<RecordEntity> recordEntityList = recordService.findRecordsByTagId(1);

        Assertions.assertEquals(2, recordEntityList.size());
    }

    @Test
    void deleteRecordByIdTest() {
        UUID uuid = UUID.randomUUID();

        recordService.deleteRecord(uuid);

        Mockito
                .verify(recordEntityRepository).deleteById(uuid);
    }
}
