package org.aston.task.service.impl;

import org.aston.task.model.RecordEntity;
import org.aston.task.repository.LikeRepository;
import org.aston.task.repository.RecordEntityRepository;
import org.aston.task.repository.UserEntityRepository;
import org.aston.task.repository.impl.LikeRepositoryImpl;
import org.aston.task.repository.impl.RecordEntityRepositoryImpl;
import org.aston.task.repository.impl.UserEntityRepositoryImpl;
import org.aston.task.service.RecordService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecordServiceImpl implements RecordService {

    private RecordEntityRepository recordEntityRepository;

    private UserEntityRepository userEntityRepository;

    private LikeRepository likeRepository;

    public RecordServiceImpl() {
        recordEntityRepository = new RecordEntityRepositoryImpl();
        userEntityRepository = new UserEntityRepositoryImpl();
        likeRepository = new LikeRepositoryImpl();
    }

    public void setRecordEntityRepository(RecordEntityRepository recordEntityRepository) {
        this.recordEntityRepository = recordEntityRepository;
    }

    public void setUserEntityRepository(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    public void setLikeRepository(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public RecordEntity createRecord(RecordEntity recordEntity, UUID userId) {
        recordEntity.setId(UUID.randomUUID());
        recordEntity.setAuthor(userEntityRepository.findById(userId));
        RecordEntity record = recordEntityRepository.save(recordEntity);
        record.setLikes(new ArrayList<>());
        return record;
    }

    @Override
    public RecordEntity findRecordById(UUID id) {
        recordEntityRepository.check(id);
        RecordEntity recordEntity = recordEntityRepository.findById(id);
        recordEntity.setLikes(likeRepository.findLikesByRecordId(id));
        return recordEntity;
    }

    @Override
    public RecordEntity updateRecord(RecordEntity recordEntity, UUID id) {
        recordEntityRepository.check(id);
        RecordEntity record = recordEntityRepository.update(recordEntity, id);
        record.setLikes(likeRepository.findLikesByRecordId(id));
        return record;
    }

    @Override
    public boolean deleteRecord(UUID id) {
        recordEntityRepository.check(id);
        return recordEntityRepository.deleteById(id);
    }

    @Override
    public List<RecordEntity> findAll() {
        List<RecordEntity> recordEntities = recordEntityRepository.findAll();
        recordEntities.forEach(recordEntity ->
                recordEntity.setLikes(likeRepository.findLikesByRecordId(recordEntity.getId())));
        return recordEntities;
    }
}