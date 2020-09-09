package ru.itis.taskmanager.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.taskmanager.models.Task;
import ru.itis.taskmanager.models.dto.TaskDto;
import ru.itis.taskmanager.services.TaskService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

// TODO: add status codes
//  FIXME: catch errors
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> showTasks(@RequestParam(required = false) Boolean completed) {
        List<Task> res = taskService.getAllTasks(Objects.requireNonNullElse(completed, Boolean.FALSE));
        return ResponseEntity.ok(TaskDto.from(res));
    }

    @PostMapping
    public ResponseEntity<TaskDto> addTask(@RequestBody @Valid Task task) {
        return ResponseEntity.ok(TaskDto.from(taskService.addTask(task)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> vanishTask(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(TaskDto.from(taskService.deleteTask(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<List<TaskDto>> vanishTask(@RequestParam List<Long> ids) {
        try {
            return ResponseEntity.ok(TaskDto.from(taskService.deleteTask(ids)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    //    FIXME: swagger arg desc
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> editTask(@PathVariable Long id, @RequestBody String text) {
        return ResponseEntity.ok(TaskDto.from(taskService.editTask(id, text)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDto> completeTask(@PathVariable Long id) {
        return ResponseEntity.ok(TaskDto.from(taskService.markComplete(id)));

    }

}
