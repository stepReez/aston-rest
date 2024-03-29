package org.aston.task.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.service.RecordService;
import org.aston.task.servlet.dto.RecordIncomingDto;
import org.aston.task.servlet.dto.RecordOutcomingDto;
import org.aston.task.servlet.dto.UserIncomingDto;
import org.aston.task.servlet.dto.UserOutcomingDto;
import org.aston.task.servlet.mapper.RecordDtoMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet(name = "record", value = "/record")
public class RecordServlet extends HttpServlet {

    private RecordService recordService;

    private RecordDtoMapper recordDtoMapper;

    private Gson gson;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String query = req.getQueryString();
        gson = new Gson();
        PrintWriter printWriter = resp.getWriter();
        if (query != null && query.contains("id=")) {
            UUID id = UUID.fromString(query.split("=")[1]);
            RecordEntity recordEntity = recordService.findRecordById(id);
            RecordOutcomingDto recordOutcomingDto = recordDtoMapper.outComingRecordMap(recordEntity);

            JsonElement jsonRecord = gson.toJsonTree(recordOutcomingDto);
            String recordString = gson.toJson(jsonRecord);

            printWriter.write(recordString);

        } else {
            List<RecordEntity> recordEntities = recordService.findAll();
            List<RecordOutcomingDto> records = recordEntities.stream().map(recordDtoMapper::outComingRecordMap).toList();
            JsonElement jsonRecord = gson.toJsonTree(records);
            String recordString = gson.toJson(jsonRecord);
            printWriter.write(recordString);
        }
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String query = req.getQueryString();

        gson = new Gson();
        if (query != null && query.contains("id=")) {
            String recordString = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            UUID id = UUID.fromString(query.split("=")[1]);
            RecordIncomingDto recordIncomingDto = gson.fromJson(recordString, RecordIncomingDto.class);
            RecordEntity recordEntity = recordService.createRecord(
                    recordDtoMapper.incomingRecordMap(recordIncomingDto), id);

            RecordOutcomingDto recordOutcomingDto = recordDtoMapper.outComingRecordMap(recordEntity);
            JsonElement jsonRecord = gson.toJsonTree(recordOutcomingDto);
            String recordOutString = gson.toJson(jsonRecord);

            PrintWriter printWriter = resp.getWriter();
            printWriter.write(recordOutString);
            printWriter.close();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String recordString = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String query = req.getQueryString();
        if (query != null && query.contains("id=")) {
            UUID id = UUID.fromString(query.split("=")[1]);

            gson = new Gson();
            RecordIncomingDto recordIncomingDto = gson.fromJson(recordString, RecordIncomingDto.class);
            RecordEntity recordEntity = recordService.updateRecord(recordDtoMapper.incomingRecordMap(recordIncomingDto), id);

            RecordOutcomingDto recordOutcomingDto = recordDtoMapper.outComingRecordMap(recordEntity);
            JsonElement jsonRecord = gson.toJsonTree(recordOutcomingDto);
            String recordOutString = gson.toJson(jsonRecord);

            PrintWriter printWriter = resp.getWriter();
            printWriter.write(recordOutString);
            printWriter.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String query = req.getQueryString();
        if (query != null && query.contains("id=")) {
            UUID id = UUID.fromString(query.split("=")[1]);
            boolean isDeleted = recordService.deleteRecord(id);
        }
    }
}
