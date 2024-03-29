package org.aston.task.repository;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;

import java.util.UUID;

public interface RecordEntityRepository extends Repository<RecordEntity, UUID>{

    void addLike(RecordEntity recordEntity, UserEntity user);

    RecordEntity createRecord(RecordEntity recordEntity, UserEntity user);
}
