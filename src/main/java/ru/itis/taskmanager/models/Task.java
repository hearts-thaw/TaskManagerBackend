package ru.itis.taskmanager.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tasks")
@NoArgsConstructor
public class Task {
    @ApiModelProperty(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Text is mandatory")
    private String text;

    @ApiModelProperty(hidden = true)
    @PastOrPresent(message = "Task must be added with past or present timestamp")
    private LocalDateTime datetime;

    @ApiModelProperty(hidden = true)
    @Builder.Default
    private boolean completed = false;

    @ApiModelProperty(hidden = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private TaskUser userid;
}
