package org.aston.task.service.impl;

import org.aston.task.exceptions.NotFoundException;
import org.aston.task.model.UserEntity;
import org.aston.task.repository.RecordEntityRepository;
import org.aston.task.repository.UserEntityRepository;
import org.aston.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;

    private final RecordEntityRepository recordEntityRepository;

    @Autowired
    public UserServiceImpl(UserEntityRepository userEntityRepository, RecordEntityRepository recordEntityRepository) {
        this.userEntityRepository = userEntityRepository;
        this.recordEntityRepository = recordEntityRepository;
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        UserEntity userEntity = userEntityRepository.save(user);
        userEntity.setRecords(new ArrayList<>());
        return userEntity;
    }

    @Override
    public UserEntity findUserById(UUID id) {
        UserEntity userEntity = userEntityRepository.findById(id).orElseThrow(NotFoundException::new);
        userEntity.setRecords(recordEntityRepository.findByAuthorId(id));
        return userEntity;
    }

    @Override
    public UserEntity updateUser(UserEntity user, UUID id) {
        user.setId(id);
        UserEntity userEntity = userEntityRepository.save(user);
        userEntity.setRecords(recordEntityRepository.findByAuthorId(id));
        return userEntity;
    }

    @Override
    public void deleteUser(UUID id) {
        userEntityRepository.deleteById(id);
    }

    @Override
    public List<UserEntity> findAll() {
        List<UserEntity> userEntities = userEntityRepository.findAll();
        userEntities.forEach(userEntity ->
                userEntity.setRecords(recordEntityRepository.findByAuthorId(userEntity.getId())));
        return userEntities;
    }
}
