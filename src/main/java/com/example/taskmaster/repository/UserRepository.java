package com.example.taskmaster.repository;

import com.example.taskmaster.entity.TaskmasterUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<TaskmasterUser, Integer> {
    TaskmasterUser findTaskmasterUserByEmail(String email);
}
