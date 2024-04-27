package org.aston.task.servlet.mapper;

import org.aston.task.model.TagEntity;
import org.aston.task.servlet.dto.TagInomingDto;
import org.aston.task.servlet.dto.TagOutcomingDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TagDtoMapperTest {
    TagDtoMapper tagDtoMapper;

    @BeforeEach
    void beforeEach() {
        tagDtoMapper = new TagDtoMapperImpl();
    }

    @Test
    void tagDtoMapper_IncomingMapTest() {
        TagInomingDto tagInomingDto = new TagInomingDto();
        String name = "TagName";
        tagInomingDto.setName(name);

        TagEntity tag = tagDtoMapper.incomingMap(tagInomingDto);
        Assertions.assertEquals(name, tag.getName());
    }

    @Test
    void tagDtoMapper_OutComingMapTest() {
        TagEntity tag = new TagEntity();
        int id = 1;
        String name = "Tag name";
        tag.setId(id);
        tag.setName(name);

        TagOutcomingDto tagOutcomingDto = tagDtoMapper.outcomingMap(tag);

        Assertions.assertEquals(id, tagOutcomingDto.getId());
        Assertions.assertEquals(name, tagOutcomingDto.getName());
    }

    @Test
    void testDtoMapper_OutComingNullMapTest() {
        TagOutcomingDto tagOutcomingDto = tagDtoMapper.outcomingMap(null);

        Assertions.assertNull(tagOutcomingDto);
    }

    @Test
    void testDtoMapper_incomingNullMapTest() {
        TagEntity tag = tagDtoMapper.incomingMap(null);

        Assertions.assertNull(tag);
    }
}
