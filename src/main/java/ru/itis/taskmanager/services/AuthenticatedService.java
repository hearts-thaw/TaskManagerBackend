package ru.itis.taskmanager.services;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.itis.taskmanager.models.TaskUser;
import ru.itis.taskmanager.security.models.CustomUser;

public interface AuthenticatedService<T extends CustomUser> {
    default TaskUser getUser() {
        return ((T) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    }

    default Long getId() {
        return getUser().getId();
    }
}
