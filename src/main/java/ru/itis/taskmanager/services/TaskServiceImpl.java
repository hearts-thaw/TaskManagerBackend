package ru.itis.taskmanager.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.taskmanager.models.Task;
import ru.itis.taskmanager.models.TaskUser;
import ru.itis.taskmanager.repositories.TaskRepository;
import ru.itis.taskmanager.security.models.CustomUserDetailsImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// FIXME: try-catches on JpaRepositories methods (such as NullPointer/IllegalArgument)
@Service
@Transactional
public class TaskServiceImpl extends AuthenticatedService<CustomUserDetailsImpl> implements TaskService {

    private final static short TASKS_LIMIT = Short.parseShort("200");

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAllTasks(Boolean completed) {
        return taskRepository.findAllTasksByUserIdAndCompletion(getId(), completed);
    }

    @Override
    public Task addTask(Task task) {
        if (checkForLimit(TASKS_LIMIT)) {
            throw new IllegalArgumentException("User has too much tasks!");
        }

        return taskRepository.save(Task.builder()
                .text(task.getText())
                .completed(false)
                .datetime(LocalDateTime.now())
                .userid((TaskUser) getUser()).build());
    }

    @Override
    public Task deleteTask(Long id) {
        Task taskFound = taskRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Wrong id: " + id));

        if (!taskFound.getUserid().getId().equals(getId())) {
            throw new IllegalArgumentException("Not allowed");
        }

        taskRepository.deleteByIdAndUserid_Id(id, getId());

        return taskFound;

    }

    @Override
    public List<Task> deleteTask(List<Long> ids) {
        List<Task> tasksFound = taskRepository.findAllById(ids);

        return tasksFound.stream().map(task -> deleteTask(task.getId())).collect(Collectors.toList());
    }

    @Override
    public Task editTask(Long id, String text) {
        if (checkForLimit(TASKS_LIMIT)) {
            throw new IllegalArgumentException("User has too much tasks!");
        }

        Task taskFound = taskRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Wrong id: " + id));

        taskFound.setText(text);

        return taskRepository.save(taskFound);
    }

    @Override
    public Task markComplete(Long id) {
        Task taskFound = taskRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Wrong id: " + id));

        if (taskFound.isCompleted()) {
            throw new IllegalArgumentException("Task is already completed, try deletion");
        }
        taskFound.setCompleted(true);

        return taskRepository.save(taskFound);
    }

    public boolean checkForLimit(short limit) {
        List<Task> tasks = getAllTasks(true);

        return tasks.size() > limit;
    }
}
