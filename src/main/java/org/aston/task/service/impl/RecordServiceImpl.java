package org.aston.task.service.impl;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;
import org.aston.task.service.RecordService;

import java.util.List;
import java.util.UUID;

public class RecordServiceImpl implements RecordService {
    @Override
    public RecordEntity createRecord(RecordEntity recordEntity, UserEntity user) {
        return null;
    }

    @Override
    public RecordEntity findRecordById(UUID id) {
        return null;
    }

    @Override
    public RecordEntity updateRecord(RecordEntity recordEntity, UUID id) {
        return null;
    }

    @Override
    public boolean deleteRecord(UUID id) {
        return true;
    }

    @Override
    public List<RecordEntity> findAll() {
        return null;
    }

    @Override
    public void addLike(RecordEntity recordEntity, UserEntity user) {

    }
}
