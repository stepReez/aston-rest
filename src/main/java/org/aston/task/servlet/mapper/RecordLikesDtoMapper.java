package org.aston.task.servlet.mapper;

import org.aston.task.model.RecordLikes;
import org.aston.task.servlet.dto.RecordLikesDto;

public interface RecordLikesDtoMapper {
    RecordLikesDto map(RecordLikes recordLikes);
}
