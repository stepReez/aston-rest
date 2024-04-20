package org.aston.task.servlet.mapper;

import org.aston.task.model.UserLikes;
import org.aston.task.servlet.dto.UserLikesDto;

public interface UserLikesDtoMapper {
    UserLikesDto map(UserLikes userLikes);
}
