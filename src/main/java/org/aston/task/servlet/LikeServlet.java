package org.aston.task.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aston.task.exceptions.BadRequestException;
import org.aston.task.exceptions.NotFoundException;
import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.service.LikeService;
import org.aston.task.service.impl.LikeServiceImpl;
import org.aston.task.servlet.dto.RecordOutcomingDto;
import org.aston.task.servlet.dto.UserOutcomingDto;
import org.aston.task.servlet.mapper.RecordDtoMapper;
import org.aston.task.servlet.mapper.UserDtoMapper;
import org.aston.task.servlet.mapper.impl.RecordDtoMapperImpl;
import org.aston.task.servlet.mapper.impl.UserDtoMapperImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@WebServlet(name = "likes", value = "/record/likes")
public class LikeServlet extends HttpServlet {
    
    private LikeService likeService;

    private RecordDtoMapper recordDtoMapper;

    private UserDtoMapper userDtoMapper;

    private final Gson gson;

    public LikeServlet() {
        likeService = new LikeServiceImpl();
        recordDtoMapper = new RecordDtoMapperImpl();
        userDtoMapper = new UserDtoMapperImpl();
        gson = new Gson();
    }

    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }

    public void setRecordDtoMapper(RecordDtoMapper recordDtoMapper) {
        this.recordDtoMapper = recordDtoMapper;
    }

    public void setUserDtoMapper(UserDtoMapper userDtoMapper) {
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String query = req.getQueryString();
        PrintWriter printWriter = resp.getWriter();
        if (query != null && Pattern.matches("^userId=.+$", query)) {
            UUID id = UUID.fromString(req.getParameter("userId"));
            List<RecordEntity> recordEntities = likeService.getLikesByUser(id);
            List<RecordOutcomingDto> records = recordEntities.stream().map(recordDtoMapper::outComingRecordMap).toList();
            JsonElement jsonRecord = gson.toJsonTree(records);
            String recordString = gson.toJson(jsonRecord);
            printWriter.write(recordString);

        } else if (query != null && Pattern.matches("^recordId=.+$", query)) {
            UUID id = UUID.fromString(req.getParameter("recordId"));
            List<UserEntity> userEntities = likeService.getLikesByRecord(id);
            List<UserOutcomingDto> users = userEntities.stream().map(userDtoMapper::outComingUserMap).toList();
            JsonElement jsonUsers = gson.toJsonTree(users);
            String usersString = gson.toJson(jsonUsers);
            printWriter.write(usersString);
        } else {
            throw new BadRequestException("Bad request: " + query);
        }
        printWriter.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String query = req.getQueryString();

        if (query != null && Pattern.matches("^recordId=.+&userId=.+$", query)) {
            UUID recordId = UUID.fromString(req.getParameter("recordId"));
            UUID userId = UUID.fromString(req.getParameter("userId"));
            likeService.addLike(recordId, userId);
        } else {
            throw new BadRequestException("Bad request: " + query);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String query = req.getQueryString();

        if (query != null && Pattern.matches("^recordId=.+&userId=.+$", query)) {
            UUID recordId = UUID.fromString(req.getParameter("recordId"));
            UUID userId = UUID.fromString(req.getParameter("userId"));
            likeService.removeLike(recordId, userId);
        } else {
            throw new BadRequestException("Bad request: " + query);
        }
    }
}
