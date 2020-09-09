package ru.itis.taskmanager.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.taskmanager.models.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class TaskDto {
    private Long id;

    private String text;

    private LocalDateTime datetime;

    private boolean completed;

    public static TaskDto from(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .text(task.getText())
                .datetime(task.getDatetime())
                .completed(task.isCompleted()).build();
    }

    public static List<TaskDto> from(List<Task> tasks) {
        return tasks.stream().map(TaskDto::from).collect(Collectors.toList());
    }
}
