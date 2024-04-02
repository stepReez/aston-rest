package org.aston.task.servlet.mapper.impl;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.servlet.dto.RecordIncomingDto;
import org.aston.task.servlet.dto.RecordOutcomingDto;
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
        return recordOutcomingDto;
    }

    @Override
    public RecordEntity incomingRecordMap(RecordIncomingDto recordIncomingDto) {
        RecordEntity recordEntity = new RecordEntity();
        recordEntity.setTitle(recordIncomingDto.getTitle());
        recordEntity.setText(recordIncomingDto.getText());
        return recordEntity;
    }
}
