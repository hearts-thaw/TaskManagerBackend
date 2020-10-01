package ru.itis.taskmanager.services;

import ru.itis.taskmanager.models.Task;
import ru.itis.taskmanager.security.models.CustomUser;

import java.util.List;

public interface TaskService extends AuthenticatedService<CustomUser> {
    List<Task> getAllTasks(Boolean completed, Boolean onfire, Long frothid);

    List<Task> getAllTasks(Long frothid);

    Task addTask(Task task, Long frothid);

    List<Task> deleteTask(List<Long> id);

    Task deleteTask(Long ids);

    Task editTask(Long id, String text, Long frothid);

    Task markComplete(Long id, Long frothid);
}
