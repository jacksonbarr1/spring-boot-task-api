package com.example.taskapi.repository;

import com.example.taskapi.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    UserEntity findUserEntityByEmail(String email);
}
