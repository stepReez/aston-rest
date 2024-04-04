package org.aston.task.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aston.task.exceptions.BadRequestException;
import org.aston.task.model.RecordEntity;
import org.aston.task.service.RecordService;
import org.aston.task.servlet.dto.RecordIncomingDto;
import org.aston.task.servlet.dto.RecordOutcomingDto;
import org.aston.task.servlet.mapper.RecordDtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class RecordServletTest {

    RecordServlet recordServlet = new RecordServlet();

    Gson gson = new Gson();

    @Test
    void doGetRecordByIdTest() throws IOException, ServletException {
        RecordService recordService = Mockito.mock(RecordService.class);
        recordServlet.setRecordService(recordService);

        RecordDtoMapper recordDtoMapper = Mockito.mock(RecordDtoMapper.class);
        recordServlet.setRecordDtoMapper(recordDtoMapper);

        RecordEntity recordEntity = new RecordEntity();
        UUID id = UUID.randomUUID();
        recordEntity.setId(id);

        Mockito
                .when(recordService.findRecordById(id))
                .thenReturn(recordEntity);

        RecordOutcomingDto recordOutcomingDto = new RecordOutcomingDto();
        recordOutcomingDto.setId(id.toString());

        Mockito
                .when(recordDtoMapper.outComingRecordMap(recordEntity))
                .thenReturn(recordOutcomingDto);

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

        recordServlet.doGet(req, resp);

        JsonElement jsonUser = gson.toJsonTree(recordOutcomingDto);
        String userString = gson.toJson(jsonUser);

        Assertions.assertEquals(userString, out.toString());
    }

    @Test
    void doGetAllRecordTest() throws IOException, ServletException {
        RecordService recordService = Mockito.mock(RecordService.class);
        recordServlet.setRecordService(recordService);

        RecordDtoMapper recordDtoMapper = Mockito.mock(RecordDtoMapper.class);
        recordServlet.setRecordDtoMapper(recordDtoMapper);

        RecordEntity recordEntity = new RecordEntity();
        UUID id = UUID.randomUUID();
        recordEntity.setId(id);

        List<RecordEntity> userEntities = new ArrayList<>();
        userEntities.add(recordEntity);

        Mockito
                .when(recordService.findAll())
                .thenReturn(userEntities);

        RecordOutcomingDto recordOutcomingDto = new RecordOutcomingDto();
        recordOutcomingDto.setId(id.toString());

        List<RecordOutcomingDto> recordOutcomingDtos = new ArrayList<>();
        recordOutcomingDtos.add(recordOutcomingDto);

        Mockito
                .when(recordDtoMapper.outComingRecordMap(recordEntity))
                .thenReturn(recordOutcomingDto);

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

        recordServlet.doGet(req, resp);

        JsonElement jsonUser = gson.toJsonTree(recordOutcomingDtos);
        String userString = gson.toJson(jsonUser);

        Assertions.assertEquals(userString, out.toString());
    }

    @Test
    void doPutRecordByIdTest() throws IOException, ServletException {
        RecordService recordService = Mockito.mock(RecordService.class);
        recordServlet.setRecordService(recordService);

        RecordDtoMapper recordDtoMapper = Mockito.mock(RecordDtoMapper.class);
        recordServlet.setRecordDtoMapper(recordDtoMapper);

        RecordEntity recordEntity = new RecordEntity();
        UUID id = UUID.randomUUID();
        String text = "text";
        recordEntity.setId(id);
        recordEntity.setText(text);

        Mockito
                .when(recordService.updateRecord(recordEntity, id))
                .thenReturn(recordEntity);


        RecordOutcomingDto recordOutcomingDto = new RecordOutcomingDto();
        recordOutcomingDto.setId(id.toString());
        recordOutcomingDto.setText(text);

        Mockito
                .when(recordDtoMapper.outComingRecordMap(Mockito.any()))
                .thenReturn(recordOutcomingDto);

        RecordIncomingDto recordIncomingDto = new RecordIncomingDto();
        recordIncomingDto.setText(text);

        Mockito
                .when(recordDtoMapper.incomingRecordMap(recordIncomingDto))
                .thenReturn(recordEntity);

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito
                .when(req.getQueryString())
                .thenReturn("id=" + id);

        Mockito
                .when(req.getParameter("id"))
                .thenReturn(id.toString());

        JsonElement jsonIncomingUser = gson.toJsonTree(recordIncomingDto);
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

        recordServlet.doPut(req, resp);

        JsonElement jsonUser = gson.toJsonTree(recordOutcomingDto);
        String userString = gson.toJson(jsonUser);

        Assertions.assertEquals(userString, out.toString());
    }

    @Test
    void doPostRecordByIdTest() throws IOException, ServletException {
        RecordService recordService = Mockito.mock(RecordService.class);
        recordServlet.setRecordService(recordService);

        RecordDtoMapper recordDtoMapper = Mockito.mock(RecordDtoMapper.class);
        recordServlet.setRecordDtoMapper(recordDtoMapper);

        RecordEntity recordEntity = new RecordEntity();
        UUID id = UUID.randomUUID();
        String text = "text";
        recordEntity.setId(id);
        recordEntity.setText(text);

        Mockito
                .when(recordService.updateRecord(recordEntity, id))
                .thenReturn(recordEntity);


        RecordOutcomingDto recordOutcomingDto = new RecordOutcomingDto();
        recordOutcomingDto.setId(id.toString());
        recordOutcomingDto.setText(text);

        Mockito
                .when(recordDtoMapper.outComingRecordMap(Mockito.any()))
                .thenReturn(recordOutcomingDto);

        RecordIncomingDto recordIncomingDto = new RecordIncomingDto();
        recordIncomingDto.setText(text);

        Mockito
                .when(recordDtoMapper.incomingRecordMap(recordIncomingDto))
                .thenReturn(recordEntity);

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito
                .when(req.getQueryString())
                .thenReturn("userId=" + id);

        Mockito
                .when(req.getParameter("userId"))
                .thenReturn(id.toString());

        JsonElement jsonIncomingUser = gson.toJsonTree(recordIncomingDto);
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

        recordServlet.doPost(req, resp);

        JsonElement jsonUser = gson.toJsonTree(recordOutcomingDto);
        String userString = gson.toJson(jsonUser);

        Assertions.assertEquals(userString, out.toString());
    }

    @Test
    void doGetRecordBadRequestTest() {


        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito
                .when(req.getQueryString())
                .thenReturn("not=" + "name");

        Assertions.assertThrows(BadRequestException.class, () -> recordServlet.doGet(req, resp));
    }

    @Test
    void doPutUserBadRequestTest() {

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito
                .when(req.getQueryString())
                .thenReturn("not=" + "name");

        Assertions.assertThrows(BadRequestException.class, () -> recordServlet.doPut(req, resp));
    }

    @Test
    void doDeleteRecordBadRequestTest() {


        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito
                .when(req.getQueryString())
                .thenReturn("not=" + "name");

        Assertions.assertThrows(BadRequestException.class, () -> recordServlet.doDelete(req, resp));
    }

    @Test
    void doPostRecordBadRequestTest() {


        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

        Mockito
                .when(req.getQueryString())
                .thenReturn("not=" + "name");

        Assertions.assertThrows(BadRequestException.class, () -> recordServlet.doPost(req, resp));
    }
}
