package org.aston.task.service.impl;

import org.aston.task.model.UserEntity;
import org.aston.task.service.UserService;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public UserEntity createUser(UserEntity user) {
        return null;
    }

    @Override
    public UserEntity findUserById(UUID id) {
        return null;
    }

    @Override
    public UserEntity updateUser(UserEntity user, UUID id) {
        return null;
    }

    @Override
    public boolean deleteUser(UUID id) {
        return true;
    }

    @Override
    public List<UserEntity> findAll() {
        return null;
    }
}
