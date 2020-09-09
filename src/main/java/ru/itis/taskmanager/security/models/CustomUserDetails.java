package ru.itis.taskmanager.security.models;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails<T extends User> extends UserDetails {
    T getUser();
}
