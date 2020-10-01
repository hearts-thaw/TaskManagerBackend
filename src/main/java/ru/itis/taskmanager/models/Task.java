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
    @Column(name = "taskid")
    private Long id;

    @NotBlank(message = "Task should have text")
    @Column(name = "tasktext")
    private String text;

    @ApiModelProperty(hidden = true)
    @PastOrPresent(message = "Task must be added with past or present timestamp")
    private LocalDateTime datetime = LocalDateTime.now();

    @ApiModelProperty(hidden = true)
    @Builder.Default
    private boolean completed = false;

    @ApiModelProperty(hidden = true)
    @Builder.Default
    private boolean onfire = false;

    @ApiModelProperty(hidden = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "froth_id")
    private Froth froth_id;

    @ApiModelProperty(hidden = true)
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
    private TaskUser user_id;

}
