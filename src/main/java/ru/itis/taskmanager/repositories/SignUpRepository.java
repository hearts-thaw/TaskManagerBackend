package ru.itis.taskmanager.repositories;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.itis.taskmanager.models.TaskUser;

@org.springframework.stereotype.Repository
@RepositoryRestResource(exported = false)
public interface SignUpRepository extends Repository<TaskUser, Long> {
    TaskUser save(TaskUser user);
}
