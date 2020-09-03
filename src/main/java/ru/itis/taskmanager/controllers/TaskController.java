package ru.itis.taskmanager.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.taskmanager.models.Task;
import ru.itis.taskmanager.services.TaskService;

import javax.validation.Valid;
import java.util.List;

// TODO: add status codes
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> showTasks(@RequestParam(required = false) Boolean completed) {
        List<Task> res;
        if (completed == null) {
            res = taskService.getAllTasks();
        } else {
            res = taskService.getAllTasks(completed);
        }
        return ResponseEntity.ok(res);
    }

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody @Valid Task task) {
        return ResponseEntity.ok(taskService.addTask(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> vanishTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.deleteTask(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> editTask(@PathVariable Long id, @RequestBody JsonNode text) {
        return ResponseEntity.ok(taskService.editTask(id, text.findValue("text").textValue()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Task> completeTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.markComplete(id));

    }
}
