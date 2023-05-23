package com.example.taskmaster.repository;

import com.example.taskmaster.entity.TaskmasterUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<TaskmasterUser, Integer> {
    TaskmasterUser findTaskmasterUserByEmail(String email);
}
