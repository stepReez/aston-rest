package org.aston.task.service;

import org.aston.task.model.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserEntity createUser(UserEntity user);

    UserEntity findUserById(UUID id);

    UserEntity updateUser(UserEntity user, UUID id);

    boolean deleteUser(UUID id);

    List<UserEntity> findAll();
}
