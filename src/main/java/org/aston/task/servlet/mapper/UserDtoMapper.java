package org.aston.task.servlet.mapper;

import org.aston.task.model.UserEntity;
import org.aston.task.servlet.dto.UserIncomingDto;
import org.aston.task.servlet.dto.UserOutcomingDto;
import org.aston.task.servlet.dto.UserOutcomingShortDto;

public interface UserDtoMapper {
    UserOutcomingDto outComingUserMap(UserEntity userEntity);

    UserOutcomingShortDto outComingShortUserMap(UserEntity userEntity);

    UserEntity incomingUserMap(UserIncomingDto userIncomingDto);
}
