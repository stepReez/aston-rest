package org.aston.task.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.service.RecordService;
import org.aston.task.servlet.dto.RecordIncomingDto;
import org.aston.task.servlet.dto.RecordOutcomingDto;
import org.aston.task.servlet.dto.UserShortDto;
import org.aston.task.servlet.mapper.RecordDtoMapper;
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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RecordServletTest {
    @Mock
    private RecordService recordService;

    @Mock
    private RecordDtoMapper recordDtoMapper;

    @InjectMocks
    private RecordServlet recordServlet;

    private final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mvc;

    private RecordIncomingDto recordIncomingDto;

    private RecordEntity recordEntity;

    private RecordOutcomingDto recordOutcomingDto;

    String id;

    String title;

    String text;

    String authorId;

    @BeforeEach
    void beforeEach() {
        mvc = MockMvcBuilders
                .standaloneSetup(recordServlet)
                .build();

        recordIncomingDto = new RecordIncomingDto();
        recordEntity = new RecordEntity();
        recordOutcomingDto = new RecordOutcomingDto();

        id = UUID.randomUUID().toString();
        title = "title";
        text = "text";
        authorId = UUID.randomUUID().toString();

        recordIncomingDto.setTitle(title);
        recordIncomingDto.setText(text);
        recordIncomingDto.setTag(new ArrayList<>());

        recordEntity.setId(UUID.fromString(id));
        recordEntity.setTitle(title);
        recordEntity.setText(text);
        recordEntity.setAuthor(new UserEntity());
        recordEntity.setTag(new ArrayList<>());

        recordOutcomingDto.setId(id);
        recordOutcomingDto.setTitle(title);
        recordOutcomingDto.setText(text);
        UserShortDto userShortDto = new UserShortDto();
        userShortDto.setId(authorId);
        recordOutcomingDto.setAuthor(userShortDto);
        recordOutcomingDto.setTag(new ArrayList<>());
    }

    @Test
    void createRecordTest() throws Exception {
        when(recordDtoMapper.incomingRecordMap(any()))
                .thenReturn(recordEntity);

        when(recordService.createRecord(any(), any(UUID.class)))
                .thenReturn(recordEntity);

        when(recordDtoMapper.outComingRecordMap(any()))
                .thenReturn(recordOutcomingDto);

        mvc.perform(post("/record?userId=" + authorId)
                        .content(mapper.writeValueAsString(recordIncomingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.title", is(title)))
                .andExpect(jsonPath("$.text", is(text)))
                .andExpect(jsonPath("$.author.id", is(authorId)));
    }

    @Test
    void getRecordByIdTest() throws Exception {
        when(recordService.findRecordById(any(UUID.class)))
                .thenReturn(recordEntity);

        when(recordDtoMapper.outComingRecordMap(any()))
                .thenReturn(recordOutcomingDto);

        mvc.perform(get("/record/" + id)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.title", is(title)))
                .andExpect(jsonPath("$.text", is(text)))
                .andExpect(jsonPath("$.author.id", is(authorId)));
    }

    @Test
    void getRecordByTagIdTest() throws Exception {
        List<RecordEntity> recordEntities = new ArrayList<>();
        recordEntities.add(recordEntity);
        when(recordService.findRecordsByTagId(anyInt()))
                .thenReturn(recordEntities);

        when(recordDtoMapper.outComingRecordMap(any()))
                .thenReturn(recordOutcomingDto);

        mvc.perform(get("/record/tag?tagId=" + 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(id)))
                .andExpect(jsonPath("$[0].title", is(title)))
                .andExpect(jsonPath("$[0].text", is(text)))
                .andExpect(jsonPath("$[0].author.id", is(authorId)));
    }

    @Test
    void getAllTest() throws Exception {
        List<RecordEntity> recordEntities = new ArrayList<>();
        recordEntities.add(recordEntity);
        when(recordService.findAll())
                .thenReturn(recordEntities);

        when(recordDtoMapper.outComingRecordMap(any()))
                .thenReturn(recordOutcomingDto);

        mvc.perform(get("/record")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(id)))
                .andExpect(jsonPath("$[0].title", is(title)))
                .andExpect(jsonPath("$[0].text", is(text)))
                .andExpect(jsonPath("$[0].author.id", is(authorId)));
    }

    @Test
    void updateRecordTest() throws Exception {
        when(recordDtoMapper.incomingRecordMap(any()))
                .thenReturn(recordEntity);

        when(recordService.updateRecord(any(), any(UUID.class)))
                .thenReturn(recordEntity);

        when(recordDtoMapper.outComingRecordMap(any()))
                .thenReturn(recordOutcomingDto);

        mvc.perform(put("/record?id=" + id)
                        .content(mapper.writeValueAsString(recordIncomingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.title", is(title)))
                .andExpect(jsonPath("$.text", is(text)))
                .andExpect(jsonPath("$.author.id", is(authorId)));
    }

    @Test
    void deleteRecordByIdTest() throws Exception {
        Mockito
                .doNothing()
                .when(recordService).deleteRecord(any(UUID.class));

        mvc.perform(delete("/record/" + id)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
