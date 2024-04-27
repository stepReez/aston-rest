package org.aston.task.servlet;

import org.aston.task.model.TagEntity;
import org.aston.task.service.TagService;
import org.aston.task.servlet.dto.TagInomingDto;
import org.aston.task.servlet.dto.TagOutcomingDto;
import org.aston.task.servlet.mapper.TagDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<TagOutcomingDto> getAll() {
        List<TagEntity> tagEntities = tagService.getAllTags();
        return tagEntities.stream().map(tagDtoMapper::outcomingMap).toList();
    }

    @PostMapping
    public TagOutcomingDto createTag(@RequestBody TagInomingDto tagInomingDto) {
        TagEntity tag = tagDtoMapper.incomingMap(tagInomingDto);
        return tagDtoMapper.outcomingMap(tagService.createTag(tag));
    }

    @DeleteMapping("/{id}")
    public void deleteTagById(@PathVariable("id") int id) {
        tagService.removeTag(id);
    }

    @GetMapping("/{id}")
    public TagOutcomingDto getTagById(@PathVariable("id") int id) {
        return tagDtoMapper.outcomingMap(tagService.findTagById(id));
    }
}
