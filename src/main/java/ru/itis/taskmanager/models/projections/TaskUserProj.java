package ru.itis.taskmanager.models.projections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.rest.core.config.Projection;
import ru.itis.taskmanager.models.TaskUser;

@Projection(name = "user", types = TaskUser.class)
@Profile("jpa")
public interface TaskUserProj {
    @Value("#{target.id}")
    Long getId();

    String getUsername();
}
