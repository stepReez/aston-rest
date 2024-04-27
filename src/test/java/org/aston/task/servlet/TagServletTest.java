package org.aston.task.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aston.task.model.TagEntity;
import org.aston.task.service.TagService;
import org.aston.task.servlet.dto.TagInomingDto;
import org.aston.task.servlet.dto.TagOutcomingDto;
import org.aston.task.servlet.mapper.TagDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TagServletTest {
    @Mock
    private TagService tagService;
    @Mock
    private TagDtoMapper tagDtoMapper;

    @InjectMocks
    private TagServlet tagServlet;

    private final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mvc;

    private TagInomingDto tagInomingDto;

    private TagOutcomingDto tagOutcomingDto;

    private TagEntity tagEntity;

    private int id;

    private String name;

    @BeforeEach
    void beforeEach() {
        mvc = MockMvcBuilders
                .standaloneSetup(tagServlet)
                .build();

        tagInomingDto = new TagInomingDto();
        tagEntity = new TagEntity();
        tagOutcomingDto = new TagOutcomingDto();

        id = 1;
        tagOutcomingDto.setId(id);
        tagEntity.setId(id);

        name = "name";
        tagInomingDto.setName(name);
        tagEntity.setName(name);
        tagOutcomingDto.setName(name);
    }

    @Test
    void createTagTest() throws Exception {
        when(tagDtoMapper.incomingMap(any()))
                .thenReturn(tagEntity);

        when(tagService.createTag(any()))
                .thenReturn(tagEntity);

        when(tagDtoMapper.outcomingMap(any()))
                .thenReturn(tagOutcomingDto);

        mvc.perform(post("/tag")
                        .content(mapper.writeValueAsString(tagInomingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is(name)));
    }

    @Test
    void getTagByIdTest() throws Exception {
        Mockito
                .when(tagService.findTagById(id))
                .thenReturn(tagEntity);

        Mockito
                .when(tagDtoMapper.outcomingMap(tagEntity))
                .thenReturn(tagOutcomingDto);

        mvc.perform(get("/tag/" + id)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is(name)));
    }

    @Test
    void deleteTagByIdTest() throws Exception {
        Mockito
                .doNothing()
                .when(tagService).removeTag(id);

        mvc.perform(delete("/tag/" + id)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllTagsTest() throws Exception {
        List<TagEntity> tagEntities = new ArrayList<>();
        tagEntities.add(tagEntity);

        Mockito
                .when(tagService.getAllTags())
                .thenReturn(tagEntities);

        Mockito
                .when(tagDtoMapper.outcomingMap(tagEntity))
                .thenReturn(tagOutcomingDto);

        mvc.perform(get("/tag")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(id)))
                .andExpect(jsonPath("$[0].name", is(name)));
    }
}
