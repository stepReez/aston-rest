package org.aston.task.repository.impl;

import org.aston.task.model.UserEntity;
import org.aston.task.repository.UserEntityRepository;

import java.util.List;
import java.util.UUID;

public class UserEntityRepositoryImpl implements UserEntityRepository {
    @Override
    public UserEntity findById(UUID id) {
        return null;
    }

    @Override
    public boolean deleteById(UUID id) {
        return false;
    }

    @Override
    public List<UserEntity> findAll() {
        return null;
    }

    @Override
    public UserEntity save(UserEntity user) {
        return null;
    }

    @Override
    public UserEntity update(UserEntity user, UUID uuid) {
        return null;
    }
}
