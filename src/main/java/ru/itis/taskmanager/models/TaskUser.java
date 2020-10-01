package ru.itis.taskmanager.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@Builder
@Entity
@Data
@NoArgsConstructor
@Table(name = "taskusers")
public class TaskUser {
    @ApiModelProperty(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long id;

    @ApiModelProperty(required = true)
    @NotBlank(message = "Username must not be null")
    @Size(message = "Username must be at least 5 characters long and less or equal to 32", min = 5, max = 32)
    private String username;

    @ApiModelProperty(required = true)
    @NotBlank(message = "Password must not be null")
    @Size(message = "Password should be less than 60 character long", max = 60)
    private String password;

    @ApiModelProperty(example = "user", hidden = true, allowableValues = "user")
    @Builder.Default
    private String role = "user";

    @ApiModelProperty(hidden = true)
    @ManyToMany(mappedBy = "taskusers", fetch = FetchType.LAZY)
    private List<Group> groups;

}
