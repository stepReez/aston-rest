package org.aston.task.servlet.mapper;

import org.aston.task.model.RecordLikes;
import org.aston.task.servlet.dto.RecordLikesDto;
import org.aston.task.servlet.mapper.impl.RecordLikesDtoMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecordLikesDtoMapperTest {

    RecordLikesDtoMapper recordLikesDtoMapper;

    @BeforeEach
    void beforeEach() {
        recordLikesDtoMapper = new RecordLikesDtoMapperImpl();
    }

    @Test
    void mapTest() {
        RecordLikes recordLikes = new RecordLikes();
        UUID uuid = UUID.randomUUID();
        List<UUID> uuids = new ArrayList<>();
        uuids.add(uuid);
        recordLikes.setRecordLikes(uuids);
        RecordLikesDto recordLikesDto = recordLikesDtoMapper.map(recordLikes);

        Assertions.assertEquals(1, recordLikesDto.getRecordLikes().size());
        Assertions.assertEquals(uuid.toString(), recordLikesDto.getRecordLikes().get(0));
    }
}
