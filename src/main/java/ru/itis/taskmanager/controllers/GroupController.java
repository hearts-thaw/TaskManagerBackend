package ru.itis.taskmanager.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.taskmanager.models.Group;
import ru.itis.taskmanager.models.dto.GroupDto;
import ru.itis.taskmanager.services.GroupService;

import javax.validation.Valid;
import java.util.List;

@Profile("jdbc")
@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<GroupDto>> showGroupsList() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @PostMapping
    public ResponseEntity<GroupDto> createGroup(@RequestBody @Valid Group group) {
        return ResponseEntity.ok(groupService.create(group));
    }
}
