package ru.itis.taskmanager.repositories;

import ru.itis.taskmanager.models.TaskUser;

import java.util.Optional;

public interface TaskUserRepository {
    Optional<TaskUser> findTaskUserByUsername(String username);

    TaskUser save(TaskUser taskUser);
}
