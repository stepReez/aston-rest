package org.aston.task.repository;

import org.aston.task.model.RecordEntity;

import java.util.List;
import java.util.UUID;

public interface RecordEntityRepository extends Repository<RecordEntity, UUID>{

    List<RecordEntity> findRecordByAuthorId(UUID authorId);
}
