package org.aston.task.servlet.mapper;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.TagEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.servlet.dto.RecordIncomingDto;
import org.aston.task.servlet.dto.RecordOutcomingDto;
import org.aston.task.servlet.dto.UserShortDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class RecordDtoMapperTest {
    ApplicationContext context = new AnnotationConfigApplicationContext(RecordDtoMapperImpl.class,
            UserDtoMapperImpl.class,
            TagDtoMapperImpl.class);

    RecordDtoMapperImpl recordDtoMapper;

    @BeforeEach
    public void beforeEach() {
        recordDtoMapper = context.getBean(RecordDtoMapperImpl.class);
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

        UserShortDto userShortDto = new UserShortDto();
        userShortDto.setId(userId1.toString());

        RecordEntity recordEntity = new RecordEntity();
        UUID recordId = UUID.randomUUID();
        recordEntity.setId(recordId);
        recordEntity.setTitle(title);
        recordEntity.setText(text);
        recordEntity.setAuthor(user1);

        TagEntity tag = new TagEntity();
        tag.setId(1);
        tag.setName("name");

        List<TagEntity> tagEntities = new ArrayList<>();
        tagEntities.add(tag);
        recordEntity.setTag(tagEntities);

        RecordOutcomingDto recordOutcomingDto = recordDtoMapper.outComingRecordMap(recordEntity);

        Assertions.assertEquals(recordId.toString(), recordOutcomingDto.getId(), "Id must be equal " + recordId);
        Assertions.assertEquals(title, recordOutcomingDto.getTitle(), "Title must be equal " + title);
        Assertions.assertEquals(text, recordOutcomingDto.getText(), "Text must be equal " + text);
        Assertions.assertEquals(userId1.toString(), recordOutcomingDto.getAuthor().getId(), "Author id must be equal " + userId1);
    }
}
