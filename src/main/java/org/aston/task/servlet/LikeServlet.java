package org.aston.task.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aston.task.exceptions.BadRequestException;
import org.aston.task.model.RecordLikes;
import org.aston.task.model.UserLikes;
import org.aston.task.service.LikeService;
import org.aston.task.service.impl.LikeServiceImpl;
import org.aston.task.servlet.dto.RecordLikesDto;
import org.aston.task.servlet.dto.UserLikesDto;
import org.aston.task.servlet.mapper.RecordLikesDtoMapper;
import org.aston.task.servlet.mapper.UserLikesDtoMapper;
import org.aston.task.servlet.mapper.impl.RecordLikesDtoMapperImpl;
import org.aston.task.servlet.mapper.impl.UserLikesDtoMapperImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet(name = "likes", value = "/record/likes")
public class LikeServlet extends HttpServlet {
    
    private LikeService likeService;

    private RecordLikesDtoMapper recordLikesDtoMapper;

    private UserLikesDtoMapper userLikesDtoMapper;

    private final Gson gson;

    public LikeServlet() {
        likeService = new LikeServiceImpl();
        recordLikesDtoMapper = new RecordLikesDtoMapperImpl();
        userLikesDtoMapper = new UserLikesDtoMapperImpl();
        gson = new Gson();
    }

    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }

    public void setRecordLikesDtoMapper(RecordLikesDtoMapper recordLikesDtoMapper) {
        this.recordLikesDtoMapper = recordLikesDtoMapper;
    }

    public void setUserLikesDtoMapper(UserLikesDtoMapper userLikesDtoMapper) {
        this.userLikesDtoMapper = userLikesDtoMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String query = req.getQueryString();
        PrintWriter printWriter = resp.getWriter();
        if (query != null && req.getParameter("userId") != null) {
            UUID id = UUID.fromString(req.getParameter("userId"));
            UserLikes likesByUser = likeService.getLikesByUser(id);
            UserLikesDto recordsThatLikes = userLikesDtoMapper.map(likesByUser);
            JsonElement jsonRecord = gson.toJsonTree(recordsThatLikes);
            String recordString = gson.toJson(jsonRecord);
            printWriter.write(recordString);

        } else if (query != null && req.getParameter("recordId") != null) {
            UUID id = UUID.fromString(req.getParameter("recordId"));
            RecordLikes likesByRecord = likeService.getLikesByRecord(id);
            RecordLikesDto usersThatLikes = recordLikesDtoMapper.map(likesByRecord);
            JsonElement jsonUsers = gson.toJsonTree(usersThatLikes);
            String usersString = gson.toJson(jsonUsers);
            printWriter.write(usersString);
        } else {
            throw new BadRequestException("Bad request: " + query);
        }
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String query = req.getQueryString();

        if (query != null && req.getParameter("userId") != null && req.getParameter("recordId") != null) {
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

        if (query != null && req.getParameter("userId") != null && req.getParameter("recordId") != null) {
            UUID recordId = UUID.fromString(req.getParameter("recordId"));
            UUID userId = UUID.fromString(req.getParameter("userId"));
            likeService.removeLike(recordId, userId);
        } else {
            throw new BadRequestException("Bad request: " + query);
        }
    }
}
