package ru.itis.taskmanager.services;

import ru.itis.taskmanager.models.Task;

import java.util.List;

public interface TaskService {

    List<Task> getAllTasks();

    List<Task> getAllTasks(boolean completed);

    Task addTask(Task task);

    Task deleteTask(Long id);

    Task editTask(Long id, String text);

    Task markComplete(Long id);

}
