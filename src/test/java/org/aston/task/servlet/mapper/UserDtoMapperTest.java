package org.aston.task.servlet.mapper;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.servlet.dto.UserIncomingDto;
import org.aston.task.servlet.dto.UserOutcomingDto;
import org.aston.task.servlet.dto.UserOutcomingShortDto;
import org.aston.task.servlet.mapper.impl.UserDtoMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class UserDtoMapperTest {

    UserDtoMapper userDtoMapper;

    @BeforeEach
    public void beforeEach() {
        userDtoMapper = new UserDtoMapperImpl();
    }

    @Test
    void userDtoMapper_incomingUserMapTest() {
        UserIncomingDto userIncomingDto = new UserIncomingDto();
        String userName = "user";
        userIncomingDto.setName(userName);
        UserEntity user = userDtoMapper.incomingUserMap(userIncomingDto);

        Assertions.assertEquals(userName, user.getName(), "Username must be equal " + userName);
    }

    @Test
    void userDtoMapper_outComingUserMapTest() {
        UserEntity userEntity = new UserEntity();
        UUID userId = UUID.randomUUID();
        userEntity.setId(userId);
        String userName = "user";
        userEntity.setName(userName);

        RecordEntity recordEntity1 = new RecordEntity();
        UUID recordId1 = UUID.randomUUID();
        recordEntity1.setId(recordId1);
        RecordEntity recordEntity2 = new RecordEntity();
        UUID recordId2 = UUID.randomUUID();
        recordEntity2.setId(recordId2);

        List<RecordEntity> usersRecords = new ArrayList<>();
        usersRecords.add(recordEntity1);

        List<UUID> likes = new ArrayList<>();
        likes.add(recordEntity1.getId());
        likes.add(recordEntity2.getId());

        userEntity.setRecords(usersRecords);

        UserOutcomingDto userOutcomingDto = userDtoMapper.outComingUserMap(userEntity);
        Assertions.assertEquals(userId.toString(), userOutcomingDto.getId(), "Id must be equal " + userId);
        Assertions.assertEquals(userName, userOutcomingDto.getName(), "Username must be equal " + userName);
        Assertions.assertEquals(1, userOutcomingDto.getRecordsId().size(), "Size must be equal " + 1);
        Assertions.assertEquals(recordId1.toString(), userOutcomingDto.getRecordsId().get(0),
                "Id must bew equal " + recordId1);
    }

    @Test
    void userDroMapper_OutComingUserShortMapTest() {
        UserEntity userEntity = new UserEntity();
        UUID userId = UUID.randomUUID();
        userEntity.setId(userId);
        String userName = "user";
        userEntity.setName(userName);

        RecordEntity recordEntity1 = new RecordEntity();
        UUID recordId1 = UUID.randomUUID();
        recordEntity1.setId(recordId1);
        RecordEntity recordEntity2 = new RecordEntity();
        UUID recordId2 = UUID.randomUUID();
        recordEntity2.setId(recordId2);

        UserOutcomingShortDto userOutcomingShortDto = userDtoMapper.outComingShortUserMap(userEntity);
        Assertions.assertEquals(userId.toString(), userOutcomingShortDto.getId(), "Id must be equal " + userId);
        Assertions.assertEquals(userName, userOutcomingShortDto.getName(), "Username must be equal " + userName);
    }
}
