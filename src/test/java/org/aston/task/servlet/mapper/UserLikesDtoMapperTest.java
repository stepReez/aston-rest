package org.aston.task.servlet.mapper;

import org.aston.task.model.UserLikes;
import org.aston.task.servlet.dto.UserLikesDto;
import org.aston.task.servlet.mapper.impl.UserLikesDtoMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserLikesDtoMapperTest {

    UserLikesDtoMapper userLikesDtoMapper;

    @BeforeEach
    void beforeEach() {
        userLikesDtoMapper = new UserLikesDtoMapperImpl();
    }

    @Test
    void mapTest() {
        UserLikes userLikes = new UserLikes();
        UUID uuid = UUID.randomUUID();
        List<UUID> uuids = new ArrayList<>();
        uuids.add(uuid);
        userLikes.setUserLikes(uuids);

        UserLikesDto userLikesDto = userLikesDtoMapper.map(userLikes);

        Assertions.assertEquals(1, userLikesDto.getUserLikes().size());
        Assertions.assertEquals(uuid.toString(), userLikesDto.getUserLikes().get(0));
    }
}
