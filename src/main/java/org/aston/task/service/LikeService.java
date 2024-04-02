package org.aston.task.service;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.UserEntity;

import java.util.List;
import java.util.UUID;

public interface LikeService {
    void addLike(UUID recordId, UUID userId);

    void removeLike(UUID recordId, UUID userId);

    List<RecordEntity> getLikesByUser(UUID userId);

    List<UserEntity> getLikesByRecord(UUID recordId);
}
