package ru.itis.taskmanager.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import ru.itis.taskmanager.models.TaskUser;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Profile("jdbc")
public class UserDto {
    private String username;

    public static UserDto from(TaskUser user) {
        return UserDto.builder()
                .username(user.getUsername()).build();
    }

    public static Set<UserDto> from(Set<TaskUser> users) {
        return users.stream().map(UserDto::from).collect(Collectors.toSet());
    }
}
