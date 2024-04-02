package org.aston.task.repository;

import org.aston.task.exceptions.NotFoundException;
import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;

import java.util.List;
import java.util.UUID;

public interface LikeRepository {
    void addLike(UUID recordId, UUID userId);

    void removeLike(UUID recordId, UUID userId);

    List<RecordEntity> findLikesByUserId(UUID userId);

    List<UserEntity> findLikesByRecordId(UUID recordId);

    void check(UUID recordId, UUID userId) throws NotFoundException;
}
