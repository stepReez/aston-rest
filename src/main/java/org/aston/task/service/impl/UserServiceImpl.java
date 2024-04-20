package org.aston.task.service.impl;

import org.aston.task.model.UserEntity;
import org.aston.task.model.UserLikes;
import org.aston.task.repository.LikeRepository;
import org.aston.task.repository.RecordEntityRepository;
import org.aston.task.repository.UserEntityRepository;
import org.aston.task.repository.impl.LikeRepositoryImpl;
import org.aston.task.repository.impl.RecordEntityRepositoryImpl;
import org.aston.task.repository.impl.UserEntityRepositoryImpl;
import org.aston.task.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    private UserEntityRepository userEntityRepository;

    private RecordEntityRepository recordEntityRepository;

    private LikeRepository likeRepository;

    public UserServiceImpl() {
        userEntityRepository = new UserEntityRepositoryImpl();
        recordEntityRepository = new RecordEntityRepositoryImpl();
        likeRepository = new LikeRepositoryImpl();
    }

    public void setUserEntityRepository(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    public void setRecordEntityRepository(RecordEntityRepository recordEntityRepository) {
        this.recordEntityRepository = recordEntityRepository;
    }

    public void setLikeRepository(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        UUID uuid = UUID.randomUUID();
        user.setId(uuid);
        UserEntity userEntity = userEntityRepository.save(user);
        userEntity.setRecords(new ArrayList<>());
        userEntity.setUserLikes(new UserLikes());
        return userEntity;
    }

    @Override
    public UserEntity findUserById(UUID id) {
        userEntityRepository.check(id);
        UserEntity userEntity = userEntityRepository.findById(id);
        userEntity.setRecords(recordEntityRepository.findRecordByAuthorId(id));
        userEntity.setUserLikes(likeRepository.findLikesByUserId(id));
        return userEntity;
    }

    @Override
    public UserEntity updateUser(UserEntity user, UUID id) {
        userEntityRepository.check(id);
        UserEntity userEntity = userEntityRepository.update(user, id);
        userEntity.setRecords(recordEntityRepository.findRecordByAuthorId(id));
        userEntity.setUserLikes(likeRepository.findLikesByUserId(id));
        return userEntity;
    }

    @Override
    public boolean deleteUser(UUID id) {
        userEntityRepository.check(id);
        return userEntityRepository.deleteById(id);
    }

    @Override
    public List<UserEntity> findAll() {
        List<UserEntity> userEntities = userEntityRepository.findAll();
        userEntities.forEach(userEntity ->
                userEntity.setRecords(recordEntityRepository.findRecordByAuthorId(userEntity.getId())));
        userEntities.forEach(userEntity -> userEntity.setUserLikes(likeRepository.findLikesByUserId(userEntity.getId())));
        return userEntities;
    }
}
