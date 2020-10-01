package ru.itis.taskmanager.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.taskmanager.models.Froth;
import ru.itis.taskmanager.models.Task;
import ru.itis.taskmanager.models.TaskUser;
import ru.itis.taskmanager.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Profile("jdbc")
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final static short TASKS_LIMIT = Short.parseShort("200");

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAllTasks(Boolean completed, Boolean onfire, Long frothid) {
        return taskRepository.findAllTasksByCompletedAndOnfireAndFroth_Id(completed, onfire, frothid, getId())
                .orElse(Collections.emptyList());
    }

    @Override
    public List<Task> getAllTasks(Long frothid) {
        return taskRepository.findAll(frothid, getId())
                .orElse(Collections.emptyList());
    }

    @Override
    public Task addTask(Task task, Long frothid) {
        if (checkForLimit(TASKS_LIMIT, frothid)) {
            throw new IllegalArgumentException("User has too much tasks!");
        }

        return taskRepository.save(Task.builder()
                .text(task.getText())
                .completed(false)
                .datetime(LocalDateTime.now())
                .user_id(TaskUser.builder().id(getId()).build())
                .froth_id(Froth.builder().id(frothid).build())
                .onfire(false).build(), frothid, getId()).orElseThrow(
                () -> new IllegalArgumentException("Can't add task!")
        );
    }

    @Override
    public Task deleteTask(Long id) {
        Task taskFound = taskRepository.findById(id, getId()).orElseThrow(
                () -> new IllegalArgumentException("Wrong id: " + id));

        System.out.println("Id of user in task: " + taskFound.getUser_id().getId() + "; " +
                "Id of user in auth: " + getId());

        if (!taskFound.getUser_id().getId().equals(getId())) {
            throw new IllegalArgumentException("Not allowed");
        }

        taskRepository.delete(id, getId());

        return taskFound;

    }

    @Override
    public List<Task> deleteTask(List<Long> ids) {
        List<Task> tasksFound = taskRepository.findAllByIds(ids, getId()).orElseThrow(
                () -> new IllegalArgumentException("No such tasks")
        );

        return tasksFound.stream().map(task -> deleteTask(task.getId())).collect(Collectors.toList());
    }

    @Override
    public Task editTask(Long id, String text, Long frothid) {
        return taskRepository.updateEdited(id, text, getId()).orElseThrow(
                () -> new IllegalArgumentException("No task with id: " + id)
        );
    }

    @Override
    public Task markComplete(Long id, Long frothid) {
        return taskRepository.updateCompleted(id, getId()).orElseThrow(
                () -> new IllegalArgumentException("No task with id: " + id)
        );
    }

    public boolean checkForLimit(short limit, Long frothid) {
        List<Task> tasks = taskRepository.findAll(frothid, getId()).orElse(Collections.emptyList());

        return tasks.size() > limit;
    }
}
