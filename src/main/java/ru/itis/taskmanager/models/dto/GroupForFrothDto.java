package ru.itis.taskmanager.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.itis.taskmanager.models.Group;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class GroupForFrothDto {
    private Long id;
    private String name;

    public static GroupForFrothDto from(Group group) {
        return new GroupForFrothDto(group.getId(), group.getName());
    }

    public static List<GroupForFrothDto> from(List<Group> group) {
        return group.stream().map(GroupForFrothDto::from).collect(Collectors.toList());
    }
}
