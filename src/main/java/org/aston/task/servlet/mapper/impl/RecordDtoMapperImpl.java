package org.aston.task.servlet.mapper.impl;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.TagEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.servlet.dto.RecordIncomingDto;
import org.aston.task.servlet.dto.RecordOutcomingDto;
import org.aston.task.servlet.dto.RecordOutcomingShortDto;
import org.aston.task.servlet.mapper.RecordDtoMapper;

import java.util.List;
import java.util.UUID;

public class RecordDtoMapperImpl implements RecordDtoMapper {
    @Override
    public RecordOutcomingDto outComingRecordMap(RecordEntity recordEntity) {
        RecordOutcomingDto recordOutcomingDto = new RecordOutcomingDto();
        recordOutcomingDto.setId(recordEntity.getId().toString());
        recordOutcomingDto.setTitle(recordEntity.getTitle());
        recordOutcomingDto.setText(recordEntity.getText());
        recordOutcomingDto.setAuthorId(recordEntity.getAuthor().getId().toString());
        List<String> likes = recordEntity.getLikes().stream().map(UserEntity::getId).map(UUID::toString).toList();
        recordOutcomingDto.setLikes(likes);
        recordOutcomingDto.setTag(recordEntity.getTag().getName());
        return recordOutcomingDto;
    }

    @Override
    public RecordOutcomingShortDto outComingShortRecordMap(RecordEntity recordEntity) {
        RecordOutcomingShortDto recordOutcomingShortDto = new RecordOutcomingShortDto();
        recordOutcomingShortDto.setId(recordEntity.getId().toString());
        recordOutcomingShortDto.setTitle(recordEntity.getTitle());
        recordOutcomingShortDto.setText(recordEntity.getText());
        recordOutcomingShortDto.setAuthorId(recordEntity.getAuthor().getId().toString());
        recordOutcomingShortDto.setTag(recordEntity.getTag().getName());
        return recordOutcomingShortDto;
    }

    @Override
    public RecordEntity incomingRecordMap(RecordIncomingDto recordIncomingDto) {
        RecordEntity recordEntity = new RecordEntity();
        recordEntity.setTitle(recordIncomingDto.getTitle());
        recordEntity.setText(recordIncomingDto.getText());
        TagEntity tag = new TagEntity();
        tag.setId(recordIncomingDto.getTag());
        recordEntity.setTag(tag);
        return recordEntity;
    }
}
