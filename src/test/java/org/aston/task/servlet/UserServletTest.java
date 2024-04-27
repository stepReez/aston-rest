package org.aston.task.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aston.task.model.UserEntity;
import org.aston.task.service.UserService;
import org.aston.task.servlet.dto.UserIncomingDto;
import org.aston.task.servlet.dto.UserOutcomingDto;
import org.aston.task.servlet.mapper.UserDtoMapper;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserServletTest {

    @Mock
    private UserService userService;

    @Mock
    private UserDtoMapper userDtoMapper;

    @InjectMocks
    private UserServlet userServlet;

    private final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mvc;

    private UserIncomingDto userIncomingDto;

    private UserOutcomingDto userOutcomingDto;

    private UserEntity userEntity;

    private String id;

    private String name;

    @BeforeEach
    void beforeEach() {
        mvc = MockMvcBuilders
                .standaloneSetup(userServlet)
                .build();

        id = UUID.randomUUID().toString();
        name = "name";

        userIncomingDto = new UserIncomingDto();
        userEntity = new UserEntity();
        userOutcomingDto = new UserOutcomingDto();

        userIncomingDto.setName(name);

        userEntity.setName(name);

        userOutcomingDto.setId(id);
        userOutcomingDto.setName(name);
        userOutcomingDto.setRecords(new ArrayList<>());
    }

    @Test
    void saveUserTest() throws Exception {
        when(userDtoMapper.incomingUserMap(any()))
                .thenReturn(userEntity);

        when(userService.createUser(any()))
                .thenReturn(userEntity);

        when(userDtoMapper.outComingUserMap(any()))
                .thenReturn(userOutcomingDto);

        mvc.perform(post("/user")
                .content(mapper.writeValueAsString(userIncomingDto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is(name)));
    }

    @Test
    void getUserByIdTest() throws Exception {
        when(userService.findUserById(any()))
                .thenReturn(userEntity);

        when(userDtoMapper.outComingUserMap(any()))
                .thenReturn(userOutcomingDto);

        mvc.perform(get("/user/" + id)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is(name)));
    }

    @Test
    void updateUserByIdTest() throws Exception {
        when(userDtoMapper.incomingUserMap(any()))
                .thenReturn(userEntity);

        when(userService.updateUser(any(), any(UUID.class)))
                .thenReturn(userEntity);

        when(userDtoMapper.outComingUserMap(any()))
                .thenReturn(userOutcomingDto);

        mvc.perform(put("/user/" + id)
                        .content(mapper.writeValueAsString(userIncomingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is(name)));
    }

    @Test
    void deleteUserByIdTest() throws Exception {
        doNothing()
                .when(userService).deleteUser(UUID.fromString(id));

        mvc.perform(delete("/user/" + id)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findAllTest() throws Exception {
        List<UserEntity> users = new ArrayList<>();
        users.add(userEntity);
        when(userService.findAll())
                .thenReturn(users);

        when(userDtoMapper.outComingUserMap(any()))
                .thenReturn(userOutcomingDto);

        mvc.perform(get("/user")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(id)))
                .andExpect(jsonPath("$[0].name", is(name)));
    }
}
