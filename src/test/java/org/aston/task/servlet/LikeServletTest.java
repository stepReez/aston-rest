package org.aston.task.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aston.task.exceptions.BadRequestException;
import org.aston.task.model.RecordEntity;
import org.aston.task.model.RecordLikes;
import org.aston.task.model.UserEntity;
import org.aston.task.model.UserLikes;
import org.aston.task.service.LikeService;
import org.aston.task.servlet.dto.RecordLikesDto;
import org.aston.task.servlet.dto.RecordOutcomingShortDto;
import org.aston.task.servlet.dto.UserLikesDto;
import org.aston.task.servlet.dto.UserOutcomingShortDto;
import org.aston.task.servlet.mapper.RecordDtoMapper;
import org.aston.task.servlet.mapper.RecordLikesDtoMapper;
import org.aston.task.servlet.mapper.UserDtoMapper;
import org.aston.task.servlet.mapper.UserLikesDtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class LikeServletTest {

    LikeServlet likeServlet = new LikeServlet();

    Gson gson = new Gson();

    @Test
    void doGetByUserTest() throws IOException, ServletException {
        UserLikesDtoMapper userLikesDtoMapper = Mockito.mock(UserLikesDtoMapper.class);
        likeServlet.setUserLikesDtoMapper(userLikesDtoMapper);

        LikeService likeService = Mockito.mock(LikeService.class);
        likeServlet.setLikeService(likeService);

        UUID id = UUID.randomUUID();

        UserLikes userLikes = new UserLikes();
        List<UUID> uuids = new ArrayList<>();
        uuids.add(UUID.randomUUID());
        userLikes.setUserLikes(uuids);

        Mockito
                .when(likeService.getLikesByUser(id))
                .thenReturn(userLikes);

        UserLikesDto userLikesDto = new UserLikesDto();

        Mockito
                .when(userLikesDtoMapper.map(Mockito.any()))
                .thenReturn(new UserLikesDto());

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito
                .when(req.getQueryString())
                .thenReturn("userId=" + id);

        Mockito
                .when(req.getParameter("userId"))
                .thenReturn(id.toString());

        StringWriter out = new StringWriter();
        PrintWriter writer = new PrintWriter(out);

        Mockito
                .when(resp.getWriter())
                .thenReturn(new PrintWriter(writer));

        likeServlet.doGet(req, resp);

        JsonElement jsonUser = gson.toJsonTree(userLikesDto);
        String userString = gson.toJson(jsonUser);

        Assertions.assertEquals(userString, out.toString());
    }

    @Test
    void doGetByRecordTest() throws IOException, ServletException {
        RecordLikesDtoMapper recordLikesDtoMapper = Mockito.mock(RecordLikesDtoMapper.class);
        likeServlet.setRecordLikesDtoMapper(recordLikesDtoMapper);

        LikeService likeService = Mockito.mock(LikeService.class);
        likeServlet.setLikeService(likeService);

        UUID id = UUID.randomUUID();

        RecordLikes recordLikes = new RecordLikes();
        List<UUID> uuids = new ArrayList<>();
        uuids.add(UUID.randomUUID());
        recordLikes.setRecordLikes(uuids);

        Mockito
                .when(likeService.getLikesByRecord(id))
                .thenReturn(recordLikes);

        RecordLikesDto recordLikesDto = new RecordLikesDto();

        Mockito
                .when(recordLikesDtoMapper.map(Mockito.any()))
                .thenReturn(new RecordLikesDto());

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito
                .when(req.getQueryString())
                .thenReturn("recordId=" + id);

        Mockito
                .when(req.getParameter("recordId"))
                .thenReturn(id.toString());

        StringWriter out = new StringWriter();
        PrintWriter writer = new PrintWriter(out);

        Mockito
                .when(resp.getWriter())
                .thenReturn(new PrintWriter(writer));

        likeServlet.doGet(req, resp);

        JsonElement jsonUser = gson.toJsonTree(recordLikesDto);
        String userString = gson.toJson(jsonUser);

        Assertions.assertEquals(userString, out.toString());
    }

    @Test
    void doGetUserBadRequestTest() {


        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito
                .when(req.getQueryString())
                .thenReturn("not=" + "name");

        Assertions.assertThrows(BadRequestException.class, () -> likeServlet.doGet(req, resp));
    }

    @Test
    void doPostUserBadRequestTest() {

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito
                .when(req.getQueryString())
                .thenReturn("not=" + "name");

        Assertions.assertThrows(BadRequestException.class, () -> likeServlet.doPost(req, resp));
    }

    @Test
    void doDeleteUserBadRequestTest() {


        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito
                .when(req.getQueryString())
                .thenReturn("not=" + "name");

        Assertions.assertThrows(BadRequestException.class, () -> likeServlet.doDelete(req, resp));
    }
}
