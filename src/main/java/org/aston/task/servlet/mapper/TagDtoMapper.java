package org.aston.task.servlet.mapper;

import org.aston.task.model.TagEntity;
import org.aston.task.servlet.dto.TagInomingDto;
import org.aston.task.servlet.dto.TagOutcomingDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TagDtoMapper {

    TagOutcomingDto outcomingMap(TagEntity tag);

    TagEntity incomingMap(TagInomingDto incomingDto);
}
