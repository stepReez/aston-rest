package org.aston.task.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aston.task.exceptions.BadRequestException;
import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.service.RecordService;
import org.aston.task.service.UserService;
import org.aston.task.service.impl.RecordServiceImpl;
import org.aston.task.servlet.dto.RecordIncomingDto;
import org.aston.task.servlet.dto.RecordOutcomingDto;
import org.aston.task.servlet.dto.UserIncomingDto;
import org.aston.task.servlet.dto.UserOutcomingDto;
import org.aston.task.servlet.mapper.RecordDtoMapper;
import org.aston.task.servlet.mapper.impl.RecordDtoMapperImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@WebServlet(name = "record", value = "/record")
public class RecordServlet extends HttpServlet {

    private final RecordService recordService;

    private final RecordDtoMapper recordDtoMapper;

    private final Gson gson;

    public RecordServlet() {
        recordService = new RecordServiceImpl();
        recordDtoMapper = new RecordDtoMapperImpl();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String query = req.getQueryString();
        PrintWriter printWriter = resp.getWriter();
        if (query != null && Pattern.matches("^id=.+$", query)) {
            UUID id = UUID.fromString(req.getParameter("id"));
            RecordEntity recordEntity = recordService.findRecordById(id);
            RecordOutcomingDto recordOutcomingDto = recordDtoMapper.outComingRecordMap(recordEntity);

            JsonElement jsonRecord = gson.toJsonTree(recordOutcomingDto);
            String recordString = gson.toJson(jsonRecord);

            printWriter.write(recordString);

        } else if (query == null) {
            List<RecordEntity> recordEntities = recordService.findAll();
            List<RecordOutcomingDto> records = recordEntities.stream().map(recordDtoMapper::outComingRecordMap).toList();
            JsonElement jsonRecord = gson.toJsonTree(records);
            String recordString = gson.toJson(jsonRecord);
            printWriter.write(recordString);
        } else {
            throw new BadRequestException("Bad request: " + query);
        }
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String query = req.getQueryString();

        if (query != null && Pattern.matches("^userId=.+$", query)) {
            String recordString = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            UUID id = UUID.fromString(req.getParameter("userId"));
            RecordIncomingDto recordIncomingDto = gson.fromJson(recordString, RecordIncomingDto.class);
            RecordEntity recordEntity = recordService.createRecord(
                    recordDtoMapper.incomingRecordMap(recordIncomingDto), id);

            RecordOutcomingDto recordOutcomingDto = recordDtoMapper.outComingRecordMap(recordEntity);
            JsonElement jsonRecord = gson.toJsonTree(recordOutcomingDto);
            String recordOutString = gson.toJson(jsonRecord);

            PrintWriter printWriter = resp.getWriter();
            printWriter.write(recordOutString);
            printWriter.close();
        } else {
            throw new BadRequestException("Bad request: " + query);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String recordString = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String query = req.getQueryString();
        if (query != null && Pattern.matches("^id=.+$", query)) {
            UUID id = UUID.fromString(req.getParameter("id"));

            RecordIncomingDto recordIncomingDto = gson.fromJson(recordString, RecordIncomingDto.class);
            RecordEntity recordEntity = recordService.updateRecord(recordDtoMapper.incomingRecordMap(recordIncomingDto), id);

            RecordOutcomingDto recordOutcomingDto = recordDtoMapper.outComingRecordMap(recordEntity);
            JsonElement jsonRecord = gson.toJsonTree(recordOutcomingDto);
            String recordOutString = gson.toJson(jsonRecord);

            PrintWriter printWriter = resp.getWriter();
            printWriter.write(recordOutString);
            printWriter.close();
        } else {
            throw new BadRequestException("Bad request: " + query);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String query = req.getQueryString();
        if (query != null && Pattern.matches("^id=.+$", query)) {
            UUID id = UUID.fromString(req.getParameter("id"));
            recordService.deleteRecord(id);
        } else {
            throw new BadRequestException("Bad request: " + query);
        }
    }
}
