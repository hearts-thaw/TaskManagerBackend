package ru.itis.taskmanager.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.itis.taskmanager.models.TaskUser;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserForGroupDto {
    private Long id;
    private String username;

    public static UserForGroupDto from(TaskUser user) {
        return new UserForGroupDto(user.getId(), user.getUsername());
    }

    public static List<UserForGroupDto> from(List<TaskUser> users) {
        if (users == null) {
            return Collections.emptyList();
        }
        return users.stream().map(UserForGroupDto::from).collect(Collectors.toList());
    }

    public static TaskUser back(UserForGroupDto user) {
        return TaskUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

    public static List<TaskUser> back(List<UserForGroupDto> users) {
        return users.stream().map(UserForGroupDto::back).collect(Collectors.toList());
    }

}
