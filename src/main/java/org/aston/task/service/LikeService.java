package org.aston.task.service;

import org.aston.task.model.RecordEntity;
import org.aston.task.model.RecordLikes;
import org.aston.task.model.UserEntity;
import org.aston.task.model.UserLikes;

import java.util.List;
import java.util.UUID;

public interface LikeService {
    void addLike(UUID recordId, UUID userId);

    void removeLike(UUID recordId, UUID userId);

    UserLikes getLikesByUser(UUID userId);

    RecordLikes getLikesByRecord(UUID recordId);
}
