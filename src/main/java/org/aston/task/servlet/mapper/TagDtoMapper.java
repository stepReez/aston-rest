package org.aston.task.servlet.mapper;

import org.aston.task.model.TagEntity;
import org.aston.task.servlet.dto.TagInomingDto;
import org.aston.task.servlet.dto.TagOutcomingDto;

public interface TagDtoMapper {

    TagOutcomingDto outcomingMap(TagEntity tag);

    TagEntity incomingMap(TagInomingDto incomingDto);
}
