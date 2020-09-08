package ru.itis.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.taskmanager.models.TaskUser;

import java.util.Optional;

@Repository
public interface TaskUserRepository extends JpaRepository<TaskUser, Long> {
    Optional<TaskUser> findTaskUserByUsername(String username);
}
