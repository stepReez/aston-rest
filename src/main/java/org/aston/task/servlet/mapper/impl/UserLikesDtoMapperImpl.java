package org.aston.task.servlet.mapper.impl;

import org.aston.task.model.UserLikes;
import org.aston.task.servlet.dto.UserLikesDto;
import org.aston.task.servlet.mapper.UserLikesDtoMapper;

import java.util.UUID;

public class UserLikesDtoMapperImpl implements UserLikesDtoMapper {
    @Override
    public UserLikesDto map(UserLikes userLikes) {
        UserLikesDto userLikesDto = new UserLikesDto();
        userLikesDto.setUserLikes(userLikes.getUserLikes().stream().map(UUID::toString).toList());
        return userLikesDto;
    }
}
