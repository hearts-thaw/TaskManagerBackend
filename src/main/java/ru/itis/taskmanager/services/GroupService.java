package ru.itis.taskmanager.services;

import ru.itis.taskmanager.models.Group;
import ru.itis.taskmanager.models.TaskUser;
import ru.itis.taskmanager.models.dto.GroupDto;
import ru.itis.taskmanager.security.models.CustomUser;

import java.util.List;

public interface GroupService extends AuthenticatedService<CustomUser> {
    List<GroupDto> getAllGroups();

    GroupDto create(Group group);

    GroupDto create(TaskUser newUser);
}
