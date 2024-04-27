package org.aston.task.servlet.mapper.impl;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.servlet.dto.UserIncomingDto;
import org.aston.task.servlet.dto.UserOutcomingDto;
import org.aston.task.servlet.mapper.UserDtoMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserDtoMapperImpl implements UserDtoMapper {
    @Override
    public UserOutcomingDto outComingUserMap(UserEntity userEntity) {
        UserOutcomingDto userOutcomingDto = new UserOutcomingDto();
        userOutcomingDto.setId(userEntity.getId().toString());
        userOutcomingDto.setName(userEntity.getName());

        List<String> records = userEntity.getRecords().stream().map(RecordEntity::getId).map(UUID::toString).toList();
        userOutcomingDto.setRecordsId(records);

        return userOutcomingDto;
    }

    @Override
    public UserEntity incomingUserMap(UserIncomingDto userIncomingDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userIncomingDto.getName());
        return userEntity;
    }
}
