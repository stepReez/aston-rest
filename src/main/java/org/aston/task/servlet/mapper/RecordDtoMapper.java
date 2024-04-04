package org.aston.task.servlet.mapper;

import org.aston.task.model.RecordEntity;
import org.aston.task.servlet.dto.RecordIncomingDto;
import org.aston.task.servlet.dto.RecordOutcomingDto;
import org.aston.task.servlet.dto.RecordOutcomingShortDto;

public interface RecordDtoMapper {
    RecordOutcomingDto outComingRecordMap(RecordEntity recordEntity);

    RecordOutcomingShortDto outComingShortRecordMap(RecordEntity recordEntity);

    RecordEntity incomingRecordMap(RecordIncomingDto recordIncomingDto);
}
