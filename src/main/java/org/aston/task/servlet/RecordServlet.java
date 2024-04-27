package org.aston.task.servlet;

import org.aston.task.model.RecordEntity;
import org.aston.task.service.RecordService;
import org.aston.task.servlet.dto.RecordIncomingDto;
import org.aston.task.servlet.dto.RecordOutcomingDto;
import org.aston.task.servlet.mapper.RecordDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/record")
public class RecordServlet {

    private RecordService recordService;

    private RecordDtoMapper recordDtoMapper;

    @Autowired
    public RecordServlet(RecordService recordService, RecordDtoMapper recordDtoMapper) {
        this.recordService = recordService;
        this.recordDtoMapper = recordDtoMapper;
    }

    @GetMapping
    public List<RecordOutcomingDto> getAll() {
        List<RecordEntity> recordEntities = recordService.findAll();
        return recordEntities.stream().map(recordDtoMapper::outComingRecordMap).toList();
    }

    @GetMapping("/{id}")
    public RecordOutcomingDto getRecordById(@PathVariable("id") UUID id) {
        RecordEntity recordEntity = recordService.findRecordById(id);
        return recordDtoMapper.outComingRecordMap(recordEntity);
    }

    @GetMapping("/tag")
    public List<RecordOutcomingDto> getByTag(@RequestParam("tagId") int tagId) {
        List<RecordEntity> recordEntities = recordService.findRecordsByTagId(tagId);
        return recordEntities.stream().map(recordDtoMapper::outComingRecordMap).toList();
    }

    @PostMapping
    public RecordOutcomingDto createRecord(@RequestBody RecordIncomingDto recordIncomingDto,
                       @RequestParam("userId") UUID userId) {

            RecordEntity recordEntity =
                    recordService.createRecord(recordDtoMapper.incomingRecordMap(recordIncomingDto), userId);
            return recordDtoMapper.outComingRecordMap(recordEntity);
    }

    @PutMapping
    public RecordOutcomingDto updateRecord(@RequestBody RecordIncomingDto recordIncomingDto,
                                           @RequestParam("id") UUID id) {

            RecordEntity recordEntity = recordService.updateRecord(recordDtoMapper.incomingRecordMap(recordIncomingDto), id);

            return recordDtoMapper.outComingRecordMap(recordEntity);
    }

    @DeleteMapping("/{id}")
    public void deleteRecord(@PathVariable("id") UUID id) {
            recordService.deleteRecord(id);
    }
}
