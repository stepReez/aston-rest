package org.aston.task.servlet.mapper.impl;

import org.aston.task.model.RecordEntity;
import org.aston.task.servlet.dto.RecordIncomingDto;
import org.aston.task.servlet.dto.RecordOutcomingDto;
import org.aston.task.servlet.mapper.RecordDtoMapper;

public class RecordDtoMapperImpl implements RecordDtoMapper {
    @Override
    public RecordOutcomingDto outComingRecordMap(RecordEntity recordEntity) {
        return null;
    }

    @Override
    public RecordEntity incomingRecordMap(RecordIncomingDto recordIncomingDto) {
        return null;
    }
}
