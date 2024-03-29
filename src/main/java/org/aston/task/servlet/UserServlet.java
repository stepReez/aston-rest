package org.aston.task.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aston.task.model.UserEntity;
import org.aston.task.service.UserService;
import org.aston.task.servlet.dto.UserIncomingDto;
import org.aston.task.servlet.dto.UserOutcomingDto;
import org.aston.task.servlet.mapper.UserDtoMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet(name = "user", value = "/user")
public class UserServlet extends HttpServlet {

    private UserService userService;

    private UserDtoMapper userDtoMapper;

    private Gson gson;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String query = req.getQueryString();
        gson = new Gson();
        PrintWriter printWriter = resp.getWriter();
        if (query != null && query.contains("id=")) {
            UUID id = UUID.fromString(query.split("=")[1]);
            UserEntity userEntity = userService.findUserById(id);
            UserOutcomingDto userOutcomingDto = userDtoMapper.outComingUserMap(userEntity);

            JsonElement jsonUser = gson.toJsonTree(userOutcomingDto);
            String userString = gson.toJson(jsonUser);

            printWriter.write(userString);

        } else {
            List<UserEntity> userEntities = userService.findAll();
            List<UserOutcomingDto> users = userEntities.stream().map(userDtoMapper::outComingUserMap).toList();
            JsonElement jsonUsers = gson.toJsonTree(users);
            String usersString = gson.toJson(jsonUsers);
            printWriter.write(usersString);
        }
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String userString = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        gson = new Gson();
        UserIncomingDto userIncomingDto = gson.fromJson(userString, UserIncomingDto.class);
        UserEntity userEntity = userService.createUser(userDtoMapper.incomingUserMap(userIncomingDto));

        UserOutcomingDto userOutcomingDto = userDtoMapper.outComingUserMap(userEntity);
        JsonElement jsonUser = gson.toJsonTree(userOutcomingDto);
        String userOutString = gson.toJson(jsonUser);

        PrintWriter printWriter = resp.getWriter();
        printWriter.write(userOutString);
        printWriter.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String userString = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String query = req.getQueryString();
        if (query != null && query.contains("id=")) {
            UUID id = UUID.fromString(query.split("=")[1]);

            gson = new Gson();
            UserIncomingDto userIncomingDto = gson.fromJson(userString, UserIncomingDto.class);
            UserEntity userEntity = userService.updateUser(userDtoMapper.incomingUserMap(userIncomingDto), id);

            UserOutcomingDto userOutcomingDto = userDtoMapper.outComingUserMap(userEntity);
            JsonElement jsonUser = gson.toJsonTree(userOutcomingDto);
            String userOutString = gson.toJson(jsonUser);

            PrintWriter printWriter = resp.getWriter();
            printWriter.write(userOutString);
            printWriter.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String query = req.getQueryString();
        if (query != null && query.contains("id=")) {
            UUID id = UUID.fromString(query.split("=")[1]);
            boolean isDeleted = userService.deleteUser(id);
        }
    }
}
