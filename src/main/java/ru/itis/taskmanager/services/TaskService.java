package ru.itis.taskmanager.services;

import ru.itis.taskmanager.models.Task;

import java.util.List;

public interface TaskService {
    List<Task> getAllTasks(Boolean completed);

    Task addTask(Task task);

    Task deleteTask(Long id);

    List<Task> deleteTask(List<Long> ids);

    Task editTask(Long id, String text);

    Task markComplete(Long id);

}
