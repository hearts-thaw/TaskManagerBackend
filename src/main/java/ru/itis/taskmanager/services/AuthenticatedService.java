package ru.itis.taskmanager.services;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.itis.taskmanager.security.models.CustomUserDetails;
import ru.itis.taskmanager.security.models.User;

public class AuthenticatedService<T extends CustomUserDetails<? extends User>> {
    protected Long getId() {
        return getUser().getId();
    }

    protected User getUser() {
        return ((T) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    }
}
