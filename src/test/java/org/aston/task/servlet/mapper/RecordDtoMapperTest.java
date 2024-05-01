package org.aston.task.servlet.mapper;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.TagEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.servlet.dto.*;
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
        List<Integer> tags = new ArrayList<>();
        tags.add(1);
        RecordIncomingDto recordIncomingDto = new RecordIncomingDto(title, text);
        recordIncomingDto.setTag(tags);
        RecordEntity recordEntity = recordDtoMapper.incomingRecordMap(recordIncomingDto);

        Assertions.assertEquals(title, recordEntity.getTitle(), "Title must be equal " + title);
        Assertions.assertEquals(text, recordEntity.getText(), "Text must be equal " + text);
        Assertions.assertEquals(tags.size(), recordEntity.getTag().size());
        Assertions.assertEquals(tags.get(0), recordEntity.getTag().get(0).getId());
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

    @Test
    void recordDtoMapper_shortDtoMapTest() {
        String title = "Title";
        String text = "text";
        RecordEntity recordEntity = new RecordEntity();
        UUID recordId = UUID.randomUUID();
        recordEntity.setId(recordId);
        recordEntity.setTitle(title);
        recordEntity.setText(text);

        RecordShortDto recordShortDto = recordDtoMapper.shortOutComingMap(recordEntity);
        Assertions.assertEquals(recordId.toString(), recordShortDto.getId(), "Id must be equal " + recordId);
        Assertions.assertEquals(title, recordShortDto.getTitle(), "Title must be equal " + title);
        Assertions.assertEquals(text, recordShortDto.getText(), "Text must be equal " + text);
    }

    @Test
    void recordOutcomingDtoMap_nullRecordTest() {
        RecordOutcomingDto recordOutcomingDto = recordDtoMapper.outComingRecordMap(null);
        Assertions.assertNull(recordOutcomingDto);
    }

    @Test
    void recordIncomingDtoMap_nullRecordTest() {
        RecordEntity recordEntity = recordDtoMapper.incomingRecordMap(null);
        Assertions.assertNull(recordEntity);
    }

    @Test
    void recordShortDtoMap_nullTest() {
        RecordShortDto recordShortDto = recordDtoMapper.shortOutComingMap(null);
        Assertions.assertNull(recordShortDto);
    }

    @Test
    void agEntityListToTagOutcomingDtoListTest() {
        List<TagOutcomingDto> list = recordDtoMapper.tagEntityListToTagOutcomingDtoList(null);
        Assertions.assertNull(list);
    }
}
