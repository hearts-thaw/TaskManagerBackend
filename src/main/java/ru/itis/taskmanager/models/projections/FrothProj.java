package ru.itis.taskmanager.models.projections;

import org.springframework.context.annotation.Profile;
import org.springframework.data.rest.core.config.Projection;
import ru.itis.taskmanager.models.Froth;

@Projection(name = "froth", types = Froth.class)
@Profile("jpa")
public interface FrothProj {
    String getName();
}
