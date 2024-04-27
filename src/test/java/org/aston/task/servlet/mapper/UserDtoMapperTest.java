package org.aston.task.servlet.mapper;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.servlet.dto.RecordShortDto;
import org.aston.task.servlet.dto.UserIncomingDto;
import org.aston.task.servlet.dto.UserOutcomingDto;
import org.aston.task.servlet.dto.UserShortDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class UserDtoMapperTest {

    ApplicationContext context = new AnnotationConfigApplicationContext(RecordDtoMapperImpl.class,
            UserDtoMapperImpl.class,
            TagDtoMapperImpl.class);

    UserDtoMapperImpl userDtoMapper;

    @BeforeEach
    public void beforeEach() {
        userDtoMapper = context.getBean(UserDtoMapperImpl.class);
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

        List<RecordEntity> usersRecords = new ArrayList<>();
        usersRecords.add(recordEntity1);

        userEntity.setRecords(usersRecords);

        UserOutcomingDto userOutcomingDto = userDtoMapper.outComingUserMap(userEntity);
        Assertions.assertEquals(userId.toString(), userOutcomingDto.getId(), "Id must be equal " + userId);
        Assertions.assertEquals(userName, userOutcomingDto.getName(), "Username must be equal " + userName);
        Assertions.assertEquals(1, userOutcomingDto.getRecords().size(), "Size must be equal " + 1);
        Assertions.assertEquals(recordId1.toString(), userOutcomingDto.getRecords().get(0).getId(),
                "Id must bew equal " + recordId1);
    }

    @Test
    void userDtoMapper_outComingNullUserMap() {
        UserOutcomingDto userOutcomingDto = userDtoMapper.outComingUserMap(null);
        Assertions.assertNull(userOutcomingDto);
    }

    @Test
    void userDtoMapper_incomingNullUserMap() {
        UserEntity user = userDtoMapper.incomingUserMap(null);
        Assertions.assertNull(user);
    }

    @Test
    void userDtoMapper_shortOutComingDtoMapNullTest() {
        UserShortDto userShortDto = userDtoMapper.shortOutComingDtoMap(null);
        Assertions.assertNull(userShortDto);
    }

    @Test
    void userDtoMapper_recordEntityListToRecordShortDtoListNullTest() {
        List<RecordShortDto> list = userDtoMapper.recordEntityListToRecordShortDtoList(null);
        Assertions.assertNull(list);
    }
}
