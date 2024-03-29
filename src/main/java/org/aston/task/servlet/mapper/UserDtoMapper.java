package org.aston.task.servlet.mapper;

import org.aston.task.model.UserEntity;
import org.aston.task.servlet.dto.UserIncomingDto;
import org.aston.task.servlet.dto.UserOutcomingDto;

public interface UserDtoMapper {
    UserOutcomingDto outComingUserMap(UserEntity userEntity);

    UserEntity incomingUserMap(UserIncomingDto userIncomingDto);
}
