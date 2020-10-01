package ru.itis.taskmanager.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "groups")
@NoArgsConstructor
public class Group implements Serializable {
    @ApiModelProperty(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groupid")
    private Long id;

    @ApiModelProperty(required = true)
    @NotBlank(message = "Group should have name")
    @Column(name = "groupname")
    private String name;

    @ApiModelProperty(hidden = true)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "groups_taskusers",
            joinColumns = @JoinColumn(
                    name = "group_id",
                    referencedColumnName = "groupid"),
            inverseJoinColumns = @JoinColumn(
                    name = "taskuser_id",
                    referencedColumnName = "userid"))
    private List<TaskUser> taskusers = new ArrayList<>();
//
//    @ApiModelProperty(hidden = true)
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
//            mappedBy = "group")
//    private List<Froth> froth = Collections.emptyList();
}
