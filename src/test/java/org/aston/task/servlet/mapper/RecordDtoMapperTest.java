package org.aston.task.servlet.mapper;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.servlet.dto.RecordIncomingDto;
import org.aston.task.servlet.dto.RecordOutcomingDto;
import org.aston.task.servlet.dto.RecordOutcomingShortDto;
import org.aston.task.servlet.mapper.impl.RecordDtoMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class RecordDtoMapperTest {

    RecordDtoMapper recordDtoMapper;

    @BeforeEach
    public void beforeEach() {
        recordDtoMapper = new RecordDtoMapperImpl();
    }

    @Test
    void recordDtoMapper_incomingRecordMapTest() {
        String title = "Title";
        String text = "text";
        RecordIncomingDto recordIncomingDto = new RecordIncomingDto(title, text);
        RecordEntity recordEntity = recordDtoMapper.incomingRecordMap(recordIncomingDto);

        Assertions.assertEquals(title, recordEntity.getTitle(), "Title must be equal " + title);
        Assertions.assertEquals(text, recordEntity.getText(), "Text must be equal " + text);
    }

    @Test
    void recordDtoMapper_outComingRecordMapTest() {
        String title = "Title";
        String text = "text";
        UserEntity user1 = new UserEntity();
        UUID userId1 = UUID.randomUUID();
        user1.setId(userId1);
        UserEntity user2 = new UserEntity();
        UUID userId2 = UUID.randomUUID();
        user2.setId(userId2);
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(user1);
        userEntities.add(user2);

        RecordEntity recordEntity = new RecordEntity();
        UUID recordId = UUID.randomUUID();
        recordEntity.setId(recordId);
        recordEntity.setTitle(title);
        recordEntity.setText(text);
        recordEntity.setAuthor(user1);
        recordEntity.setLikes(userEntities);

        RecordOutcomingDto recordOutcomingDto = recordDtoMapper.outComingRecordMap(recordEntity);

        Assertions.assertEquals(recordId.toString(), recordOutcomingDto.getId(), "Id must be equal " + recordId);
        Assertions.assertEquals(title, recordOutcomingDto.getTitle(), "Title must be equal " + title);
        Assertions.assertEquals(text, recordOutcomingDto.getText(), "Text must be equal " + text);
        Assertions.assertEquals(userId1.toString(), recordOutcomingDto.getAuthorId(), "Author id must be equal " + userId1);
        Assertions.assertEquals(2, recordOutcomingDto.getLikes().size(), "Size must be equal " + 2);
        Assertions.assertEquals(userId1.toString(), recordOutcomingDto.getLikes().get(0), "Id must be equal " + userId1);
        Assertions.assertEquals(userId2.toString(), recordOutcomingDto.getLikes().get(1), "Id must be equal " + userId2);
    }

    @Test
    void recordDtoMapper_outComingRecordShortMapTest() {
        String title = "Title";
        String text = "text";
        UserEntity user1 = new UserEntity();
        UUID userId1 = UUID.randomUUID();
        user1.setId(userId1);
        UserEntity user2 = new UserEntity();
        UUID userId2 = UUID.randomUUID();
        user2.setId(userId2);
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(user1);
        userEntities.add(user2);

        RecordEntity recordEntity = new RecordEntity();
        UUID recordId = UUID.randomUUID();
        recordEntity.setId(recordId);
        recordEntity.setTitle(title);
        recordEntity.setText(text);
        recordEntity.setAuthor(user1);

        RecordOutcomingShortDto recordOutcomingShortDto = recordDtoMapper.outComingShortRecordMap(recordEntity);

        Assertions.assertEquals(recordId.toString(), recordOutcomingShortDto.getId(), "Id must be equal " + recordId);
        Assertions.assertEquals(title, recordOutcomingShortDto.getTitle(), "Title must be equal " + title);
        Assertions.assertEquals(text, recordOutcomingShortDto.getText(), "Text must be equal " + text);
        Assertions.assertEquals(userId1.toString(), recordOutcomingShortDto.getAuthorId(), "Author id must be equal " + userId1);
    }
}
