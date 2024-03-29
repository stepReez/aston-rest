package org.aston.task.servlet.mapper.impl;

import org.aston.task.model.UserEntity;
import org.aston.task.servlet.dto.UserIncomingDto;
import org.aston.task.servlet.dto.UserOutcomingDto;
import org.aston.task.servlet.mapper.UserDtoMapper;

public class UserDtoMapperImpl implements UserDtoMapper {
    @Override
    public UserOutcomingDto outComingUserMap(UserEntity userEntity) {
        return null;
    }

    @Override
    public UserEntity incomingUserMap(UserIncomingDto userIncomingDto) {
        return null;
    }
}
