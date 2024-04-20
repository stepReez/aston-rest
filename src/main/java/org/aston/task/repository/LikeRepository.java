package org.aston.task.repository;

import org.aston.task.exceptions.NotFoundException;
import org.aston.task.model.RecordLikes;
import org.aston.task.model.UserLikes;

import java.util.List;
import java.util.UUID;

public interface LikeRepository {
    void addLike(UUID recordId, UUID userId);

    void removeLike(UUID recordId, UUID userId);

    UserLikes findLikesByUserId(UUID userId);

    RecordLikes findLikesByRecordId(UUID recordId);

    void check(UUID recordId, UUID userId) throws NotFoundException;
}
