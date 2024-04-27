package org.aston.task.servlet.mapper;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.TagEntity;
import org.aston.task.servlet.dto.RecordIncomingDto;
import org.aston.task.servlet.dto.RecordOutcomingDto;
import org.aston.task.servlet.dto.RecordShortDto;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {UserDtoMapper.class, TagDtoMapper.class})
public interface RecordDtoMapper {

    RecordOutcomingDto outComingRecordMap(RecordEntity recordEntity);

    RecordEntity incomingRecordMap(RecordIncomingDto recordIncomingDto);

    RecordShortDto shortOutComingMap(RecordEntity recordEntity);

    default List<TagEntity> map(List<Integer> value) {
        List<TagEntity> tags = new ArrayList<>();
        if (value != null) {
            value.forEach(x -> tags.add(new TagEntity(x)));
        }
        return tags;
    }
}
