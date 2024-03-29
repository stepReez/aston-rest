package org.aston.task.service;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;

import java.util.List;
import java.util.UUID;

public interface RecordService {

    RecordEntity createRecord(RecordEntity recordEntity, UUID id);

    RecordEntity findRecordById(UUID id);

    RecordEntity updateRecord(RecordEntity recordEntity, UUID id);

    boolean deleteRecord(UUID id);

    void addLike(RecordEntity recordEntity, UserEntity user);

    List<RecordEntity> findAll();
}
