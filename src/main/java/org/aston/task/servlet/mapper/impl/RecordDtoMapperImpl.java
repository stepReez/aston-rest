package org.aston.task.servlet.mapper.impl;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.TagEntity;
import org.aston.task.servlet.dto.RecordIncomingDto;
import org.aston.task.servlet.dto.RecordLikesDto;
import org.aston.task.servlet.dto.RecordOutcomingDto;
import org.aston.task.servlet.dto.RecordOutcomingShortDto;
import org.aston.task.servlet.mapper.RecordDtoMapper;

import java.util.ArrayList;
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
        RecordLikesDto likes = new RecordLikesDto();
        likes.setRecordLikes(recordEntity.getLikes().getRecordLikes().stream().map(UUID::toString).toList());
        recordOutcomingDto.setLikes(likes);
        List<TagEntity> tags = recordEntity.getTag();
        List<String> tagsString = new ArrayList<>();
        tags.forEach(x -> tagsString.add(x.getName()));
        recordOutcomingDto.setTag(tagsString);
        return recordOutcomingDto;
    }

    @Override
    public RecordOutcomingShortDto outComingShortRecordMap(RecordEntity recordEntity) {
        RecordOutcomingShortDto recordOutcomingShortDto = new RecordOutcomingShortDto();
        recordOutcomingShortDto.setId(recordEntity.getId().toString());
        recordOutcomingShortDto.setTitle(recordEntity.getTitle());
        recordOutcomingShortDto.setText(recordEntity.getText());
        recordOutcomingShortDto.setAuthorId(recordEntity.getAuthor().getId().toString());
        List<TagEntity> tags = recordEntity.getTag();
        List<String> tagsString = new ArrayList<>();
        tags.forEach(x -> tagsString.add(x.getName()));
        recordOutcomingShortDto.setTag(tagsString);
        return recordOutcomingShortDto;
    }

    @Override
    public RecordEntity incomingRecordMap(RecordIncomingDto recordIncomingDto) {
        RecordEntity recordEntity = new RecordEntity();
        recordEntity.setTitle(recordIncomingDto.getTitle());
        recordEntity.setText(recordIncomingDto.getText());
        List<TagEntity> tags = new ArrayList<>();
        if (recordIncomingDto.getTag() != null) {
            recordIncomingDto.getTag().forEach(x -> tags.add(new TagEntity(x)));
            recordEntity.setTag(tags);
        }
        return recordEntity;
    }
}
