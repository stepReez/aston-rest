package org.aston.task.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aston.task.exceptions.BadRequestException;
import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.service.LikeService;
import org.aston.task.servlet.dto.RecordOutcomingDto;
import org.aston.task.servlet.dto.UserOutcomingDto;
import org.aston.task.servlet.mapper.RecordDtoMapper;
import org.aston.task.servlet.mapper.UserDtoMapper;
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
        RecordDtoMapper recordDtoMapper = Mockito.mock(RecordDtoMapper.class);
        likeServlet.setRecordDtoMapper(recordDtoMapper);

        LikeService likeService = Mockito.mock(LikeService.class);
        likeServlet.setLikeService(likeService);

        UUID id = UUID.randomUUID();

        List<RecordEntity> recordEntities = new ArrayList<>();
        recordEntities.add(new RecordEntity());

        Mockito
                .when(likeService.getLikesByUser(id))
                .thenReturn(recordEntities);

        List<RecordOutcomingDto> outcomingDtos = new ArrayList<>();
        outcomingDtos.add(new RecordOutcomingDto());

        Mockito
                .when(recordDtoMapper.outComingRecordMap(Mockito.any()))
                .thenReturn(new RecordOutcomingDto());

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

        JsonElement jsonUser = gson.toJsonTree(outcomingDtos);
        String userString = gson.toJson(jsonUser);

        Assertions.assertEquals(userString, out.toString());
    }

    @Test
    void doGetByRecordTest() throws IOException, ServletException {
        UserDtoMapper userDtoMapper = Mockito.mock(UserDtoMapper.class);
        likeServlet.setUserDtoMapper(userDtoMapper);

        LikeService likeService = Mockito.mock(LikeService.class);
        likeServlet.setLikeService(likeService);

        UUID id = UUID.randomUUID();

        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(new UserEntity());

        Mockito
                .when(likeService.getLikesByRecord(id))
                .thenReturn(userEntities);

        List<UserOutcomingDto> outcomingDtos = new ArrayList<>();
        outcomingDtos.add(new UserOutcomingDto());

        Mockito
                .when(userDtoMapper.outComingUserMap(Mockito.any()))
                .thenReturn(new UserOutcomingDto());

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

        JsonElement jsonUser = gson.toJsonTree(outcomingDtos);
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
