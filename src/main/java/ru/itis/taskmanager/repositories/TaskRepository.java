package ru.itis.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itis.taskmanager.models.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task AS t WHERE t.completed = :completed")
    List<Task> findAllTasksCompleted(boolean completed);
}
