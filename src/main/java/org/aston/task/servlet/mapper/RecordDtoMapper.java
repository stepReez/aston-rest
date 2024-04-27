package org.aston.task.servlet.mapper;

import org.aston.task.model.RecordEntity;
import org.aston.task.servlet.dto.RecordIncomingDto;
import org.aston.task.servlet.dto.RecordOutcomingDto;

public interface RecordDtoMapper {
    RecordOutcomingDto outComingRecordMap(RecordEntity recordEntity);

    RecordEntity incomingRecordMap(RecordIncomingDto recordIncomingDto);
}
