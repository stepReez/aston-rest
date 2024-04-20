package org.aston.task.service.impl;

import org.aston.task.model.RecordLikes;
import org.aston.task.model.UserLikes;
import org.aston.task.repository.LikeRepository;
import org.aston.task.repository.impl.LikeRepositoryImpl;
import org.aston.task.service.LikeService;

import java.util.UUID;

public class LikeServiceImpl implements LikeService {

    private LikeRepository likeRepository;

    public LikeServiceImpl() {
        likeRepository = new LikeRepositoryImpl();
    }

    public void setLikeRepository(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public void addLike(UUID recordId, UUID userId) {
        likeRepository.addLike(recordId, userId);
    }

    @Override
    public void removeLike(UUID recordId, UUID userId) {
        likeRepository.check(recordId, userId);
        likeRepository.removeLike(recordId, userId);
    }

    @Override
    public UserLikes getLikesByUser(UUID userId) {
        return likeRepository.findLikesByUserId(userId);
    }

    @Override
    public RecordLikes getLikesByRecord(UUID recordId) {
        return likeRepository.findLikesByRecordId(recordId);
    }
}
