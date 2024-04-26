package org.aston.task.service.impl;

import org.aston.task.exceptions.NotFoundException;
import org.aston.task.model.RecordEntity;
import org.aston.task.repository.RecordEntityRepository;
import org.aston.task.repository.TagEntityRepository;
import org.aston.task.repository.UserEntityRepository;
import org.aston.task.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RecordServiceImpl implements RecordService {

    private RecordEntityRepository recordEntityRepository;

    private UserEntityRepository userEntityRepository;

    private TagEntityRepository tagEntityRepository;

    @Autowired
    public RecordServiceImpl(RecordEntityRepository recordEntityRepository, UserEntityRepository userEntityRepository,
                             TagEntityRepository tagEntityRepository) {
        this.recordEntityRepository = recordEntityRepository;
        this.userEntityRepository = userEntityRepository;
        this.tagEntityRepository = tagEntityRepository;
    }

    public RecordServiceImpl(){}

    public void setRecordEntityRepository(RecordEntityRepository recordEntityRepository) {
        this.recordEntityRepository = recordEntityRepository;
    }

    public void setUserEntityRepository(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    public void setTagRepository(TagEntityRepository tagEntityRepository) {
        this.tagEntityRepository = tagEntityRepository;
    }

    @Override
    public RecordEntity createRecord(RecordEntity recordEntity, UUID userId) {
        recordEntity.setAuthor(userEntityRepository.findById(userId).orElseThrow(NotFoundException::new));
        RecordEntity record = recordEntityRepository.save(recordEntity);
        record.setTags(tagEntityRepository.findByRecordId(recordEntity.getId()));
        return record;
    }

    @Override
    public RecordEntity findRecordById(UUID id) {
        RecordEntity recordEntity = recordEntityRepository.findById(id).orElseThrow(NotFoundException::new);
        recordEntity.setTags(tagEntityRepository.findByRecordId(id));
        return recordEntity;
    }

    @Override
    public RecordEntity updateRecord(RecordEntity recordEntity, UUID id) {
        recordEntity.setId(id);
        RecordEntity record = recordEntityRepository.save(recordEntity);
        record.setTags(tagEntityRepository.findByRecordId(recordEntity.getId()));
        return record;
    }

    @Override
    public void deleteRecord(UUID id) {
        recordEntityRepository.deleteById(id);
    }

    @Override
    public List<RecordEntity> findRecordsByTagId(int id) {
        return recordEntityRepository.findByTagId(id);
    }

    @Override
    public List<RecordEntity> findAll() {
        List<RecordEntity> recordEntities = recordEntityRepository.findAll();
        recordEntities.forEach(recordEntity -> recordEntity.setTags(tagEntityRepository.findByRecordId(recordEntity.getId())));
        return recordEntities;
    }
}
