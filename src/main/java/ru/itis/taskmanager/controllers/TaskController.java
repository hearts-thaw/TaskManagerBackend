package ru.itis.taskmanager.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.taskmanager.models.Task;
import ru.itis.taskmanager.models.dto.TaskDto;
import ru.itis.taskmanager.services.TaskService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Profile("jdbc")
@RestController
@RequestMapping(value = "/froth/{frothid}/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> showTasks(@RequestParam(required = false) Boolean completed,
                                                   @RequestParam(required = false) Boolean onfire, @PathVariable Long frothid) {
        List<Task> res = taskService.getAllTasks(
                Objects.requireNonNullElse(completed, Boolean.FALSE),
                Objects.requireNonNullElse(onfire, Boolean.FALSE), frothid);
        return ResponseEntity.ok(TaskDto.from(res));
    }

    @PostMapping
    public ResponseEntity<TaskDto> addTask(@RequestBody @Valid Task task, @PathVariable Long frothid) {
        Task res = taskService.addTask(task, frothid);
        return ResponseEntity.ok(TaskDto.from(res));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> vanishTask(@PathVariable Long id, @PathVariable Long frothid) {
        return ResponseEntity.ok(TaskDto.from(taskService.deleteTask(id)));
    }

    @DeleteMapping
    public ResponseEntity<List<TaskDto>> vanishTask(
            @RequestBody List<Long> ids, @PathVariable Long frothid) {
        return ResponseEntity.ok(TaskDto.from(taskService.deleteTask(ids)));
    }

    //    FIXME: swagger arg desc
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> editTask(
            @PathVariable Long id, @RequestBody String text, @PathVariable(required = false) Long frothid) {
        return ResponseEntity.ok(TaskDto.from(taskService.editTask(id, text, frothid)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDto> completeTask(@PathVariable Long id, @PathVariable Long frothid) {
        return ResponseEntity.ok(TaskDto.from(taskService.markComplete(id, frothid)));

    }

}
