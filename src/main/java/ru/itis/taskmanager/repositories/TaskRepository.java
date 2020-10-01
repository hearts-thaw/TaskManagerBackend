package ru.itis.taskmanager.repositories;

import ru.itis.taskmanager.models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends Repository<Task> {
    Optional<List<Task>> findAllTasksByCompletedAndOnfireAndFroth_Id(Boolean completed, Boolean onfire,
                                                                     Long frothid, Long userid);

    Optional<List<Task>> findAll(Long frothid, Long userid);

    Optional<Task> save(Task task, Long frothid, Long userid);

    Optional<Task> updateCompleted(Long id, Long userid);

    Optional<Task> updateEdited(Long id, String text, Long userid);
}
