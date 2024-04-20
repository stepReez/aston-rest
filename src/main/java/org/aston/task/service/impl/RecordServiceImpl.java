package org.aston.task.service.impl;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.RecordLikes;
import org.aston.task.repository.LikeRepository;
import org.aston.task.repository.RecordEntityRepository;
import org.aston.task.repository.TagRepository;
import org.aston.task.repository.UserEntityRepository;
import org.aston.task.repository.impl.LikeRepositoryImpl;
import org.aston.task.repository.impl.RecordEntityRepositoryImpl;
import org.aston.task.repository.impl.TagEntityRepositoryImpl;
import org.aston.task.repository.impl.UserEntityRepositoryImpl;
import org.aston.task.service.RecordService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecordServiceImpl implements RecordService {

    private RecordEntityRepository recordEntityRepository;

    private UserEntityRepository userEntityRepository;

    private LikeRepository likeRepository;

    private TagRepository tagRepository;

    public RecordServiceImpl() {
        recordEntityRepository = new RecordEntityRepositoryImpl();
        userEntityRepository = new UserEntityRepositoryImpl();
        likeRepository = new LikeRepositoryImpl();
        tagRepository = new TagEntityRepositoryImpl();
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

    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public RecordEntity createRecord(RecordEntity recordEntity, UUID userId) {
        recordEntity.setId(UUID.randomUUID());
        recordEntity.setAuthor(userEntityRepository.findById(userId));
        RecordEntity record = recordEntityRepository.save(recordEntity);
        record.setLikes(new RecordLikes());
        record.setTag(tagRepository.getTagsByRecord(recordEntity.getId()));
        return record;
    }

    @Override
    public RecordEntity findRecordById(UUID id) {
        recordEntityRepository.check(id);
        RecordEntity recordEntity = recordEntityRepository.findById(id);
        recordEntity.setLikes(likeRepository.findLikesByRecordId(id));
        recordEntity.setTag(tagRepository.getTagsByRecord(id));
        return recordEntity;
    }

    @Override
    public RecordEntity updateRecord(RecordEntity recordEntity, UUID id) {
        recordEntityRepository.check(id);
        RecordEntity record = recordEntityRepository.update(recordEntity, id);
        record.setLikes(likeRepository.findLikesByRecordId(id));
        record.setTag(tagRepository.getTagsByRecord(recordEntity.getId()));
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
        recordEntities.forEach(recordEntity -> recordEntity.setTag(tagRepository.getTagsByRecord(recordEntity.getId())));
        return recordEntities;
    }
}
