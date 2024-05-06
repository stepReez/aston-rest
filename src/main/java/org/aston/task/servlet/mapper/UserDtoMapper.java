package org.aston.task.servlet.mapper;

import org.aston.task.model.UserEntity;
import org.aston.task.servlet.dto.RecordShortDto;
import org.aston.task.servlet.dto.UserIncomingDto;
import org.aston.task.servlet.dto.UserOutcomingDto;
import org.aston.task.servlet.dto.UserShortDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = RecordDtoMapper.class)
public interface UserDtoMapper {

    UserOutcomingDto outComingUserMap(UserEntity userEntity);

    UserEntity incomingUserMap(UserIncomingDto userIncomingDto);

    UserShortDto shortOutComingDtoMap(UserEntity userEntity);

    default String map(UUID value) {
        return value.toString();
    }
}
