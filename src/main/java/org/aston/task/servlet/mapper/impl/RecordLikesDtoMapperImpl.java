package org.aston.task.servlet.mapper.impl;

import org.aston.task.model.RecordLikes;
import org.aston.task.servlet.dto.RecordLikesDto;
import org.aston.task.servlet.mapper.RecordLikesDtoMapper;

import java.util.UUID;

public class RecordLikesDtoMapperImpl implements RecordLikesDtoMapper {
    @Override
    public RecordLikesDto map(RecordLikes recordLikes) {
        RecordLikesDto recordLikesDto = new RecordLikesDto();
        recordLikesDto.setRecordLikes(recordLikes.getRecordLikes().stream().map(UUID::toString).toList());
        return recordLikesDto;
    }
}
