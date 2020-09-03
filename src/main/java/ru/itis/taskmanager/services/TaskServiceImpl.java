package ru.itis.taskmanager.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.taskmanager.models.Task;
import ru.itis.taskmanager.repositories.TaskRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

// FIXME: try-catches on JpaRepositories methods (such as NullPointer/IllegalArgument)
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getAllTasks(boolean completed) {
        return taskRepository.findAllTasksCompleted(completed);
    }

    @Override
    @Transactional
    public Task addTask(Task task) {
        if (task.getDatetime() == null) {
            task.setDatetime(LocalDateTime.now());
        }
        task.setCompleted(false);
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task deleteTask(Long id) {
        Task taskFound;
        try {
            taskFound = taskRepository.getOne(id);
        } catch (EntityNotFoundException e) {
            throw new IllegalArgumentException("Wrong id " + id);
        }
        taskRepository.deleteById(id);
        return taskFound;
    }

    @Override
    @Transactional
    public Task editTask(Long id, String text) {
        Task res = taskRepository.getOne(id);
        res.setText(text);
        return taskRepository.save(res);
    }

    @Override
    @Transactional
    public Task markComplete(Long id) {
        Task res = taskRepository.getOne(id);
        res.setCompleted(true);
        return taskRepository.save(res);
    }
}
