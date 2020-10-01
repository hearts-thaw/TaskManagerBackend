package ru.itis.taskmanager.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.itis.taskmanager.models.Group;

import java.util.List;

@Data
@AllArgsConstructor
public class GroupDto {
    private Long id;
    private String name;
    private List<UserForGroupDto> users;

    public static GroupDto from(Group group, UserForGroupDto userRef) {
        return new GroupDto(group.getId(), group.getName(), List.of(userRef));
    }

    public static GroupDto from(Group group) {
        return new GroupDto(group.getId(), group.getName(), UserForGroupDto.from(group.getTaskusers()));
    }

    public static Group back(GroupDto groupDto) {
        return Group.builder()
                .id(groupDto.getId())
                .name(groupDto.getName())
                .taskusers(UserForGroupDto.back(groupDto.getUsers()))
                .build();
    }
}

