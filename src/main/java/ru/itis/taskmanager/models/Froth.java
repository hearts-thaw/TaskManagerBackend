package ru.itis.taskmanager.models;

import com.vladmihalcea.hibernate.type.interval.PostgreSQLIntervalType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Duration;

@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "froth")
@NoArgsConstructor
@TypeDef(
        typeClass = PostgreSQLIntervalType.class,
        defaultForType = Duration.class)
public class Froth {
    @ApiModelProperty(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "frothid")
    private Long id;

    @ApiModelProperty(required = true)
    @NotBlank(message = "Froth should have name")
    @Column(name = "frothname")
    private String name;

    @Builder.Default
    private Short capacity = 8;

    //    TODO: let this value vary on init
    @Builder.Default
    @Column(columnDefinition = "interval")
    private Duration duration = Duration.ofDays(3);

    @Builder.Default
    @ApiModelProperty(hidden = true)
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group = Group.builder().name("maingr").build();
}
