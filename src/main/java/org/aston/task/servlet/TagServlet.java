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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tag")
public class TagServlet {

    private TagService tagService;

    private TagDtoMapper tagDtoMapper;

    @Autowired
    public TagServlet(TagService tagService, TagDtoMapper tagDtoMapper) {
        this.tagService = tagService;
        this.tagDtoMapper = tagDtoMapper;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    public void setTagDtoMapper(TagDtoMapper tagDtoMapper) {
        this.tagDtoMapper = tagDtoMapper;
    }

    @GetMapping
    public List<TagOutcomingDto> getAll() {
        List<TagEntity> tagEntities = tagService.getAllTags();
        return tagEntities.stream().map(tagDtoMapper::outcomingMap).toList();
    }

    @PostMapping
    public TagOutcomingDto doPost(@RequestBody TagInomingDto tagInomingDto) {
        TagEntity tag = tagDtoMapper.incomingMap(tagInomingDto);
        return tagDtoMapper.outcomingMap(tagService.createTag(tag));
    }

    @DeleteMapping("/{id}")
    public void doDelete(@PathVariable("id") int id) {
        tagService.removeTag(id);
    }

    @GetMapping("/{id}")
    public TagOutcomingDto getTagById(@PathVariable("id") int id) {
        return tagDtoMapper.outcomingMap(tagService.findTagById(id));
    }
}
