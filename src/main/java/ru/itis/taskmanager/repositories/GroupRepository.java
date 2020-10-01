package ru.itis.taskmanager.repositories;

import ru.itis.taskmanager.models.Group;
import ru.itis.taskmanager.models.TaskUser;
import ru.itis.taskmanager.models.dto.GroupDto;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    Optional<List<GroupDto>> findAllByTaskuser_Id(Long taskuser_id);

    Optional<GroupDto> save(Group group, TaskUser user);

    Optional<GroupDto> save(TaskUser newUser);
}
