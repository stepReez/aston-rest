package org.aston.task.servlet.mapper.impl;

import org.aston.task.model.TagEntity;
import org.aston.task.servlet.dto.TagInomingDto;
import org.aston.task.servlet.dto.TagOutcomingDto;
import org.aston.task.servlet.mapper.TagDtoMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TagDtoMapperImpl implements TagDtoMapper {
    @Override
    public TagOutcomingDto outcomingMap(TagEntity tag) {
        TagOutcomingDto tagOutcomingDto = new TagOutcomingDto();
        tagOutcomingDto.setId(tag.getId());
        tagOutcomingDto.setName(tag.getName());
        return tagOutcomingDto;
    }

    @Override
    public TagEntity incomingMap(TagInomingDto incomingDto) {
        TagEntity tag = new TagEntity();
        tag.setName(incomingDto.getName());
        return tag;
    }
}
