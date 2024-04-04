package org.aston.task.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aston.task.exceptions.BadRequestException;
import org.aston.task.model.UserEntity;
import org.aston.task.service.UserService;
import org.aston.task.service.impl.UserServiceImpl;
import org.aston.task.servlet.dto.UserIncomingDto;
import org.aston.task.servlet.dto.UserOutcomingDto;
import org.aston.task.servlet.mapper.UserDtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class UserServletTest {

    UserServlet userServlet = new UserServlet();

    Gson gson = new Gson();

    @Test
    void doGetUserByIdTest() throws IOException, ServletException {
        UserService userService = Mockito.mock(UserServiceImpl.class);
        userServlet.setUserService(userService);

        UserDtoMapper userDtoMapper = Mockito.mock(UserDtoMapper.class);
        userServlet.setUserDtoMapper(userDtoMapper);

        UserEntity user = new UserEntity();
        UUID id = UUID.randomUUID();
        user.setId(id);

        Mockito
                .when(userService.findUserById(id))
                .thenReturn(user);

        UserOutcomingDto userOutcomingDto = new UserOutcomingDto();
        userOutcomingDto.setId(id.toString());

        Mockito
                .when(userDtoMapper.outComingUserMap(user))
                .thenReturn(userOutcomingDto);

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito
                .when(req.getQueryString())
                .thenReturn("id=" + id);

        Mockito
                .when(req.getParameter("id"))
                .thenReturn(id.toString());

        StringWriter out = new StringWriter();
        PrintWriter writer = new PrintWriter(out);

        Mockito
                .when(resp.getWriter())
                .thenReturn(new PrintWriter(writer));

        userServlet.doGet(req, resp);

        JsonElement jsonUser = gson.toJsonTree(userOutcomingDto);
        String userString = gson.toJson(jsonUser);

        Assertions.assertEquals(userString, out.toString());
    }

    @Test
    void doGetAllUserTest() throws IOException, ServletException {
        UserService userService = Mockito.mock(UserServiceImpl.class);
        userServlet.setUserService(userService);

        UserDtoMapper userDtoMapper = Mockito.mock(UserDtoMapper.class);
        userServlet.setUserDtoMapper(userDtoMapper);

        UserEntity user = new UserEntity();
        UUID id = UUID.randomUUID();
        user.setId(id);

        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(user);

        Mockito
                .when(userService.findAll())
                .thenReturn(userEntities);

        UserOutcomingDto userOutcomingDto = new UserOutcomingDto();
        userOutcomingDto.setId(id.toString());

        List<UserOutcomingDto> userOutcomingDtos = new ArrayList<>();
        userOutcomingDtos.add(userOutcomingDto);

        Mockito
                .when(userDtoMapper.outComingUserMap(user))
                .thenReturn(userOutcomingDto);

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito
                .when(req.getQueryString())
                .thenReturn(null);

        StringWriter out = new StringWriter();
        PrintWriter writer = new PrintWriter(out);

        Mockito
                .when(resp.getWriter())
                .thenReturn(new PrintWriter(writer));

        userServlet.doGet(req, resp);

        JsonElement jsonUser = gson.toJsonTree(userOutcomingDtos);
        String userString = gson.toJson(jsonUser);

        Assertions.assertEquals(userString, out.toString());
    }

    @Test
    void doPutUserByIdTest() throws IOException, ServletException {
        UserService userService = Mockito.mock(UserServiceImpl.class);
        userServlet.setUserService(userService);

        UserDtoMapper userDtoMapper = Mockito.mock(UserDtoMapper.class);
        userServlet.setUserDtoMapper(userDtoMapper);

        UserEntity user = new UserEntity();
        UUID id = UUID.randomUUID();
        String name = "name";
        user.setId(id);
        user.setUserName(name);

        Mockito
                .when(userService.updateUser(user, id))
                .thenReturn(user);

        UserOutcomingDto userOutcomingDto = new UserOutcomingDto();
        userOutcomingDto.setId(id.toString());
        userOutcomingDto.setUserName(name);

        Mockito
                .when(userDtoMapper.outComingUserMap(Mockito.any()))
                .thenReturn(userOutcomingDto);

        UserIncomingDto userIncomingDto = new UserIncomingDto();
        userIncomingDto.setUserName(name);

        Mockito
                .when(userDtoMapper.incomingUserMap(userIncomingDto))
                .thenReturn(user);

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito
                .when(req.getQueryString())
                .thenReturn("id=" + id);

        Mockito
                .when(req.getParameter("id"))
                .thenReturn(id.toString());

        JsonElement jsonIncomingUser = gson.toJsonTree(userIncomingDto);
        String userIncomingString = gson.toJson(jsonIncomingUser);
        Reader inputString = new StringReader(userIncomingString);

        Mockito
                .when(req.getReader())
                .thenReturn(new BufferedReader(new BufferedReader(inputString)));


        StringWriter out = new StringWriter();
        PrintWriter writer = new PrintWriter(out);

        Mockito
                .when(resp.getWriter())
                .thenReturn(new PrintWriter(writer));

        userServlet.doPut(req, resp);

        JsonElement jsonUser = gson.toJsonTree(userOutcomingDto);
        String userString = gson.toJson(jsonUser);

        Assertions.assertEquals(userString, out.toString());
    }

    @Test
    void doPostUserByIdTest() throws IOException, ServletException {
        UserService userService = Mockito.mock(UserServiceImpl.class);
        userServlet.setUserService(userService);

        UserDtoMapper userDtoMapper = Mockito.mock(UserDtoMapper.class);
        userServlet.setUserDtoMapper(userDtoMapper);

        UserEntity user = new UserEntity();
        UUID id = UUID.randomUUID();
        String name = "name";
        user.setId(id);
        user.setUserName(name);

        Mockito
                .when(userService.updateUser(user, id))
                .thenReturn(user);

        UserOutcomingDto userOutcomingDto = new UserOutcomingDto();
        userOutcomingDto.setId(id.toString());
        userOutcomingDto.setUserName(name);

        Mockito
                .when(userDtoMapper.outComingUserMap(Mockito.any()))
                .thenReturn(userOutcomingDto);

        UserIncomingDto userIncomingDto = new UserIncomingDto();
        userIncomingDto.setUserName(name);

        Mockito
                .when(userDtoMapper.incomingUserMap(userIncomingDto))
                .thenReturn(user);

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        JsonElement jsonIncomingUser = gson.toJsonTree(userIncomingDto);
        String userIncomingString = gson.toJson(jsonIncomingUser);
        Reader inputString = new StringReader(userIncomingString);

        Mockito
                .when(req.getReader())
                .thenReturn(new BufferedReader(new BufferedReader(inputString)));


        StringWriter out = new StringWriter();
        PrintWriter writer = new PrintWriter(out);

        Mockito
                .when(resp.getWriter())
                .thenReturn(new PrintWriter(writer));

        userServlet.doPost(req, resp);

        JsonElement jsonUser = gson.toJsonTree(userOutcomingDto);
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

        Assertions.assertThrows(BadRequestException.class, () -> userServlet.doGet(req, resp));
    }

    @Test
    void doPutUserBadRequestTest() {

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito
                .when(req.getQueryString())
                .thenReturn("not=" + "name");

        Assertions.assertThrows(BadRequestException.class, () -> userServlet.doPut(req, resp));
    }

    @Test
    void doDeleteUserBadRequestTest() {


        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito
                .when(req.getQueryString())
                .thenReturn("not=" + "name");

        Assertions.assertThrows(BadRequestException.class, () -> userServlet.doDelete(req, resp));
    }


}
