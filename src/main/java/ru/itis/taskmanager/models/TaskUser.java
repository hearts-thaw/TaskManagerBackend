package ru.itis.taskmanager.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.taskmanager.security.models.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Builder
@Entity
@Data
@NoArgsConstructor
@Table(name = "taskusers")
public class TaskUser implements User {
    @ApiModelProperty(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(required = true)
    @NotBlank(message = "Username must not be null")
    @Size(message = "Username must be at least 5 characters long", min = 5)
    private String username;

    @ApiModelProperty(required = true)
    @NotNull
    private String password;

    @ApiModelProperty(example = "user", hidden = true, allowableValues = "user")
    @Builder.Default
    private String role = "user";
}
