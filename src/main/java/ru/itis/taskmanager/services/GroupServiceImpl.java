package ru.itis.taskmanager.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.itis.taskmanager.models.Group;
import ru.itis.taskmanager.models.TaskUser;
import ru.itis.taskmanager.models.dto.GroupDto;
import ru.itis.taskmanager.repositories.GroupRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Profile("jdbc")
@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<GroupDto> getAllGroups() {
        return groupRepository.findAllByTaskuser_Id(getId()).orElse(Collections.emptyList());
    }

    @Override
    public GroupDto create(Group group) {
        return groupRepository.save(group, getUser()).orElseThrow(
                () -> new IllegalArgumentException("Can't create group")
        );
    }

    @Override
    public GroupDto create(TaskUser newUser) {
        return groupRepository.save(newUser).orElseThrow(
                () -> new IllegalArgumentException("Can't create group")
        );
    }

}
