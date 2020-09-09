package ru.itis.taskmanager.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.taskmanager.models.TaskUser;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class UserDto {
    private String username;

    public static UserDto from(TaskUser user) {
        return UserDto.builder()
                .username(user.getUsername()).build();
    }

    public static List<UserDto> from(List<TaskUser> users) {
        return users.stream().map(UserDto::from).collect(Collectors.toList());
    }
}
