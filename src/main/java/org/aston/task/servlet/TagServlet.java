package org.aston.task.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aston.task.exceptions.BadRequestException;
import org.aston.task.model.TagEntity;
import org.aston.task.service.TagService;
import org.aston.task.service.impl.TagServiceImpl;
import org.aston.task.servlet.dto.TagInomingDto;
import org.aston.task.servlet.dto.TagOutcomingDto;
import org.aston.task.servlet.mapper.TagDtoMapper;
import org.aston.task.servlet.mapper.impl.TagDtoMapperImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "tag", value = "/tag")
public class TagServlet extends HttpServlet {

    private TagService tagService;

    private TagDtoMapper tagDtoMapper;

    private final Gson gson;

    public TagServlet() {
        tagService = new TagServiceImpl();
        tagDtoMapper = new TagDtoMapperImpl();
        gson = new Gson();
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    public void setTagDtoMapper(TagDtoMapper tagDtoMapper) {
        this.tagDtoMapper = tagDtoMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();
        List<TagEntity> tagEntities = tagService.getAllTags();
        List<TagOutcomingDto> tagOutcomingDtoList = tagEntities.stream().map(tagDtoMapper::outcomingMap).toList();

        JsonElement tags = gson.toJsonTree(tagOutcomingDtoList);
        String tagString = gson.toJson(tags);

        printWriter.write(tagString);
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        String tagString = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        TagInomingDto tagInomingDto = gson.fromJson(tagString, TagInomingDto.class);
        TagEntity tag = tagDtoMapper.incomingMap(tagInomingDto);
        tagService.createTag(tag);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        String query = req.getQueryString();
        if (query != null && req.getParameter("id") != null) {
            int id = Integer.parseInt(req.getParameter("id"));
            tagService.removeTag(id);
        } else {
            throw new BadRequestException("Bad request: " + query);
        }
    }
}
