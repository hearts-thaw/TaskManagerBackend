package ru.itis.taskmanager.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class TaskUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username must not be null")
    @Size(message = "Username must be at least 5 characters long", min = 5)
    private String username;

    @NotNull
    private String password;

    @Builder.Default
    private String role = "user";
}
